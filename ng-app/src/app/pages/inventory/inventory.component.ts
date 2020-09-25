import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Subscription } from "rxjs";
import { InventoryEditComponent } from "./inventory-edit/inventory-edit.component";
import { InventoryManagementService } from "./inventory-management.service";

@Component({
  selector: "app-inventory",
  templateUrl: "./inventory.component.html",
  styleUrls: ["./inventory.component.scss"],
})
export class InventoryComponent implements OnInit, OnDestroy {
  page = 1;
  sort = "brand,asc";
  size = 20;
  inventories: any[];
  totalSize = 0;
  reloadEmitterSubscription: Subscription;

  constructor(
    private inventoryManagementServ: InventoryManagementService,
    private modalService: NgbModal
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
      .getAllInventories({
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
}
