package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.OrderAddress;
import com.urna.urnacare.domain.OrderItem;
import com.urna.urnacare.dto.InventoryDTO;
import com.urna.urnacare.dto.OrderDTO;
import com.urna.urnacare.dto.OrderItemDTO;
import com.urna.urnacare.dto.PrescriptionDrugQuantityDTO;
import com.urna.urnacare.service.OrderService;
import com.urna.urnacare.util.HeaderUtil;
import com.urna.urnacare.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/init/{consultationId}")
    public ResponseEntity<List<OrderItemDTO>> preOrder(@PathVariable Long consultationId) {
        log.info("order init.....");
        System.out.println("order init ....");
        final List<OrderItemDTO> orderItems = orderService.preOrderItems(consultationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(orderItems);
        return new ResponseEntity<>(orderItems, headers, HttpStatus.OK);
    }

    @PostMapping("/finish/{consultationId}")
    public ResponseEntity<OrderDTO> create(@PathVariable Long consultationId, @RequestBody List<PrescriptionDrugQuantityDTO> prescriptionDrugQuantityDTOS) throws URISyntaxException {
        OrderDTO orderDTO = this.orderService.createOrder(consultationId, prescriptionDrugQuantityDTOS);
        return ResponseEntity.created(new URI("/api/orders/" + orderDTO.getId()))
                .headers(HeaderUtil.createAlert( "order.created", String.valueOf(orderDTO.getId())))
                .body(orderDTO);
    }

    @PutMapping("/shipping-address/{id}")
    public void updateShippingAddress(@PathVariable Long id, @RequestBody OrderAddress orderAddress) {
        this.orderService.updateShippingAddress(id, orderAddress);
    }


}
