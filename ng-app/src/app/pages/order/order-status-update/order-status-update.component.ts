import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { OrderService } from 'src/app/shared/service/order.service';

@Component({
  selector: 'app-order-status-update',
  templateUrl: './order-status-update.component.html',
  styleUrls: ['./order-status-update.component.scss']
})
export class OrderStatusUpdateComponent implements OnInit {

  statusForm: FormGroup;
  submitted = false;
  loading = false;
  order;
  constructor(public activeModal: NgbActiveModal, private fb: FormBuilder, private orderService: OrderService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.statusForm = this.fb.group({
      status: ['', [Validators.required]],
      comments: ['', [Validators.required]]
    })
  }
  get f() {
    return this.statusForm.controls;
  }
  onSubmit() {
    this.submitted = true;
    if(this.statusForm.invalid) return;
    console.log(this.statusForm.value);
    this.orderService.updateStatus(this.order.id, this.statusForm.value)
    .subscribe(res => {
      this.toastr.success('Status updated successfully');
      this.activeModal.close(res.body);
    })
  }

}
