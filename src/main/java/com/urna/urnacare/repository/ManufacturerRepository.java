package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    boolean existsByNameIgnoreCase(String name);
}
