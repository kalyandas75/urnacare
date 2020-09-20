package com.urna.urnacare.service;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.DoctorRegistrationDTO;
import com.urna.urnacare.dto.UserRegistrationDTO;
import com.urna.urnacare.dto.UserDTO;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.errors.EmailAlreadyUsedException;
import com.urna.urnacare.errors.InvalidPasswordException;
import com.urna.urnacare.mapper.AddressMapper;
import com.urna.urnacare.mapper.DoctorMapper;
import com.urna.urnacare.mapper.UserMapper;
import com.urna.urnacare.repository.DoctorRepository;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.security.SecurityUtils;
import com.urna.urnacare.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final DoctorRepository doctorRepository;

    private final UserMapper userMapper;

    private final DoctorMapper doctorMapper;

    private final AddressMapper addressMapper;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, DoctorRepository doctorRepository, UserMapper userMapper, DoctorMapper doctorMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.doctorRepository = doctorRepository;
        this.userMapper = userMapper;
        this.doctorMapper = doctorMapper;
        this.addressMapper = addressMapper;
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
                    return this.userRepository.save(user);
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    return this.userRepository.save(user);
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

    public User registerPatient(UserRegistrationDTO userRegistrationDTO) {
        userRepository.findOneByEmailIgnoreCase(userRegistrationDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User user = this.userMapper.toEntity(userRegistrationDTO);
        user.setEmail(userRegistrationDTO.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
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

    public Optional<UserDTO> update(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setEmail(userDTO.getEmail());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    if(userDTO.getAddresses() != null) {
                        userDTO.getAddresses().stream().forEach(a -> a.setUserId(user.getId()));
                    }
                    user.setAddresses(this.addressMapper.toEntity(userDTO.getAddresses()));
                    user.setAlternatePhoneNumber(userDTO.getAlternatePhoneNumber());
                    user.setPhoneNumber(userDTO.getPhoneNumber());
                    user.setGender(userDTO.getGender());
                    return this.userMapper.toDto(this.userRepository.save(user));
                });
    }

    public UserDTO createUser(UserRegistrationDTO userDTO) {
        this.userRepository.findOneByEmailIgnoreCase(userDTO.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyUsedException();
                });
        User user = this.userMapper.toEntity(userDTO);
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        user.setActivated(true);
        return this.userMapper.toDto(this.userRepository.save(user));
    }

    public Page<UserDTO> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable).map(user -> this.userMapper.toDto(user));
    }

    public Optional<UserDTO> getUser(Long id) {
        return this.userRepository.findById(id).map(user ->this.userMapper.toDto(user));
    }

}
