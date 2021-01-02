import { Component, OnInit } from "@angular/core";
import { AccountService } from 'src/app/shared/service/account.service';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
  authorities: string[]
}
export const ROUTES: RouteInfo[] = [
  {
    path: "/patient-appointment",
    title: "Appointments",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_PATIENT']
  },
  {
    path: "/doctor-appointment",
    title: "Appointments",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_DOCTOR']
  },
  {
    path: "/user-management",
    title: "User Management",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_ADMIN']
  },
  {
    path: "/drug",
    title: "Drugs",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_ADMIN', 'ROLE_SUPPORT']
  },
  {
    path: "/inventory",
    title: "Inventories",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_ADMIN', 'ROLE_SUPPORT']
  },
  {
    path: "/composition",
    title: "Compositions",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_ADMIN', 'ROLE_SUPPORT']
  },
  {
    path: "/manufacturer",
    title: "Manufacturers",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_ADMIN', 'ROLE_SUPPORT']
  },
  {
    path: "/orders",
    title: "Orders",
    icon: "icon-notes",
    class: "",
    authorities: ['ROLE_ADMIN', 'ROLE_SUPPORT', 'ROLE_PATIENT']
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
    console.log(this.accountService.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_SUPPORT']));
  }
  isMobileMenu() {
    if (window.innerWidth > 991) {
      return false;
    }
    return true;
  }
}
