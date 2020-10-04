package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Drug;
import com.urna.urnacare.enumeration.Formulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    boolean existsByBrandIgnoreCaseAndStrengthIgnoreCaseAndFormulation(String brand, String strength, Formulation formulation);
    @Query("SELECT d FROM Drug d WHERE lower(d.brand) LIKE lower(concat('%', :q,'%')) OR " +
            "lower(d.composition.name) LIKE lower(concat('%', :q,'%'))")
    List<Drug> searchByBrandOrComposition(@Param("q") String q);
    boolean existsByCompositionId(Long compositionId);
    boolean existsByManufacturerId(Long manufacturerId);
}
