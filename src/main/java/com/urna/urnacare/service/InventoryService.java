package com.urna.urnacare.service;

import com.urna.urnacare.domain.Inventory;
import com.urna.urnacare.dto.InventoryDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.InventoryMapper;
import com.urna.urnacare.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    public InventoryService(InventoryRepository repository, InventoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<InventoryDTO> getAll(Pageable pageable) {
        return this.repository.findAll(pageable)
                .map(inventory -> this.mapper.toDto(inventory));
    }

    public Optional<InventoryDTO> getOne(Long id) {
        return this.repository.findById(id)
                .map(inventory -> this.mapper.toDto(inventory));
    }

    public Optional<InventoryDTO> save(InventoryDTO dto) {
        if(dto.getId() == null) {
            if(this.repository.existsByBrandIgnoreCaseAndBatchNumberIgnoreCase(dto.getBrand(), dto.getBatchNumber())) {
                throw new BadRequestAlertException("Inventory entry already exists. Please update instead of creating new entry.", "inventory", "exists");
            }
        } else {
            Inventory inventory = this.repository.findById(dto.getId()).get();
            if(inventory == null) {
                throw new BadRequestAlertException("Inventory does not exist", "inventory", "notFound");
            }
            if(!(inventory.getBrand().equalsIgnoreCase(dto.getBrand()) && inventory.getBatchNumber().equalsIgnoreCase(dto.getBatchNumber()))) {
                if(this.repository.existsByBrandIgnoreCaseAndBatchNumberIgnoreCase(dto.getBrand(), dto.getBatchNumber())) {
                    throw new BadRequestAlertException("Inventory entry already exists. Please update instead of creating new entry.", "inventory", "exists");
                }
            }
        }
        return Optional.of(this.mapper.toDto(this.repository.save(this.mapper.toEntity(dto))));
    }

    public void delete(Long id) {
        Inventory inventory = this.repository.findById(id).get();
        if(inventory == null) {
            throw new BadRequestAlertException("Inventory does not exist", "inventory", "notFound");
        }
        this.repository.deleteById(id);
    }

}
