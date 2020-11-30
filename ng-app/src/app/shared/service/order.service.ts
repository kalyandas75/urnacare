import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVICE_API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  orderUnderProcess;

  constructor(private http: HttpClient) { }

  initOrder(consultationId) {
    // /api/orders/init/{consultationId}
    return this.http.get(SERVICE_API_URL + '/orders/init/' + consultationId, { observe: 'response'});
  }

  finishOrder(consultationId, drugQty: any[]) {
    return this.http.post(SERVICE_API_URL + '/orders/finish/' + consultationId, drugQty, { observe: 'response'});
  }

  updateShippingAddress(orderId, shippingAddress) {
    return this.http.put(SERVICE_API_URL + '/orders/shipping-address/' + orderId, shippingAddress, { observe: 'response'});
  }
}
