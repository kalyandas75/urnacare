import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Subscription } from "rxjs";
import { ManufacturerEditComponent } from "./manufacturer-edit/manufacturer-edit.component";
import { ManufacturerService } from "./manufacturer.service";

@Component({
  selector: "app-manufacturer",
  templateUrl: "./manufacturer.component.html",
  styleUrls: ["./manufacturer.component.scss"],
})
export class ManufacturerComponent implements OnInit, OnDestroy {
  manufacturers: any[];
  reloadEmitterSubscription: Subscription;

  constructor(
    private manufacturerServ: ManufacturerService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadAll();
    this.reloadEmitterSubscription = this.manufacturerServ.reloadEmitter.subscribe(
      () => {
        this.loadAll();
      }
    );
  }

  ngOnDestroy(): void {
    this.reloadEmitterSubscription.unsubscribe();
  }

  loadAll() {
    this.manufacturerServ.getAllManufacturers().subscribe((res) => {
      this.manufacturers = res.body as any[];
    });
  }

  open(manufacturer?: any) {
    const modalRef = this.modalService.open(ManufacturerEditComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.manufacturer = Object.assign({}, manufacturer);
  }
}
