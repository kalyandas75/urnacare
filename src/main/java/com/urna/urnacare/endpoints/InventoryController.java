package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.DrugDTO;
import com.urna.urnacare.dto.InventoryDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.errors.InternalServerErrorException;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.InventoryService;
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
@RequestMapping("/api/inventories")
@Slf4j
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("search")
    public ResponseEntity<List<InventoryDTO>> search(@RequestParam("q") String q) {
        final List<InventoryDTO> inventories = service.searchByBrandOrComposition(q);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(inventories);
        return new ResponseEntity<>(inventories, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAll(Pageable pageable) {
        final Page<InventoryDTO> page = service.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getOne(@PathVariable Long id) {
        log.debug("REST request to get inventory : {}", id);
        return ResponseUtil.wrapOrNotFound(
                service.getOne(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<InventoryDTO> create(@Valid @RequestBody InventoryDTO dto) throws URISyntaxException {
        log.debug("REST request to save Inventory : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new inventory cannot already have an ID", "inventory", "idexists");
        } else {
            InventoryDTO newInventory = service.create(dto);
            if(newInventory == null) {
                throw new InternalServerErrorException("Could not create inventory");
            }
            return ResponseEntity.created(new URI("/api/inventories/" + newInventory.getId()))
                    .headers(HeaderUtil.createAlert( "inventory.created", String.valueOf(newInventory.getId())))
                    .body(newInventory);
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<InventoryDTO> update(@PathVariable Long id, @Valid @RequestBody InventoryDTO dto) {
        log.debug("REST request to update inventory : {}", dto);
        dto.setId(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(service.update(id, dto)),
                HeaderUtil.createAlert("inventory.updated", dto.getId() + ""));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
