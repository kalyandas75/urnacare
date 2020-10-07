package com.urna.urnacare.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "consultation")
@Data
public class Consultation implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	@Column(length = 255)
	private String chiefComplaint;
	@Column(length = 255)
	private String observation;
	@Column(length = 255)
	private String suggestedInvestigation;
	@Column(length = 255)
	private String diagnosis;
	@Column(length = 255)
	private String specialInstructions;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="consultationId", referencedColumnName="id")
	private List<PrescriptionDrug> prescriptionDrugs;
	@Column(name = "created_date", columnDefinition = "TIMESTAMP", updatable = false)
	private Instant createdDate = Instant.now();
}
