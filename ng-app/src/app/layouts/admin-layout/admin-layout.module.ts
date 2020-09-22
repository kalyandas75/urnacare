import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { AdminLayoutRoutes } from "./admin-layout.routing";
import { DashboardComponent } from "../../pages/dashboard/dashboard.component";

import { NgbModule, NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { SharedModule } from 'src/app/shared/shared.module';
import { IngredientComponent } from 'src/app/pages/ingredient/ingredient.component';
import { PatientAppointmentRequestComponent } from 'src/app/pages/appointment/patient-appointment-request/patient-appointment-request.component';
import { DoctorAppointmentRequestComponent } from 'src/app/pages/appointment/doctor-appointment-request/doctor-appointment-request.component';
import { PatientAppointmentComponent } from 'src/app/pages/appointment/patient-appointment.component';
import { DoctorAppointmentComponent } from 'src/app/pages/appointment/doctor-appointment.component';
import { SearchDoctorComponent } from 'src/app/pages/appointment/search-doctor/search-doctor.component';
import { AppointmentRequestEditComponent } from 'src/app/pages/appointment/appointment-request-edit/appointment-request-edit.component';
import { PatientAppointmentListComponent } from 'src/app/pages/appointment/patient-appointment-list/patient-appointment-list.component';
import { DoctorAppointmentListComponent } from 'src/app/pages/appointment/doctor-appointment-list/doctor-appointment-list.component';
import { ConsultationEditComponent } from 'src/app/pages/appointment/consultation-edit/consultation-edit.component';
import { ConsultationViewComponent } from 'src/app/pages/appointment/consultation-view/consultation-view.component';
import { ChatComponent } from 'src/app/pages/chat/chat.component';
import { FormlyModule } from '@ngx-formly/core';
import { ChangePasswordComponent } from 'src/app/pages/profile/change-password/change-password.component';
import { ProfileComponent } from 'src/app/pages/profile/profile.component';
import { CompositionComponent } from 'src/app/pages/composition/composition.component';
import { InventoryComponent } from 'src/app/pages/inventory/inventory.component';
import { ManufacturerComponent } from 'src/app/pages/manufacturer/manufacturer.component';
import { UserManagementComponent } from 'src/app/pages/user-management/user-management.component';
import { UserManagementEditComponent } from 'src/app/pages/user-management/user-management-edit/user-management-edit.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    SharedModule,
    NgbModule,
    ReactiveFormsModule,
    NgbModalModule,
    FormlyModule
  ],
  declarations: [
    DashboardComponent,
    IngredientComponent,
    PatientAppointmentRequestComponent,
    DoctorAppointmentRequestComponent,
    PatientAppointmentComponent,
    DoctorAppointmentComponent,
    SearchDoctorComponent,
    AppointmentRequestEditComponent,
    PatientAppointmentListComponent,
    DoctorAppointmentListComponent,
    ConsultationEditComponent,
    ConsultationViewComponent,
    ChatComponent,
    ChangePasswordComponent, 
    ProfileComponent,
    CompositionComponent,
    ManufacturerComponent,
    InventoryComponent,
    UserManagementComponent,
    UserManagementEditComponent
  ],
  entryComponents: [
    AppointmentRequestEditComponent,
    ConsultationEditComponent,
    ConsultationViewComponent,
    ChatComponent,
    UserManagementEditComponent
  ]
})
export class AdminLayoutModule {}
