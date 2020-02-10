package fr.cooptalent.neodrive.controller;

import fr.cooptalent.neodrive.domain.State;
import fr.cooptalent.neodrive.repository.StateRepository;
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
public class StateController {
    private final Logger log = LoggerFactory.getLogger(StateController.class);
    private final StateRepository stateRepository;

    public StateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @GetMapping("/states")
    public ResponseEntity<List<State>> getAll(Pageable pageable) {
        final Page<State> page = this.stateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
