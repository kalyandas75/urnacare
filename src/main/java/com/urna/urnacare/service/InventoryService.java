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

import java.util.List;
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

    public InventoryDTO add(Long id, Integer noOfUnits) {
        Inventory inventory = this.repository.findById(id).get();
        if(inventory == null) {
            throw new BadRequestAlertException("Inventory does not exist", "inventory", "notFound");
        }
        inventory.setNoOfUnits(inventory.getNoOfUnits() + noOfUnits);
        return this.mapper.toDto(this.repository.save(inventory));
    }

    public InventoryDTO subtract(Long id, Integer noOfUnits) {
        Inventory inventory = this.repository.findById(id).get();
        if(inventory == null) {
            throw new BadRequestAlertException("Inventory does not exist", "inventory", "notFound");
        }
        inventory.setNoOfUnits(inventory.getNoOfUnits() - noOfUnits);
        return this.mapper.toDto(this.repository.save(inventory));
    }

    public InventoryDTO create(InventoryDTO dto) {
        dto.setId(null);
        if(this.repository.existsByDrugIdAndBatchNumber(dto.getDrugId(), dto.getBatchNumber())) {
            throw new BadRequestAlertException("Inventory entry already exists. Please update instead of creating new entry.", "inventory", "exists");
        }
        Inventory inventory = this.mapper.toEntity(dto);
        return this.mapper.toDto(this.repository.save(inventory));
    }

    public InventoryDTO update(Long id, InventoryDTO dto) {
        Optional<Inventory> optionalInventory = this.repository.findById(id);
        if(!optionalInventory.isPresent()) {
            throw new BadRequestAlertException("Inventory not found", "Inventory", "notFound");
        }
        Inventory inventory = this.mapper.toEntity(dto);
        inventory.setId(id);
        return this.mapper.toDto(this.repository.save(inventory));
    }

    public void delete(Long id) {
        Inventory inventory = this.repository.findById(id).get();
        if(inventory == null) {
            throw new BadRequestAlertException("Inventory does not exist", "inventory", "notFound");
        }
        this.repository.deleteById(id);
    }

    public List<InventoryDTO> searchByBrandOrComposition(String q) {
        return this.mapper.toDto(this.repository.searchByBrandOrComposition(q));
    }

}
