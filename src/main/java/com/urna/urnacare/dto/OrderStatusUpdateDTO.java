package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrderStatusUpdateDTO implements Serializable {
    @NotNull
    private OrderStatus status;
    @NotBlank
    private String comments;
}
