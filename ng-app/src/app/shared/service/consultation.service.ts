import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {

  getConsultationByIdUrl = '/api/appointments/17/consultations'
  constructor(private http: HttpClient) { }

  getConsultationById(consultationId: number) {
   // this.http.get('/rest/urna/appointments/' /consultations')
  }
}
