package com.urna.urnacare.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "consultation")
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
}
