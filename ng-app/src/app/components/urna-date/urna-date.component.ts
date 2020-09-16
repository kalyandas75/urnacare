import { Component, OnInit } from '@angular/core';
import { Field } from '@ngx-formly/core';

@Component({
  selector: 'app-urna-date',
  template: `
  <div class="form-group">
    <div class="input-group">
      <input class="form-control" placeholder="yyyy-mm-dd" name="dp" [formControl]="formControl" ngbDatepicker #d="ngbDatepicker">
      <div class="input-group-append">
        <button class="btn btn-sm btn-outline-secondary icon-calendar-60" (click)="d.toggle()" type="button"></button>
      </div>
    </div>
  </div>
  `,
  styles: [
  ]
})
export class UrnaDateComponent extends Field {

}
