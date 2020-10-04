package com.urna.urnacare.service;

import com.urna.urnacare.domain.Manufacturer;
import com.urna.urnacare.dto.ManufacturerDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.ManufacturerMapper;
import com.urna.urnacare.repository.DrugRepository;
import com.urna.urnacare.repository.ManufacturerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;
    private final DrugRepository drugRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository,
                               ManufacturerMapper manufacturerMapper,
                               DrugRepository drugRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerMapper = manufacturerMapper;
        this.drugRepository = drugRepository;
    }

    public Page<ManufacturerDTO> getAll(Pageable pageable) {
        return this.manufacturerRepository.findAll(pageable)
                .map(manufacturer -> this.manufacturerMapper.toDto(manufacturer));
    }

    public Optional<ManufacturerDTO> getOne(Long id) {
        return this.manufacturerRepository.findById(id)
                .filter(Objects::nonNull)
                .map(manufacturer -> this.manufacturerMapper.toDto(manufacturer));
    }

    public ManufacturerDTO update(Long id, ManufacturerDTO dto) {
        Optional<Manufacturer> optionalManufacturer = this.manufacturerRepository.findById(id);
        if(!optionalManufacturer.isPresent()) {
            throw new BadRequestAlertException("Manufacturer not found", "Manufacturer", "notFound");
        }
        if(this.manufacturerRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestAlertException("Manufacturer already exists", "Manufacturer", "exists");
        }
        Manufacturer Manufacturer = optionalManufacturer.get();
        Manufacturer.setName(dto.getName());
        return this.manufacturerMapper.toDto(this.manufacturerRepository.save(Manufacturer));
    }

    public ManufacturerDTO create(ManufacturerDTO dto) {
        if(this.manufacturerRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestAlertException("Manufacturer already exists", "Manufacturer", "exists");
        }
        Manufacturer Manufacturer = this.manufacturerMapper.toEntity(dto);
        return this.manufacturerMapper.toDto(this.manufacturerRepository.save(Manufacturer));
    }

    public void delete(Long id) {
        if(!this.manufacturerRepository.findById(id)
                .isPresent()) {
            throw new BadRequestAlertException("Manufacturer not found", "Manufacturer", "notFound");
        }
        if(this.drugRepository.existsByManufacturerId(id)) {
            throw new BadRequestAlertException("Inventory exists for this Manufacturer, cannot delete", "Manufacturer", "dependencyExists");
        }
        this.manufacturerRepository.deleteById(id);
    }

    public List<ManufacturerDTO> searchByManufacturerName(String nameLike) {
        return this.manufacturerMapper.toDto(this.manufacturerRepository.findByNameContainingIgnoreCase(nameLike));
    }
}
