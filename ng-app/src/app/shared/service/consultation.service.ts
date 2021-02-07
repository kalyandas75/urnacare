import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {

  getConsultationByIdUrl = '/api/appointments/17/consultations'
  constructor(private http: HttpClient) { }

  getConsultationById(consultationId: number) {
   // this.http.get('/rest/urna/appointments/' /consultations')
  }

  downloadPrescription(consultationId: number) {
    return this.http.get(SERVICE_API_URL + '/consultations/' + consultationId + '/download', {responseType: 'blob'});
  }
}
