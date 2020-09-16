package com.urna.urnacare.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ConsultationDto implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long id ;
	
	private String  consultationFor;
	private String 	speciality;
	private String 	healthIssue;
	private String 	durationOfHealthIssue;
	private String  additionalQuery;
	private String  consultationResponse;
	private Long lastrespondedByDocId;
	private Long cratedByPatientId;
	
	private String status;
	private Set<MedicalFileDto> medicalFiles;
}
