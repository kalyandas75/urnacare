import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/shared/service/payment.service';

@Component({
  selector: 'app-order-summary',
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.scss']
})
export class OrderSummaryComponent implements OnInit {

  orderId;
  paymentRequest;
  total = {
    amount: 0,
    discount: 0,
    gst: 0,
    netAmount: 0
  }

  constructor(private paymentService: PaymentService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.orderId = params.get('orderId');
      console.log(this.orderId);
      this.load();
    });
  }

  load() {
    this.paymentService.getPaymentDetails(this.orderId)
    .subscribe(res => {
      console.log(res.body);
      this.paymentRequest = res.body;
      if(this.paymentRequest && this.paymentRequest.items) {
        const items = this.paymentRequest.items as any[];
        items.forEach(item => {
          console.log('TOTAL', this.total);
          this.total.amount+= item.amount;
          this.total.discount+= item.discount;
          this.total.gst+= item.discount;
          this.total.netAmount+= item.netAmount;
        });
      }
    });
  } 
}
