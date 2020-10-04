package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.CompositionDTO;
import com.urna.urnacare.dto.ManufacturerDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.ManufacturerService;
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
@RequestMapping("/api/manufacturers")
@Slf4j
public class ManufacturerController {
    private final ManufacturerService service;

    public ManufacturerController(ManufacturerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> getAll(Pageable pageable) {
        final Page<ManufacturerDTO> page = service.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manufacturers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ManufacturerDTO getOne(@PathVariable Long id) {
        return this.service.getOne(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ManufacturerDTO create(@Valid @RequestBody ManufacturerDTO manufacturerDTO) {
        if(manufacturerDTO.getId() != null) {
            throw new BadRequestAlertException("Cannot create manufacturer. Id not null", "manufacturer", "idNotNull");
        }
        return this.service.create(manufacturerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ManufacturerDTO create(@PathVariable Long id, @Valid @RequestBody ManufacturerDTO manufacturerDTO) {
        return this.service.update(id, manufacturerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ManufacturerDTO> findByName(@RequestParam("q") @NotBlank @Size(max = 3) String q) {
        return this.service.searchByManufacturerName(q);
    }
}
