package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class DoctorDTO extends UserDTO {
    @NotBlank
    @Size(max = 50)
    private String registrationNumber;
    @NotBlank
    @Size(max = 100)
    private String primarySpeciality;
    private List<String> otherSpecialities;
    private List<String> qualifications;
    private String practice;
}
