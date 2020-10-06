package com.urna.urnacare.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class ConsultationDto implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id ;
	@Size(max = 255)
	private String chiefComplaint;
	@Size(max = 255)
	private String observation;
	@Size(max = 255)
	private String suggestedInvestigation;
	@Size(max = 255)
	private String diagnosis;
	@Size(max = 255)
	private String specialInstructions;
	private List<PrescriptionDrugDTO> prescriptionDrugs;
}
