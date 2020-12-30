package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchDoctorDto implements Serializable {
    private Long id ;
    private String firstName;
    private String lastName;
    private String primarySpeciality;
    private List<String> qualifications;
    private BigDecimal fees;
}
