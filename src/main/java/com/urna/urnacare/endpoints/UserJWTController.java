package com.urna.urnacare.endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urna.urnacare.errors.InternalServerErrorException;
import com.urna.urnacare.mapper.UserMapper;
import com.urna.urnacare.service.UserService;
import com.urna.urnacare.dto.LoginDTO;
import com.urna.urnacare.dto.UserDTO;
import com.urna.urnacare.security.jwt.JWTFilter;
import com.urna.urnacare.security.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final UserMapper userMapper;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager
            , UserService userService,
                             UserMapper userMapper) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginDTO.getRememberMe() == null) ? false : loginDTO.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        UserDTO account = userService.findUserByEmail()
                .map(u -> userMapper.toDto(u))
                .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
        return new ResponseEntity<>(new JWTToken(jwt, account), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private UserDTO account;

        JWTToken(String idToken, UserDTO account) {
            this.idToken = idToken;
            this.account = account;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public UserDTO getAccount() {
            return account;
        }

        public void setAccount(UserDTO account) {
            this.account = account;
        }
    }
}
