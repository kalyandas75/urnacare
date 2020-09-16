import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HasAnyAuthorityDirective } from './directive/has-any-authority.directive';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    HasAnyAuthorityDirective
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    HasAnyAuthorityDirective
  ]
})
export class SharedModule { }
