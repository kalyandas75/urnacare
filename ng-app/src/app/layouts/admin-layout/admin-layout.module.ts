import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

import { AdminLayoutRoutes } from "./admin-layout.routing";
import { DashboardComponent } from "../../pages/dashboard/dashboard.component";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { SharedModule } from 'src/app/shared/shared.module';
import { IngredientComponent } from 'src/app/pages/ingredient/ingredient.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    SharedModule
  ],
  declarations: [
    DashboardComponent,
    IngredientComponent
  ]
})
export class AdminLayoutModule {}
