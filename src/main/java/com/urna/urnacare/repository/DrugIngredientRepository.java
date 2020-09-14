package com.urna.urnacare.repository;

import com.urna.urnacare.domain.DrugIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugIngredientRepository extends JpaRepository<DrugIngredient, Long> {
}
