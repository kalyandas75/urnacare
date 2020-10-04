package com.urna.urnacare.service;

import com.urna.urnacare.domain.Composition;
import com.urna.urnacare.domain.Drug;
import com.urna.urnacare.dto.CompositionDTO;
import com.urna.urnacare.dto.DrugDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.DrugMapper;
import com.urna.urnacare.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DrugService {
    private final DrugRepository repository;
    private final DrugMapper mapper;

    public DrugService(DrugRepository repository, DrugMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<DrugDTO> getAll(Pageable pageable) {
        return this.repository.findAll(pageable)
                .map(drug -> this.mapper.toDto(drug));
    }

    public Optional<DrugDTO> getOne(Long id) {
        return this.repository.findById(id)
                .map(drug -> this.mapper.toDto(drug));
    }

    public DrugDTO create(DrugDTO dto) {
        dto.setId(null);
        if(this.repository.existsByBrandIgnoreCaseAndStrengthIgnoreCaseAndFormulation(dto.getBrand(), dto.getStrength(), dto.getFormulation())) {
            throw new BadRequestAlertException("Drug entry already exists. Please update instead of creating new entry.", "drug", "exists");
        }
        Drug drug = this.mapper.toEntity(dto);
        return this.mapper.toDto(this.repository.save(drug));
    }

    public DrugDTO update(Long id, DrugDTO dto) {
        Optional<Drug> optionalDrug = this.repository.findById(id);
        if(!optionalDrug.isPresent()) {
            throw new BadRequestAlertException("Drug not found", "composition", "notFound");
        }
        if(this.repository.existsByBrandIgnoreCaseAndStrengthIgnoreCaseAndFormulation(dto.getBrand(), dto.getStrength(), dto.getFormulation())) {
            throw new BadRequestAlertException("Drug entry already exists. Please update instead of creating new entry.", "drug", "exists");
        }

        return this.mapper.toDto(this.repository.save(this.mapper.toEntity(dto)));
    }


    public void delete(Long id) {
        Drug drug = this.repository.findById(id).get();
        if(drug == null) {
            throw new BadRequestAlertException("Drug does not exist", "drug", "notFound");
        }
        this.repository.deleteById(id);
    }

    public List<DrugDTO> searchByBrandOrComposition(String q) {
        return this.mapper.toDto(this.repository.searchByBrandOrComposition(q));
    }
}
