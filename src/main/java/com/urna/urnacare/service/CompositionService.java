package com.urna.urnacare.service;

import ch.qos.logback.classic.gaffer.ComponentDelegate;
import com.urna.urnacare.domain.Composition;
import com.urna.urnacare.dto.CompositionDTO;
import com.urna.urnacare.dto.IdNameDTO;
import com.urna.urnacare.dto.ManufacturerDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.CompositionMapper;
import com.urna.urnacare.repository.CompositionRepository;
import com.urna.urnacare.repository.DrugRepository;
import com.urna.urnacare.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompositionService {
    private final CompositionRepository compositionRepository;
    private final CompositionMapper compositionMapper;
    private final DrugRepository drugRepository;

    public CompositionService(CompositionRepository compositionRepository, CompositionMapper compositionMapper,
                              DrugRepository drugRepository) {
        this.compositionRepository = compositionRepository;
        this.compositionMapper = compositionMapper;
        this.drugRepository = drugRepository;
    }

    public Page<CompositionDTO> getAll(Pageable pageable) {
        return this.compositionRepository.findAll(pageable)
                .map(composition -> this.compositionMapper.toDto(composition));
    }

    public Optional<CompositionDTO> getOne(Long id) {
        return this.compositionRepository.findById(id)
                .filter(Objects::nonNull)
                .map(composition -> this.compositionMapper.toDto(composition));
    }

    public CompositionDTO update(Long id, CompositionDTO dto) {
        Optional<Composition> optionalComposition = this.compositionRepository.findById(id);
        if(!optionalComposition.isPresent()) {
            throw new BadRequestAlertException("Composition not found", "composition", "notFound");
        }
        if(this.compositionRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestAlertException("Composition already exists", "composition", "exists");
        }
        Composition composition = optionalComposition.get();
        composition.setName(dto.getName());
        return this.compositionMapper.toDto(this.compositionRepository.save(composition));
    }

    public CompositionDTO create(CompositionDTO dto) {
        if(this.compositionRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestAlertException("Composition already exists", "composition", "exists");
        }
        Composition composition = this.compositionMapper.toEntity(dto);
        return this.compositionMapper.toDto(this.compositionRepository.save(composition));
    }

    public void delete(Long id) {
        if(!this.compositionRepository.findById(id)
                .isPresent()) {
            throw new BadRequestAlertException("Composition not found", "composition", "notFound");
        }
        if(this.drugRepository.existsByCompositionId(id)) {
            throw new BadRequestAlertException("Drug exists for this composition, cannot delete", "composition", "dependencyExists");
        }
        this.compositionRepository.deleteById(id);
    }

    public List<IdNameDTO> searchByCompositionName(String nameLike) {
        return this.compositionRepository.findByNameContainingIgnoreCase(nameLike).stream()
                .map(composition -> {
                    return new IdNameDTO(composition.getId(), composition.getName());
                })
                .collect(Collectors.toList());
    }
}
