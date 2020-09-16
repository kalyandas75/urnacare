package com.urna.urnacare.domain;

import javax.persistence.*;

@Entity
@Table(name = "consultationfile")
public class ConsultationFile implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	private Long consultationId ;
	
	private byte[] file;
	
	private String fileName;

	private String fileType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConsultationId() {
		return consultationId;
	}

	public void setConsultationId(Long consultationId) {
		this.consultationId = consultationId;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


}
