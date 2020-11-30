package com.urna.urnacare.service;

import com.urna.urnacare.domain.*;
import com.urna.urnacare.dto.OrderDTO;
import com.urna.urnacare.dto.OrderItemDTO;
import com.urna.urnacare.dto.PrescriptionDrugQuantityDTO;
import com.urna.urnacare.enumeration.OrderStatus;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.InventoryMapper;
import com.urna.urnacare.mapper.OrderItemMapper;
import com.urna.urnacare.mapper.OrderMapper;
import com.urna.urnacare.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final ConsultationRepository consultationRepository;

    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final InventoryMapper inventoryMapper;

    private final PrescriptionDrugRepository prescriptionDrugRepository;

    private final AppointmentRepository appointmentRepository;

    private final DrugRepository drugRepository;

    public OrderService(OrderItemRepository orderItemRepository,
                        OrderRepository orderRepository,
                        InventoryRepository inventoryRepository,
                        ConsultationRepository consultationRepository,
                        OrderItemMapper orderItemMapper,
                        OrderMapper orderMapper,
                        InventoryMapper inventoryMapper,
                        PrescriptionDrugRepository prescriptionDrugRepository,
                        AppointmentRepository appointmentRepository, DrugRepository drugRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.consultationRepository = consultationRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderMapper = orderMapper;
        this.inventoryMapper = inventoryMapper;
        this.prescriptionDrugRepository = prescriptionDrugRepository;
        this.appointmentRepository = appointmentRepository;
        this.drugRepository = drugRepository;
    }



    public List<OrderItemDTO> preOrderItems(Long consultationId) {
        Optional<Consultation> consultationOptional = this.consultationRepository.findById(consultationId);
        List<OrderItemDTO> itemDTOS = new ArrayList<>();
        if (consultationOptional.isPresent()) {
            List<PrescriptionDrug> prescriptionDrugs = consultationOptional.get().getPrescriptionDrugs();
            if (prescriptionDrugs != null) {
                prescriptionDrugs.stream().forEach(pd -> {
                    System.out.println("pd ===>");
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTO.setDrugId(pd.getDrug().getId());
                    itemDTO.setDrugBrand(pd.getDrug().getBrand());
                    itemDTO.setDrugCompositionName(pd.getDrug().getComposition().getName());
                    itemDTO.setDrugManufacturerName(pd.getDrug().getManufacturer().getName());
                    itemDTO.setDrugStrength(pd.getDrug().getStrength());
                    itemDTO.setDrugFormulation(pd.getDrug().getFormulation());
                    itemDTO.setDose(pd.getDose());
                    itemDTO.setUnit(pd.getUnit());
                    itemDTO.setFrequency(pd.getFrequency());
                    itemDTO.setDuration(pd.getDuration());
                    itemDTO.setDurationUnit(pd.getDurationUnit());
                    itemDTOS.add(itemDTO);
                    List<Inventory> inventories = this.inventoryRepository.findByDrugAndExpiryDateGreaterThanOrderByExpiryDateDesc(pd.getDrug(), LocalDate.now());
                    System.out.println("--------------inventories----------------");
                    System.out.println(inventories);
                    System.out.println("-----------------------------------------");
                    if (inventories != null && inventories.size() > 0) {
                        Inventory inventory = getHighestPricedInventory(inventories);
                        itemDTO.setPricePerUnit(inventory.getPrice());
                        itemDTO.setQuantity(1); // keep one for now will calculate later
                        itemDTO.setCgst(inventory.getCgst());
                        itemDTO.setIgst(inventory.getIgst());
                        itemDTO.setDiscountRate(inventory.getMaxDiscountRate());
                        itemDTO.setSgst(inventory.getSgst());
                        itemDTO.setPackSize(inventory.getPackSize());
                    } else {
                        itemDTO.setUnavailable(true);
                        itemDTO.setQuantity(0);
                    }
                });
            }
        }
        return itemDTOS;
    }

    public OrderDTO createOrder(Long consultationId, List<PrescriptionDrugQuantityDTO> prescriptionDrugQuantityDTOS) {
        Optional<Consultation> optionalConsultation = this.consultationRepository.findById(consultationId);
        if(!optionalConsultation.isPresent()) {
            throw new BadRequestAlertException("Consultation does not exist", "consultation", "notFound");
        }

        Consultation consultation = optionalConsultation.get();
        List<Order> existingOrders = this.orderRepository.findByConsultationIdAndStatus(consultationId, OrderStatus.CREATED);
        if(existingOrders !=null && existingOrders.size() > 0) {
            throw new BadRequestAlertException("There are similar unpaid orders. Please use order history and complete.", "orders", "unPaidOrders");
        }

        List<Long> pIds = consultation.getPrescriptionDrugs().stream().map(pd -> pd.getDrug().getId()).collect(Collectors.toList());
        List<PrescriptionDrugQuantityDTO> filteredPdQs = prescriptionDrugQuantityDTOS.stream().filter(pdq -> pIds.contains(pdq.getDrugId()))
                .collect(Collectors.toList());
        List<OrderItem> orderItems = new ArrayList<>();
        LocalDate today = LocalDate.now();
        filteredPdQs.forEach(fpdq -> {
            Drug drug = this.drugRepository.getOne(fpdq.getDrugId());
            if(drug != null) {
                List<Inventory> inventories = this.inventoryRepository.findByDrugAndExpiryDateGreaterThanOrderByExpiryDateDesc(drug, today);
                if (inventories != null && inventories.size() > 0) {
                    Inventory inventory = getHighestPricedInventory(inventories);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setDrug(drug);
                    orderItem.setPricePerUnit(inventory.getPrice());
                    orderItem.setCgst(inventory.getCgst());
                    orderItem.setDiscountRate(inventory.getMaxDiscountRate());
                    orderItem.setIgst(inventory.getIgst());
                    orderItem.setSgst(inventory.getSgst());
                    orderItem.setQuantity(fpdq.getQuantity());
                    orderItems.add(orderItem);
                }
            }
        });
        Order order = new Order();
        order.setConsultationId(consultationId);
        order.setItems(orderItems);
        User patient = this.appointmentRepository.findByConsultationId(consultationId).getPatient();
        order.setPatientId(patient.getId());
        order.setItems(orderItems);
        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setStatus(OrderStatus.CREATED);
        orderStatusHistory.setStatusDate(Instant.now());
        List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();
        orderStatusHistories.add(orderStatusHistory);
        order.setStatusHistory(orderStatusHistories);
        order.setStatus(OrderStatus.CREATED);
        return this.orderMapper.toDto(this.orderRepository.save(order));
    }

    public void updateShippingAddress(Long id, OrderAddress orderAddress) {
        Optional<Order> optionalOrder = this.orderRepository.findById(id);
        if(!optionalOrder.isPresent()) {
            throw new BadRequestAlertException("Order does not exist", "order", "notFound");
        }
        Order order = optionalOrder.get();
        order.setShippingAddress(orderAddress);
        Order savedOrder = this.orderRepository.save(order);
        System.out.println(savedOrder);
    }



    // if tablet then how many strips
    // if liquid then how many bottles

    /*
    private int calculateQuantity(PrescriptionDrug pd) {
        Formulation formulation = pd.getDrug().getFormulation();
        if (formulation == Formulation.Cream || formulation == Formulation.Drops || formulation == Formulation.Device
                || formulation == Formulation.Lotion || formulation == Formulation.Tonic || formulation == Formulation.Powder) {
            return 1;
        }
        //pd.

    }*/


    private Inventory getHighestPricedInventory(List<Inventory> inventories) {
        return inventories.stream().max(Comparator.comparing(v -> v.getPrice().doubleValue())).get();
    }
    private BigDecimal getPrice(List<Inventory> inventories, int quantity) {
        int count = 0;
        BigDecimal price = BigDecimal.ZERO;
        if(inventories != null) {
            for (int i = 0; i < inventories.size(); i++) {
                count += inventories.get(i).getNoOfUnits();
                if(count >= quantity) {
                    price = inventories.get(i).getPrice();
                    break;
                }
            }
        }
        return price;
    }


}
