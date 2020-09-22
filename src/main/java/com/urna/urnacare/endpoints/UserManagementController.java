package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.UserDTO;
import com.urna.urnacare.dto.UserRegistrationDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.UserMapper;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.service.MailService;
import com.urna.urnacare.service.UserService;
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

@RestController
@RequestMapping("/api")
@Slf4j
public class UserManagementController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final MailService mailService;

    public UserManagementController(UserService userService, UserMapper userMapper, MailService mailService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserRegistrationDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
        } else {
            User newUser = userService.createUser(userDTO);
            this.mailService.sendPasswordResetMail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getEmail()))
                    .headers(HeaderUtil.createAlert( "userManagement.created", String.valueOf(newUser.getId())))
                    .body(this.userMapper.toDto(newUser));
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN + "\",\"" + AuthoritiesConstants.SUPPORT + "\")")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        return ResponseUtil.wrapOrNotFound(
                userService.getUser(id));

    }
}
