import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PHONE_REGEX, PIN_REGEX } from 'src/app/app.constant';
import { OrderService } from 'src/app/shared/service/order.service';

@Component({
  selector: 'app-order-finish',
  templateUrl: './order-finish.component.html',
  styleUrls: ['./order-finish.component.scss']
})
export class OrderFinishComponent implements OnInit {
  form: FormGroup;
  submitted = false;
  order;
  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private orderService: OrderService,
    private router: Router) { }

  ngOnInit(): void {
    this.order = this.orderService.orderUnderProcess;
    this.initForm();
  }

  initForm() {
    this.form = this.fb.group({
      phoneNumber: ['', [Validators.pattern(PHONE_REGEX)]],
      address: ['', [Validators.required]],
      city: ['', [Validators.required]],
      state: ['', [Validators.required]],
      pin: ['', [Validators.required, Validators.pattern(PIN_REGEX)]],
    });
  }

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;
    if(this.form.invalid) {
      return;
    }
    this.orderService.updateShippingAddress(this.order.id, this.form.value)
    .subscribe(res => {
      this.router.navigateByUrl('/order/summary/' + this.order.id);
    });
  }

}
