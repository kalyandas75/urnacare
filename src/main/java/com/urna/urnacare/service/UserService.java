package com.urna.urnacare.service;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.DoctorDTO;
import com.urna.urnacare.dto.DoctorRegistrationDTO;
import com.urna.urnacare.dto.PatientRegistrationDTO;
import com.urna.urnacare.dto.UserDTO;
import com.urna.urnacare.errors.EmailAlreadyUsedException;
import com.urna.urnacare.errors.InvalidPasswordException;
import com.urna.urnacare.mapper.DoctorMapper;
import com.urna.urnacare.mapper.UserMapper;
import com.urna.urnacare.repository.DoctorRepository;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.security.SecurityUtils;
import com.urna.urnacare.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final DoctorRepository doctorRepository;

    private final UserMapper userMapper;

    private final DoctorMapper doctorMapper;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, DoctorRepository doctorRepository, UserMapper userMapper, DoctorMapper doctorMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.doctorRepository = doctorRepository;
        this.userMapper = userMapper;
        this.doctorMapper = doctorMapper;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    log.debug("Activated user: {}", user);
                    return this.userRepository.save(user);
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    return user;
                });
    }

    public Doctor registerDoctor(DoctorRegistrationDTO doctorRegistrationDTO) {
        userRepository.findOneByEmailIgnoreCase(doctorRegistrationDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        Doctor doctor = this.doctorMapper.toEntity(doctorRegistrationDTO);
        doctor.setEmail(doctorRegistrationDTO.getEmail().toLowerCase());
        doctor.setPassword(passwordEncoder.encode(doctorRegistrationDTO.getPassword()));
        doctor.setActivated(false);
        doctor.setActivationKey(RandomUtil.generateActivationKey());
        doctor.setAuthority(AuthoritiesConstants.DOCTOR);
        doctor.setCreatedBy("system");
        doctor.setLastModifiedBy("system");
        return doctorRepository.save(doctor);
    }

    public User registerPatient(PatientRegistrationDTO patientRegistrationDTO) {
        userRepository.findOneByEmailIgnoreCase(patientRegistrationDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User user = this.userMapper.toEntity(patientRegistrationDTO);
        user.setEmail(patientRegistrationDTO.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(patientRegistrationDTO.getPassword()));
        user.setActivated(false);
        user.setActivationKey(RandomUtil.generateActivationKey());
        user.setAuthority(AuthoritiesConstants.PATIENT);
        user.setCreatedBy("system");
        user.setLastModifiedBy("system");
        return userRepository.save(user);
    }


    private boolean removeNonActivatedUser(User existingUser){
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }


    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    this.userRepository.save(user);
                    log.debug("Changed password for User: {}", user);
                });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getEmail());
                    userRepository.delete(user);
                });
    }

    public Optional<Doctor> findDoctorByEmail() {
        return SecurityUtils.getCurrentUserLogin().flatMap(doctorRepository::findOneByEmailIgnoreCase);
    }

    public Optional<User> findUserByEmail() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByEmailIgnoreCase);
    }

}
