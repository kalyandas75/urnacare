package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByCompositionId(Long compositionId);
    boolean existsByManufacturerId(Long manufacturerId);
    boolean existsByBrandIgnoreCaseAndBatchNumberIgnoreCase(String brand, String batchNumber);
}
