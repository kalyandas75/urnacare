package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.CompositionDTO;
import com.urna.urnacare.dto.DrugDTO;
import com.urna.urnacare.dto.IdNameDTO;
import com.urna.urnacare.dto.ManufacturerDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.CompositionService;
import com.urna.urnacare.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    public ResponseEntity<List<CompositionDTO>> getAll(Pageable pageable) {
        final Page<CompositionDTO> page = service.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compositions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        return this.service.create(compositionDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public CompositionDTO update(@PathVariable Long id, @Valid @RequestBody CompositionDTO compositionDTO) {
        log.debug("request to update composition {}", compositionDTO);
        return this.service.update(id, compositionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<IdNameDTO> findByName(@RequestParam("q") @NotBlank @Size(max = 3) String q) {
        return this.service.searchByCompositionName(q);
    }
}
