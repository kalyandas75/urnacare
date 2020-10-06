package com.urna.urnacare.service;

import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.dto.ConsultationDto;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.ConsultationMapper;
import com.urna.urnacare.repository.ConsultationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class ConsultationService {

	private final ConsultationRepository repository;
	private final ConsultationMapper mapper;

	public ConsultationService(ConsultationRepository repository, ConsultationMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public ConsultationDto create(ConsultationDto dto) {
		if(dto.getId() != null) {
			throw new BadRequestAlertException("Consultation cannot have id while creating.",
					"consultation", "idNotNull");
		}
		Consultation consultation = this.mapper.toEntity(dto);
		return this.mapper.toDto(this.repository.save(consultation));
	}

	public ConsultationDto update(ConsultationDto dto) {
		if(dto.getId() == null) {
			throw new BadRequestAlertException("Consultation cannot be null while updating.",
					"consultation", "idNull");
		}
		Consultation consultation = this.mapper.toEntity(dto);
		return this.mapper.toDto(this.repository.save(consultation));
	}

	public ConsultationDto getOne(Long id) {
		Optional<Consultation> consultationOptional = this.repository.findById(id);
		if(consultationOptional
				.isPresent()) {
			return this.mapper.toDto(consultationOptional.get());
		}
		return null;
	}
}
