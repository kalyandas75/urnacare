package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.AppointmentRequestDto;
import com.urna.urnacare.dto.AppointmentRequestTimeDto;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.security.SecurityUtils;
import com.urna.urnacare.service.AppointmentRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/appointment-requests")
public class AppointmentRequestController {
    private final AppointmentRequestService requestService;
    private final UserRepository userRepository;

    public AppointmentRequestController(AppointmentRequestService requestService, UserRepository userRepository) {
        this.requestService = requestService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.PATIENT + "\")")
    public AppointmentRequestDto create(@Valid @RequestBody AppointmentRequestDto appointmentRequest) {
        User patient = SecurityUtils.getCurrentUserLogin().map(userRepository::findOneByEmailIgnoreCase).get().get();
        appointmentRequest.setPatientId(patient.getId());
        return this.requestService.create(appointmentRequest);
    }

    @PostMapping("/approve/{id}")
    @ResponseBody
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
    public Appointment approve(@PathVariable Long id, @RequestBody AppointmentRequestTimeDto appointmentRequestTimeDto) {
        return this.requestService.approve(id, appointmentRequestTimeDto.getRequestTime());
    }

    @PostMapping("/reject/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
    public void reject(@PathVariable Long id, @RequestBody String rejectReason, @ApiIgnore HttpSession session) {
        this.requestService.reject(id, rejectReason);
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.PATIENT + "\")")
    public List<AppointmentRequestDto> getAllForPatient() {
        User patient = SecurityUtils.getCurrentUserLogin().map(userRepository::findOneByEmailIgnoreCase).get().get();
        return this.requestService.getAllForPatient(patient.getId());
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
    public List<AppointmentRequestDto> getAllForDoctor() {
        User doctor = SecurityUtils.getCurrentUserLogin().map(userRepository::findOneByEmailIgnoreCase).get().get();
        return this.requestService.getAllForDoctor(doctor.getId());
    }
}
