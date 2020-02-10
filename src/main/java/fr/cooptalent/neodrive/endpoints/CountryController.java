package fr.cooptalent.neodrive.endpoints;

import fr.cooptalent.neodrive.domain.Country;
import fr.cooptalent.neodrive.repository.CountryRepository;
import fr.cooptalent.neodrive.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {
    private final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final CountryRepository stateRepository;

    public CountryController(CountryRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAll(Pageable pageable) {
        final Page<Country> page = this.stateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/countries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
