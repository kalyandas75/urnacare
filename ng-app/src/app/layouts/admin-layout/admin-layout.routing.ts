import { Routes } from "@angular/router";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { TitleResolver } from 'src/app/shared/title.resolve';
import { UserRouteAccessService } from 'src/app/shared/service/user-route-access-service';
import { IngredientComponent } from 'src/app/pages/ingredient/ingredient.component';

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
      authorities: []
    },
    resolve: {
      title: TitleResolver
    },
    canActivate: [UserRouteAccessService]
  }
];
