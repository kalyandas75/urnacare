package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Long> {
    boolean existsByNameIgnoreCase(String name);
}
