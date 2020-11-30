import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { OrderService } from 'src/app/shared/service/order.service';

@Component({
  selector: 'app-order-init',
  templateUrl: './order-init.component.html',
  styleUrls: ['./order-init.component.scss']
})
export class OrderInitComponent implements OnInit {

  orderItems: any[];
  consultationId;

  constructor(private orderService: OrderService, 
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.consultationId = params.get('consultationId');
      this.load();
    });
  }

  load() {
    this.orderService.initOrder(this.consultationId)
    .subscribe(res => {
      this.orderItems = res.body as any[];
    });
  }

  getNetAmount(item) {
    const pricePerUnit = item.pricePerUnit;
    const qty = item.quantity;
    let netAmount = pricePerUnit * qty;
    netAmount = netAmount - (netAmount * item.discountRate) / 100;
    let cgst = 0;
    let sgst = 0;
    let igst = 0;
    if(item.cgst) {
      cgst = (item.cgst * netAmount)/100;
    }
    if(item.sgst) {
      sgst = (item.sgst * netAmount)/100;
    }
    if(item.igst) {
      igst = (item.igst * netAmount)/100;
    }
    netAmount = netAmount + cgst + sgst + igst;
    return netAmount;
  }

  getTotal() {
    let total = 0;
    if(this.orderItems && this.orderItems.length > 0) {
      for (let index = 0; index < this.orderItems.length; index++) {
        const item = this.orderItems[index];
        if(!item.unavailable) {
          total+= this.getNetAmount(item);
        }
        
      }
    }
    return total;
  }

  onConfirm() {
    const drugQty = [];
    if(this.orderItems && this.orderItems.length > 0) {
      for (let index = 0; index < this.orderItems.length; index++) {
        const item = this.orderItems[index];
        drugQty.push({
          drugId: item.drugId,
          quantity: item.quantity
        });      
      }
    }
    this.orderService.finishOrder(this.consultationId, drugQty)
    .subscribe(res => {
      this.orderService.orderUnderProcess = res.body;
      this.router.navigateByUrl('/order/finish');
    }, err => {
      console.log(err);
      this.toastr.error('Oops something wrong happened.', 'Error');
    })
  }

}
