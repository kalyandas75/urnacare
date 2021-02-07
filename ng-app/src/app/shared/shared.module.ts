import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HasAnyAuthorityDirective } from './directive/has-any-authority.directive';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule } from 'ngx-spinner';



@NgModule({
  declarations: [
    HasAnyAuthorityDirective
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule
  ],
  exports: [
    HasAnyAuthorityDirective,
    NgxSpinnerModule
  ]
})
export class SharedModule { }
