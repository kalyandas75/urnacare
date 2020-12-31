package com.urna.urnacare.dto;


import com.urna.urnacare.enumeration.AppointmentRequestStatus;
import com.urna.urnacare.enumeration.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AppointmentRequestDto implements Serializable {
    private Long id;
    private String requestId;
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private List<String> doctorQualifications;
    private String doctorPrimarySpeciality;
    private String healthIssueDescription;
    private LocalDate desiredDate;
    private Byte desiredStartHours;
    private Byte desiredEndHours;
    private Boolean dateFlexible;
    private Boolean hoursFlexible;
    private AppointmentRequestStatus requestStatus;
    private LocalDateTime createdAt;
    private String rejectReason;
    private PaymentStatus paymentStatus;
}
