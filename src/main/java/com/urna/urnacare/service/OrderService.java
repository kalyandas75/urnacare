package com.urna.urnacare.service;

import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.domain.Inventory;
import com.urna.urnacare.domain.PrescriptionDrug;
import com.urna.urnacare.dto.OrderItemDTO;
import com.urna.urnacare.enumeration.Formulation;
import com.urna.urnacare.mapper.InventoryMapper;
import com.urna.urnacare.mapper.OrderItemMapper;
import com.urna.urnacare.mapper.OrderMapper;
import com.urna.urnacare.repository.ConsultationRepository;
import com.urna.urnacare.repository.InventoryRepository;
import com.urna.urnacare.repository.OrderItemRepository;
import com.urna.urnacare.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public OrderService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, InventoryRepository inventoryRepository, ConsultationRepository consultationRepository, OrderItemMapper orderItemMapper, OrderMapper orderMapper, InventoryMapper inventoryMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.consultationRepository = consultationRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderMapper = orderMapper;
        this.inventoryMapper = inventoryMapper;
    }



    /*
    public List<OrderItemDTO> preOrderItems(Long consultationId) {
        Optional<Consultation> consultationOptional = this.consultationRepository.findById(consultationId);
        if (consultationOptional.isPresent()) {
            List<PrescriptionDrug> prescriptionDrugs = consultationOptional.get().getPrescriptionDrugs();
            if (prescriptionDrugs != null) {
                List<OrderItemDTO> itemDTOS = new ArrayList<>();
                prescriptionDrugs.stream().forEach(pd -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTOS.add(itemDTO);
                    List<Inventory> inventories = this.inventoryRepository.findOneByDrugAndExpiryDateGreaterThanOrderByExpiryDateDesc(pd.getDrug(), LocalDate.now());
                    if (inventories != null) {
                        itemDTO.setPricePerUnit(getPrice(inventories, ));
                    }
                });
            }
        }
    }*/

    // if tablet then how many strips
    // if liquid then how many bottles
    /*
    private int calculateQuantity(PrescriptionDrug pd) {
        Formulation formulation = pd.getDrug().getFormulation();
        if(formulation == Formulation.Cream || formulation == Formulation.Drops || formulation == Formulation.Device
        || formulation == Formulation.Lotion || formulation == Formulation.Tonic || formulation == Formulation.Powder) {
            return 1;
        }

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
    */

}
