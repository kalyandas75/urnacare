package com.urna.urnacare.service;

import com.urna.urnacare.domain.Composition;
import com.urna.urnacare.dto.CompositionDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.CompositionMapper;
import com.urna.urnacare.repository.CompositionRepository;
import com.urna.urnacare.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CompositionService {
    private final CompositionRepository compositionRepository;
    private final CompositionMapper compositionMapper;
    private final InventoryRepository inventoryRepository;

    public CompositionService(CompositionRepository compositionRepository, CompositionMapper compositionMapper, InventoryRepository inventoryRepository) {
        this.compositionRepository = compositionRepository;
        this.compositionMapper = compositionMapper;
        this.inventoryRepository = inventoryRepository;
    }

    public List<CompositionDTO> getAll() {
        return this.compositionMapper.toDto(this.compositionRepository.findAll());
    }

    public Optional<CompositionDTO> getOne(Long id) {
        return this.compositionRepository.findById(id)
                .filter(Objects::nonNull)
                .map(composition -> this.compositionMapper.toDto(composition));
    }

    public CompositionDTO save(CompositionDTO compositionDTO) {
        if(compositionDTO.getId() == null) {
            if(this.compositionRepository.existsByNameIgnoreCase(compositionDTO.getName())) {
                throw new BadRequestAlertException("Composition already exists", "composition", "exists");
            }
        } else {
            Optional<Composition> optionalComposition = this.compositionRepository.findById(compositionDTO.getId());
            if(!optionalComposition.isPresent()) {
                throw new BadRequestAlertException("Composition not found", "composition", "notFound");
            }
            Composition composition = optionalComposition.get();
            if(!composition.getName().equalsIgnoreCase(compositionDTO.getName())){
                if(this.compositionRepository.existsByNameIgnoreCase(compositionDTO.getName())) {
                    throw new BadRequestAlertException("Composition already exists", "composition", "exists");
                }
            }
        }
        Composition composition = this.compositionMapper.toEntity(compositionDTO);
        return this.compositionMapper.toDto(this.compositionRepository.save(composition));
    }

    public void delete(Long id) {
        if(!this.compositionRepository.findById(id)
                .isPresent()) {
            throw new BadRequestAlertException("Composition not found", "composition", "notFound");
        }
        if(this.inventoryRepository.existsByCompositionId(id)) {
            throw new BadRequestAlertException("Inventory exists for this composition, cannot delete", "composition", "dependencyExists");
        }
        this.compositionRepository.deleteById(id);
    }
}
