import { Routes } from "@angular/router";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { TitleResolver } from "src/app/shared/title.resolve";
import { UserRouteAccessService } from "src/app/shared/service/user-route-access-service";
import { IngredientComponent } from "src/app/pages/ingredient/ingredient.component";
import { PatientAppointmentComponent } from "src/app/pages/appointment/patient-appointment.component";
import { DoctorAppointmentComponent } from "src/app/pages/appointment/doctor-appointment.component";
import { ChangePasswordComponent } from "src/app/pages/profile/change-password/change-password.component";
import { ProfileComponent } from "src/app/pages/profile/profile.component";
import { CompositionComponent } from "src/app/pages/composition/composition.component";
import { ManufacturerComponent } from "src/app/pages/manufacturer/manufacturer.component";
import { UserManagementComponent } from "src/app/pages/user-management/user-management.component";
import { InventoryComponent } from "src/app/pages/inventory/inventory.component";

export const AdminLayoutRoutes: Routes = [
  {
    path: "dashboard",
    component: DashboardComponent,
    data: {
      title: "dashboard.pageTitle",
      authorities: [],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "ingredients",
    component: IngredientComponent,
    data: {
      title: "dashboard.pageTitle",
      authorities: ["ROLE_ADMIN", "ROLE_SUPPORT"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "patient-appointment",
    component: PatientAppointmentComponent,
    data: {
      title: "patient.appointment",
      authorities: ["ROLE_PATIENT"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "doctor-appointment",
    component: DoctorAppointmentComponent,
    data: {
      title: "doctor.appointment",
      authorities: ["ROLE_DOCTOR"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "change-password",
    component: ChangePasswordComponent,
    data: {
      title: "changePassword.title",
      authorities: [],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "profile",
    component: ProfileComponent,
    data: {
      title: "profile.title",
      authorities: [],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "composition",
    component: CompositionComponent,
    data: {
      title: "composition.title",
      authorities: ["ROLE_ADMIN", "ROLE_SUPPORT"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "manufacturer",
    component: ManufacturerComponent,
    data: {
      title: "manufacturer.title",
      authorities: ["ROLE_ADMIN", "ROLE_SUPPORT"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "inventory",
    component: InventoryComponent,
    data: {
      title: "inventory.title",
      authorities: ["ROLE_ADMIN", "ROLE_SUPPORT"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "user-management",
    component: UserManagementComponent,
    data: {
      title: "userManagement.title",
      authorities: ["ROLE_ADMIN"],
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
];
