import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  getPaymentDetails(orderId) {
    return this.http.get(SERVICE_API_URL + '/payments/init/' + orderId, { observe: 'response'});
  }
}
