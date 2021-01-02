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
import { DrugComponent } from 'src/app/pages/drug/drug.component';
import { OrderInitComponent } from 'src/app/pages/order/order-init/order-init.component';
import { OrderFinishComponent } from 'src/app/pages/order/order-finish/order-finish.component';
import { OrderSummaryComponent } from 'src/app/pages/order/order-summary/order-summary.component';
import { PaymentResultComponent } from 'src/app/pages/order/payment-result/payment-result.component';
import { JChatComponent } from "src/app/pages/jchat/jchat.component";
import { OrderListComponent } from "src/app/pages/order/order-list/order-list.component";

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
  {
    path: "drug",
    component: DrugComponent,
    data: {
      title: "drug.title",
      authorities: ["ROLE_ADMIN", "ROLE_SUPPORT"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "order/init/:consultationId",
    component: OrderInitComponent,
    data: {
      title: "orderInit.title",
      authorities: ["ROLE_PATIENT"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "order/finish",
    component: OrderFinishComponent,
    data: {
      title: "orderFinal.title",
      authorities: ["ROLE_PATIENT"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "order/summary/:orderId",
    component: OrderSummaryComponent,
    data: {
      title: "orderSummary.title",
      authorities: ["ROLE_PATIENT"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "payment/result",
    component: PaymentResultComponent,
    data: {
      title: "paymentResult.title",
      authorities: ["ROLE_PATIENT"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "chat/:roomId",
    component: JChatComponent,
    data: {
      title: "jChat.title",
      authorities: ["ROLE_PATIENT", "ROLE_DOCTOR"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: "orders",
    component: OrderListComponent,
    data: {
      title: "orders.title",
      authorities: ["ROLE_PATIENT", "ROLE_ADMIN", "ROLE_ADMIN"]
    },
    resolve: {
      title: TitleResolver,
    },
    canActivate: [UserRouteAccessService],
  }
];
