package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.*;
import com.urna.urnacare.errors.EmailAlreadyUsedException;
import com.urna.urnacare.errors.EmailNotFoundException;
import com.urna.urnacare.errors.InternalServerErrorException;
import com.urna.urnacare.errors.InvalidPasswordException;
import com.urna.urnacare.mapper.DoctorMapper;
import com.urna.urnacare.mapper.UserMapper;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.security.SecurityUtils;
import com.urna.urnacare.service.DoctorService;
import com.urna.urnacare.service.MailService;
import com.urna.urnacare.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

import static com.urna.urnacare.dto.DoctorRegistrationDTO.PASSWORD_MAX_LENGTH;
import static com.urna.urnacare.dto.DoctorRegistrationDTO.PASSWORD_MIN_LENGTH;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class AccountController {
    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final UserMapper userMapper;

    private final DoctorMapper doctorMapper;

    private final DoctorService doctorService;


    public AccountController(UserRepository userRepository, UserService userService, MailService mailService, UserMapper userMapper, DoctorMapper doctorMapper, DoctorService doctorService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.doctorMapper = doctorMapper;
        this.doctorService = doctorService;
    }

    @PostMapping("/register-doctor")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDoctor(@Valid @RequestBody DoctorRegistrationDTO registrationDTO) {
        if (!checkPasswordLength(registrationDTO.getPassword())) {
            throw new InvalidPasswordException();
        }
        Doctor user = userService.registerDoctor(registrationDTO);
        mailService.sendActivationEmail(user);
    }

    @PostMapping("/register-patient")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPatient(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        if (!checkPasswordLength(registrationDTO.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerPatient(registrationDTO);
        mailService.sendActivationEmail(user);
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DOCTOR)) {
            return userService.findDoctorByEmail()
                    .map(doctor -> this.doctorMapper.toDto(doctor))
                    .orElseThrow(() -> new AccountResourceException("Doctor not found"));
        } else {
            return userService.findUserByEmail()
                    .map(user -> this.userMapper.toDto(user))
                    .orElseThrow(() -> new AccountResourceException("User not found"));
        }
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */

    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getEmail().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        userService.update(userDTO);

    }

    @PostMapping("/account-doctor")
    public void saveDoctorAccount(@Valid @RequestBody DoctorDTO doctorDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(doctorDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getEmail().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DOCTOR)) {
            this.doctorService.update(doctorDTO);
        } else {
            userService.update(doctorDTO);
        }
    }



    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= PASSWORD_MIN_LENGTH &&
            password.length() <= PASSWORD_MAX_LENGTH;
    }
}
