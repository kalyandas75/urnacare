package com.urna.urnacare.service;

import com.urna.urnacare.domain.Ingredient;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
    @Transactional(readOnly = true)
    public Page<Ingredient> getAll(Pageable pageable) {
        return this.ingredientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Ingredient> getOne(Long id) {
        return this.ingredientRepository.findById(id);
    }

    public Ingredient create(Ingredient ingredient) {
        if(ingredient.getId() != null) {
            throw new BadRequestAlertException("Cannot have id.", "ingredient", "idNotNull");
        }
        return this.ingredientRepository.save(ingredient);
    }

    public Optional<Ingredient> update(Long id, Ingredient ingredient) {
        if(id == null) {
            throw new BadRequestAlertException("Id null.", "ingredient", "idNull");
        }
        if(this.ingredientRepository.getOne(id) == null) {
            throw new BadRequestAlertException("ingredient not found.", "ingredient", "notFound");
        }
        ingredient.setId(id);
        return Optional.of(this.ingredientRepository.save(ingredient));
    }

    public void delete(Long id) {
        this.ingredientRepository.deleteById(id);
    }
}
