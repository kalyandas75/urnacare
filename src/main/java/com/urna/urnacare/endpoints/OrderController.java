package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.OrderAddress;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.OrderDTO;
import com.urna.urnacare.dto.OrderItemDTO;
import com.urna.urnacare.dto.PrescriptionDrugQuantityDTO;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.security.SecurityUtils;
import com.urna.urnacare.service.OrderService;
import com.urna.urnacare.util.HeaderUtil;
import com.urna.urnacare.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;


    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/init/{consultationId}")
    public ResponseEntity<List<OrderItemDTO>> preOrder(@PathVariable Long consultationId) {
        log.info("order init.....");
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

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll(Pageable pageable) {
        Page<OrderDTO> results = null;
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PATIENT)) {
            User patient = this.userRepository.findOneByEmailIgnoreCase(SecurityUtils.getCurrentUserLogin().get()).get();
            results = this.orderService.getAllByPatient(patient.getId(), pageable);
        } else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SUPPORT)) {
            results = this.orderService.getAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(results, "/api/orders");
        return new ResponseEntity<>(results != null ? results.getContent(): null, headers, HttpStatus.OK);
    }

}
