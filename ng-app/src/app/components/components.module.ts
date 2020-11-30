import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { FooterComponent } from "./footer/footer.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { SidebarComponent } from "./sidebar/sidebar.component";
import { SharedModule } from '../shared/shared.module';
import { UrnaDateComponent } from './urna-date/urna-date.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { PaymentComponent } from './payment/payment.component';

@NgModule({
  imports: [CommonModule, RouterModule, NgbModule, SharedModule],
  declarations: [FooterComponent, NavbarComponent, SidebarComponent, UrnaDateComponent, ConfirmationComponent, PaymentComponent],
  exports: [FooterComponent, NavbarComponent, SidebarComponent]
})
export class ComponentsModule {}
