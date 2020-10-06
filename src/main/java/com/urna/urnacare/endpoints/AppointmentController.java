package com.urna.urnacare.endpoints;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.AppointmentDto;
import com.urna.urnacare.dto.ConsultationDto;
import com.urna.urnacare.repository.AppointmentRepository;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.AuthoritiesConstants;
import com.urna.urnacare.security.SecurityUtils;
import com.urna.urnacare.service.AppointmentService;
import com.urna.urnacare.util.RandomUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
	private final AppointmentRepository appointmentRepository;
	private final AppointmentService appointmentService;
	private final UserRepository userRepository;

	public AppointmentController(AppointmentRepository appointmentRepository, AppointmentService appointmentService, UserRepository userRepository) {
		this.appointmentRepository = appointmentRepository;
		this.appointmentService = appointmentService;
		this.userRepository = userRepository;
	}

	@PostMapping("/appointment")
	public Appointment getUpdateAppointment(@Valid @RequestBody Appointment appointment) {
		Calendar cal= Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR,24);
		
		Timestamp apptEndTime= new Timestamp(cal.getTimeInMillis());
		appointment.setApptEndTime(apptEndTime);
		appointment.setApptEndTimeN(cal.getTimeInMillis());
		appointment.setUniquieKey(RandomUtil.generateRandomAlphaNumeric(10));
		appointmentRepository.save(appointment);
		return appointment;
		
	}
	
	
	@PostMapping("/inquire/appointment")
	public Iterable<Appointment> getValidateAppointment(@Valid @RequestBody Appointment appointment) {
		
		Iterable<Appointment> appointmentByUniquieKey = appointmentRepository.getAppointmentByUniquieKey(appointment.getUniquieKey());
		
		Calendar cal= Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR,24);
		Timestamp timeStampNow= new Timestamp(cal.getTimeInMillis());
		appointmentByUniquieKey.forEach((app)->{
			Timestamp timeStampApp = app.getApptEndTime();
			long timems=timeStampNow.getTime()-timeStampApp.getTime();
			int hours=(int)timems/3600000;
			if (hours>24) {
				throw new RuntimeException("Appointment not valid !");
			}
		});
		return appointmentByUniquieKey;
	}

	@GetMapping("/pending")
	@PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DOCTOR + "\",\"" + AuthoritiesConstants.PATIENT + "\")")
	public List<AppointmentDto> pendingAppointments() {
		User user = SecurityUtils.getCurrentUserLogin()
				.map(userRepository::findOneByEmailIgnoreCase)
				.get().get();
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PATIENT)) {
			return this.appointmentService.pendingAppointmentsForPatient(user.getId());
		} else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DOCTOR)) {
			return this.appointmentService.pendingAppointmentsForDoctor(user.getId());
		}
		throw new AccessDeniedException("Illegal Access");
	}

	@GetMapping("/completed")
	@PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DOCTOR + "\",\"" + AuthoritiesConstants.PATIENT + "\")")
	public List<AppointmentDto> completedAppointments() {
		User user = SecurityUtils.getCurrentUserLogin()
				.map(userRepository::findOneByEmailIgnoreCase)
				.get().get();
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PATIENT)) {
			return this.appointmentService.pastAppointmentsForPatient(user.getId());
		}
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DOCTOR)) {
			return this.appointmentService.pastAppointmentsForDoctor(user.getId());
		}
		throw new AccessDeniedException("Illegal access");
	}

	@PutMapping("/{id}/consultations")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.DOCTOR + "\")")
	public void createConsultation(@PathVariable Long id, @Valid @RequestBody ConsultationDto consultationDto) {
		this.appointmentService.createConsultation(id, consultationDto);
	}

	@GetMapping(value = "/{id}/consultations")
	@PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DOCTOR + "\",\"" + AuthoritiesConstants.PATIENT + "\")")
	public ConsultationDto getConsultationByAppointmentId(@PathVariable Long id) {
		return this.appointmentService.getConsultation(id);
	}
}
