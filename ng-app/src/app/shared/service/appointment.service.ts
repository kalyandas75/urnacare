import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private doctorAppointmentRequestUrl = '/api/appointment-requests/doctor';
  private patientAppointmentRequestUrl = '/api/appointment-requests/patient';
  private searchDoctorBySpeciality = '/api/doctors/speciality/doctors/';
  private createAppointmentRequestUrl = '/api/appointment-requests';
  private appointmentRequestApproveUrl = '/api/appointment-requests/approve/';
  private appointmentRequestRejectUrl = '/api/appointment-requests/reject/';
  private pendingAppointmentsUrl = '/api/appointments/pending';
  private completedAppointmentsUrl = '/api/appointments/completed';


  refreshListEmitter = new EventEmitter();
  
  constructor(private http: HttpClient) { }

  getAppointmentRequestsForPatient() {
    return this.http.get(this.patientAppointmentRequestUrl, { observe: 'response'});
  }

  getAppointmentRequestsForDoctor() {
    return this.http.get(this.doctorAppointmentRequestUrl, { observe: 'response'});
  }

  searchDoctor(speciality: string) {
    return this.http.get(this.searchDoctorBySpeciality + speciality, { observe: 'response'});
  }

  createAppointmentRequest(ar) {
    return this.http.post(this.createAppointmentRequestUrl, ar, { observe: 'response'});
  }

  approveAppointmentRequest(id, scheduleDateTime) {
    return this.http.post(this.appointmentRequestApproveUrl + id , {
      requestTime: scheduleDateTime
    }, { observe: 'response'});
  }

  rejectAppointmentRequest(id, reason) {
    return this.http.post(this.appointmentRequestRejectUrl + id , reason, { observe: 'response'});
  }

  pendingAppointments() {
    return this.http.get(this.pendingAppointmentsUrl, { observe: 'response'});
  }

  completedAppointments() {
    return this.http.get(this.completedAppointmentsUrl, { observe: 'response'});
  }

  addConsultation(appointmentId, consultation) {
    return this.http.put('/api/appointments/' + appointmentId + '/consultations', consultation);
  }

  getConsulationByAppointmentId(appointmentId) {
    return this.http.get('/api/appointments/' + appointmentId + '/consultations',{ observe: 'response'});
  }
}
