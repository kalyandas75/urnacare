package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentRequestTimeDto implements Serializable {
    @NotNull
    @FutureOrPresent
    private LocalDateTime requestTime;
}
