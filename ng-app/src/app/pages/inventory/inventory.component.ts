import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from 'ngx-toastr';
import { Subscription } from "rxjs";
import { ConfirmationComponent } from 'src/app/components/confirmation/confirmation.component';
import { InventoryEditComponent } from "./inventory-edit/inventory-edit.component";
import { InventoryManagementService } from "./inventory-management.service";

@Component({
  selector: "app-inventory",
  templateUrl: "./inventory.component.html",
  styleUrls: ["./inventory.component.scss"],
})
export class InventoryComponent implements OnInit, OnDestroy {
  page = 1;
  sort = "drug.brand,asc";
  size = 20;
  inventories: any[];
  totalSize = 0;
  reloadEmitterSubscription: Subscription;

  constructor(
    private inventoryManagementServ: InventoryManagementService,
    private modalService: NgbModal,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadAll();
    this.reloadEmitterSubscription = this.inventoryManagementServ.reloadEmitter.subscribe(
      () => {
        this.loadAll();
      }
    );
  }

  ngOnDestroy(): void {
    this.reloadEmitterSubscription.unsubscribe();
  }

  loadAll() {
    this.inventoryManagementServ
      .getAll({
        page: this.page - 1,
        size: this.size,
        sort: this.sort,
      })
      .subscribe((res) => {
        this.inventories = res.body as any[];
        this.totalSize = Number(res.headers.get("x-total-count"));
        console.log(this.inventories);
      });
  }

  pageChange() {
    this.loadAll();
  }

  open(inventory?: any) {
    const modalRef = this.modalService.open(InventoryEditComponent, {
      size: "lg",
      scrollable: true,
      centered: true,
      backdrop: "static",
    });
    modalRef.componentInstance.inventory = Object.assign({}, inventory);
  }

  delete(inventory) {
    const modalRef = this.modalService.open(ConfirmationComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.message = 'Do you want to delete ' + inventory.id + '?';
    modalRef.result.then(r => {
      console.log(r);
      if(r === 'Y') {
        this.inventoryManagementServ.delete(inventory.id)
        .subscribe(() => {
          this.toastr.success('inventory delete successfully');
          this.inventoryManagementServ.reloadEmitter.emit();
        });
      }
    })
    .catch((r) => console.log(r));
  }
}
