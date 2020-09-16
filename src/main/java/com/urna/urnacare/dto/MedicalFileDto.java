package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

@Data
@NoArgsConstructor
public class MedicalFileDto implements Serializable {
    private Long id ;
    private String fileName;
    private String fileType;
    private byte[] file;
    private Long appointmentId;
    private Long consultationId;
    private Long doctorId;
    private Long patientId;
}
