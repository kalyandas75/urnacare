import { Component, OnInit } from "@angular/core";
import { AccountService } from 'src/app/shared/service/account.service';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  {
    path: "",
    title: "Appointments",
    icon: "icon-notes",
    class: ""
  },
  {
    path: "",
    title: "Profile",
    icon: "icon-single-02",
    class: ""
  }
];

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.css"]
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.menuItems = ROUTES;
    console.log(this.accountService.isAuthenticated(), this.accountService.isIdentityResolved());
    console.log(this.accountService.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_CUSTOMER_SUPPORT']));
  }
  isMobileMenu() {
    if (window.innerWidth > 991) {
      return false;
    }
    return true;
  }
}
