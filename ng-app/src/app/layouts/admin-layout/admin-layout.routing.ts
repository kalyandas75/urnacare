import { Routes } from "@angular/router";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { TitleResolver } from 'src/app/shared/title.resolve';
import { UserRouteAccessService } from 'src/app/shared/service/user-route-access-service';
import { IngredientComponent } from 'src/app/pages/ingredient/ingredient.component';
import { PatientAppointmentComponent } from 'src/app/pages/appointment/patient-appointment.component';
import { DoctorAppointmentComponent } from 'src/app/pages/appointment/doctor-appointment.component';

export const AdminLayoutRoutes: Routes = [
  {
    path: "dashboard",
    component: DashboardComponent,
    data: {
      title: 'dashboard.pageTitle',
      authorities: []
    },
    resolve: {
      title: TitleResolver
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: "ingredients",
    component: IngredientComponent,
    data: {
      title: 'dashboard.pageTitle',
      authorities: ['ROLE_ADMIN', 'ROLE_CUSTOMER_SUPPORT']
    },
    resolve: {
      title: TitleResolver
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: "patient-appointment",
    component: PatientAppointmentComponent,
    data: {
      title: 'patient.appointment',
      authorities: ['ROLE_PATIENT']
    },
    resolve: {
      title: TitleResolver
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: "doctor-appointment",
    component: DoctorAppointmentComponent,
    data: {
      title: 'doctor.appointment',
      authorities: ['ROLE_DOCTOR']
    },
    resolve: {
      title: TitleResolver
    },
    canActivate: [UserRouteAccessService]
  },
];
