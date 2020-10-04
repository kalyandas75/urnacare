import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from 'ngx-toastr';
import { Subscription } from "rxjs";
import { ConfirmationComponent } from 'src/app/components/confirmation/confirmation.component';
import { ManufacturerEditComponent } from "./manufacturer-edit/manufacturer-edit.component";
import { ManufacturerService } from "./manufacturer.service";

@Component({
  selector: "app-manufacturer",
  templateUrl: "./manufacturer.component.html",
  styleUrls: ["./manufacturer.component.scss"],
})
export class ManufacturerComponent implements OnInit, OnDestroy {
  page = 1;
  sort = 'name,asc';
  size = 20;
  totalSize = 0;
  manufacturers: any[];
  reloadEmitterSubscription: Subscription;

  constructor(
    private manufacturerServ: ManufacturerService,
    private modalService: NgbModal,
    private toastr: ToastrService
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
    this.manufacturerServ.getAll({ page: this.page - 1, size: this.size, sort: this.sort})
    .subscribe(res => {
      this.manufacturers = res.body as any[];
      this.totalSize = Number(res.headers.get('x-total-count'));
    })
  }

  pageChange() {
    this.loadAll();
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

  delete(manufacturer) {
    const modalRef = this.modalService.open(ConfirmationComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.message = 'Do you want to delete ' + manufacturer.name + '?';
    modalRef.result.then(r => {
      console.log(r);
      if(r === 'Y') {
        this.manufacturerServ.delete(manufacturer.id)
        .subscribe(() => {
          this.toastr.success('manufacturer delete successfully');
          this.manufacturerServ.reloadEmitter.emit();
        });
      }
    })
    .catch((r) => console.log(r));
  }
}
