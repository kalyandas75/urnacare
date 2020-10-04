package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.DrugDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.errors.InternalServerErrorException;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.DrugService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/drugs")
@Slf4j
public class DrugController {
    private final DrugService service;

    public DrugController(DrugService service) {
        this.service = service;
    }

    @GetMapping("search")
    public ResponseEntity<List<DrugDTO>> search(@RequestParam("q") String q) {
        final List<DrugDTO> drugs = service.searchByBrandOrComposition(q);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(drugs);
        return new ResponseEntity<>(drugs, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DrugDTO>> getAll(Pageable pageable) {
        final Page<DrugDTO> page = service.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/drugs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrugDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get drug : {}", id);
        return ResponseUtil.wrapOrNotFound(
                service.getOne(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<DrugDTO> create(@Valid @RequestBody DrugDTO dto) throws URISyntaxException {
        log.debug("REST request to save drug : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", "drug", "idexists");
        }
        DrugDTO newdrug = this.service.create(dto);
        if(newdrug == null) {
            throw new InternalServerErrorException("Could not create drug");
        }
        return ResponseEntity.created(new URI("/api/drugs/" + newdrug.getId()))
                .headers(HeaderUtil.createAlert( "drug.created", String.valueOf(newdrug.getId())))
                .body(newdrug);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<DrugDTO> update(@PathVariable Long id, @Valid @RequestBody DrugDTO dto) {
        log.debug("REST request to update drug : {}", dto);
        return ResponseUtil.wrapOrNotFound(Optional.of(service.update(id, dto)),
                HeaderUtil.createAlert("drug.updated", dto.getId() + ""));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
