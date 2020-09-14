import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  registerPatient(patient) {
    return this.http.post(SERVICE_API_URL + '/register-patient', patient, { observe: 'response'});
  }

  registerDoctor(doctor) {
    return this.http.post(SERVICE_API_URL + '/register-doctor', doctor, { observe: 'response'});
  }

  activate(key: string) {
    return this.http.get(SERVICE_API_URL + '/activate?key=' + key);
  }
}
