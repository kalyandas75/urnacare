package com.urna.urnacare.service;

import com.urna.urnacare.domain.Manufacturer;
import com.urna.urnacare.dto.ManufacturerDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.ManufacturerMapper;
import com.urna.urnacare.repository.InventoryRepository;
import com.urna.urnacare.repository.ManufacturerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;
    private final InventoryRepository inventoryRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository,
                               ManufacturerMapper manufacturerMapper,
                               InventoryRepository inventoryRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerMapper = manufacturerMapper;
        this.inventoryRepository = inventoryRepository;
    }

    public List<ManufacturerDTO> getAll() {
        return this.manufacturerMapper.toDto(this.manufacturerRepository.findAll());
    }

    public Optional<ManufacturerDTO> getOne(Long id) {
        return this.manufacturerRepository.findById(id)
                .filter(Objects::nonNull)
                .map(composition -> this.manufacturerMapper.toDto(composition));
    }

    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO) {
        if(manufacturerDTO.getId() == null) {
            if(this.manufacturerRepository.existsByNameIgnoreCase(manufacturerDTO.getName())) {
                throw new BadRequestAlertException("Manufacturer already exists", "manufacturer", "exists");
            }
        } else {
            Optional<Manufacturer> optionalManufacturer = this.manufacturerRepository.findById(manufacturerDTO.getId());
            if(!optionalManufacturer.isPresent()) {
                throw new BadRequestAlertException("Composition not found", "composition", "notFound");
            }
            Manufacturer manufacturer = optionalManufacturer.get();
            if(!manufacturer.getName().equalsIgnoreCase(manufacturerDTO.getName())){
                if(this.manufacturerRepository.existsByNameIgnoreCase(manufacturerDTO.getName())) {
                    throw new BadRequestAlertException("Manufacturer already exists", "manufacturer", "exists");
                }
            }
        }
        Manufacturer manufacturer = this.manufacturerMapper.toEntity(manufacturerDTO);
        return this.manufacturerMapper.toDto(this.manufacturerRepository.save(manufacturer));
    }

    public void delete(Long id) {
        if(!this.manufacturerRepository.findById(id)
                .isPresent()) {
            throw new BadRequestAlertException("Manufacturer not found", "manufacturer", "notFound");
        }
        if(this.inventoryRepository.existsByManufacturerId(id)) {
            throw new BadRequestAlertException("Manufacturer exists for this manufacturer, cannot delete", "manufacturer", "dependencyExists");
        }
        this.manufacturerRepository.deleteById(id);
    }
}
