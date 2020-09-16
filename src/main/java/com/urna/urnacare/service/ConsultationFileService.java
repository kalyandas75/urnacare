package com.urna.urnacare.service;

import com.urna.urnacare.domain.ConsultationFile;
import com.urna.urnacare.repository.ConsultationFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ConsultationFileService {
	ConsultationFileRepository consultationFileRepository;

	public ConsultationFileRepository getConsultationFileRepository() {
		return consultationFileRepository;
	}

	@Autowired
	public void setConsultationFileRepository(ConsultationFileRepository consultationFileRepository) {
		this.consultationFileRepository = consultationFileRepository;
	}
	public ConsultationFile save(ConsultationFile consultationFile, MultipartFile file) throws IOException {
		  // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        consultationFile.setFileType(file.getContentType());
        consultationFile.setFileName(fileName);
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            consultationFile.setFile(file.getBytes());
		return consultationFileRepository.save(consultationFile);
	}
	public void delete(ConsultationFile consultationFile) {
		 consultationFileRepository.delete(consultationFile);
	}
	public Optional<ConsultationFile> findById(ConsultationFile consultationFile) {
		return consultationFileRepository.findById(consultationFile.getId());
	}
}
