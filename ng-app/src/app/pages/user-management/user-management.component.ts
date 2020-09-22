import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { UserManagementEditComponent } from './user-management-edit/user-management-edit.component';
import { UserManagementService } from './user-management.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit, OnDestroy {

  page = 1;
  sort = 'firstName,asc';
  size = 20;
  users: any[];
  totalSize = 0;
  reloadEmitterSubscription: Subscription;
  constructor(private userManagementService: UserManagementService, private modalService: NgbModal) { }


  ngOnInit(): void {
    this.loadAll();
    this.reloadEmitterSubscription = this.userManagementService.reloadEmitter.subscribe(() => {
      this.loadAll();
    });
  }

  ngOnDestroy(): void {
    this.reloadEmitterSubscription.unsubscribe();
  }

  loadAll() {
    this.userManagementService.getAllUsers({ page: this.page - 1, size: this.size, sort: this.sort})
    .subscribe(res => {
      this.users = res.body as any[];
      this.totalSize = Number(res.headers.get('x-total-count'));
    })
  }

  pageChange() {
    this.loadAll();
  }

  open(user?: any) {
    const modalRef = this.modalService.open(UserManagementEditComponent,  { size: 'lg', scrollable: true, centered: false, backdrop: 'static' });
    modalRef.componentInstance.user = Object.assign({}, user);
  }

}
