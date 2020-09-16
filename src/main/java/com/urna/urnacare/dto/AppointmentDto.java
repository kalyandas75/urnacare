package com.urna.urnacare.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AppointmentDto implements Serializable {
    private Long id ;

    private Long consultationId;

    private String uniquieKey;

    private Long patientId;

    private String patientFirstName;
    private String patientLastName;

    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private List<String> doctorQualifications;
    private String doctorPrimarySpeciality;

    private Timestamp apptEndTime;

    private Long apptEndTimeN;

    private Long appointmentRequestId;

    private LocalDateTime scheduledDate;

    private Set<MedicalFileDto> medicalFiles;
}
