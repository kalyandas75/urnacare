import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/shared/service/account.service';
import { OrderService } from 'src/app/shared/service/order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {
  page = 1;
  sort = 'id,desc';
  size = 20;
  totalSize = 0;
  orders: any[];
  constructor(private orderService: OrderService, private accountService: AccountService) { }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.orderService.getAll({ page: this.page - 1, size: this.size, sort: this.sort})
    .subscribe(res => {
      this.orders = res.body as any[];
      console.log('ORDERS', this.orders);
      this.totalSize = Number(res.headers.get('x-total-count'));
    });
  }

  getTotal(item) {
    return (item.quantity * item.pricePerUnit);
  }
  getDiscount(item) {
    let discount = item.discountRate;
    if(!discount) {
      discount = 0;
    }
    return this.getTotal(item) * discount / 100;
  }
  getTaxes(item) {
    const total = this.getTotal(item);
    const discount = this.getDiscount(item);
    const net = total - discount;
    let taxRate = (item.cgst ? item.cgst : 0) + (item.sgst ? item.sgst : 0) + (item.igst ? item.igst : 0);
    return net * taxRate/ 100;
  }
  getNet(item) {
    return this.getTotal(item) - this.getDiscount(item) + this.getTaxes(item);
  }

  getTotalOfItems(items) {
    let total = 0;
    if(items && items.length > 0) {
      items.forEach(item => {
        total+= this.getNet(item);
      });
    }
    return total;
  }
  pageChange() {
    this.loadAll();
  }
}
