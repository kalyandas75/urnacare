package fr.cooptalent.neodrive.controller;

import fr.cooptalent.neodrive.domain.Currency;
import fr.cooptalent.neodrive.repository.CurrencyRepository;
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
public class CurrencyController {
    private final Logger log = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyRepository currencyRepository;

    public CurrencyController(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<Currency>> getAll(Pageable pageable) {
        final Page<Currency> page = this.currencyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/currencies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
