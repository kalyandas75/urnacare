package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Composition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Composition> findByNameContainingIgnoreCase(String name);
}
