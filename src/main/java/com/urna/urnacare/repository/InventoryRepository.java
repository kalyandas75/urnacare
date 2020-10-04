package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Drug;
import com.urna.urnacare.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByDrugIdAndBatchNumber(Long drugId, String batchNumber);
    @Query("SELECT i FROM Inventory i WHERE lower(i.drug.brand) LIKE lower(concat('%', :q,'%')) OR " +
            "lower(i.drug.composition.name) LIKE lower(concat('%', :q,'%'))")
    List<Inventory> searchByBrandOrComposition(@Param("q") String q);
}
