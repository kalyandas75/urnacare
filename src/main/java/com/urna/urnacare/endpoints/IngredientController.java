package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.Ingredient;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.IngredientService;
import com.urna.urnacare.util.HeaderUtil;
import com.urna.urnacare.util.PaginationUtil;
import com.urna.urnacare.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class IngredientController {
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAll(Pageable pageable) {
        final Page<Ingredient> page = this.ingredientService.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ingredients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> get(@PathVariable Long id) {
        log.debug("REST request to get ingredient : {}", id);
        return ResponseUtil.wrapOrNotFound(
                ingredientService.getOne(id)
        );
    }

    @PutMapping("/ingredients/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\" or \"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @Valid @RequestBody Ingredient ingredient) {
        log.debug("REST request to update Ingredient : {}", ingredient);
        return ResponseUtil.wrapOrNotFound(ingredientService.update(id, ingredient),
                HeaderUtil.createAlert("ingredient.updated", ingredient.getId() + ""));
    }
    @DeleteMapping("/ingredients/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\" or \"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.ingredientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "ingredient.deleted", String.valueOf(id))).build();
    }


}
