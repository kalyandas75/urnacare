package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.CompositionDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.CompositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/compositions")
@Slf4j
public class CompositionController {
    private final CompositionService service;

    public CompositionController(CompositionService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompositionDTO> getAll() {
        return this.service.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompositionDTO getOne(@PathVariable Long id) {
        return this.service.getOne(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public CompositionDTO create(@Valid @RequestBody CompositionDTO compositionDTO) {
        if(compositionDTO.getId() != null) {
            throw new BadRequestAlertException("Cannot create composition. Id not null", "composition", "idNotNull");
        }
        return this.service.save(compositionDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public CompositionDTO create(@PathVariable Long id, @Valid @RequestBody CompositionDTO compositionDTO) {
        compositionDTO.setId(id);
        return this.service.save(compositionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
