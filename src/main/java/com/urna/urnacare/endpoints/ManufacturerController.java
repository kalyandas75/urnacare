package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.ManufacturerDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.ManufacturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ManufacturerDTO> getAll() {
        return this.service.getAll();
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
        return this.service.save(manufacturerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ManufacturerDTO create(@PathVariable Long id, @Valid @RequestBody ManufacturerDTO manufacturerDTO) {
        manufacturerDTO.setId(id);
        return this.service.save(manufacturerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
