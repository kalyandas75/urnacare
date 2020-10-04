import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from 'ngx-toastr';
import { Subscription } from "rxjs";
import { ConfirmationComponent } from 'src/app/components/confirmation/confirmation.component';
import { CompositionEditComponent } from "./composition-edit/composition-edit.component";
import { CompositionService } from "./composition.service";

@Component({
  selector: "app-composition",
  templateUrl: "./composition.component.html",
  styleUrls: ["./composition.component.scss"],
})
export class CompositionComponent implements OnInit, OnDestroy {
  page = 1;
  sort = 'name,asc';
  size = 20;
  totalSize = 0;
  compositions: any[];
  reloadEmitterSubscription: Subscription;

  constructor(
    private compositionServ: CompositionService,
    private modalService: NgbModal,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadAll();
    this.reloadEmitterSubscription = this.compositionServ.reloadEmitter.subscribe(
      () => {
        this.loadAll();
      }
    );
  }

  ngOnDestroy(): void {
    this.reloadEmitterSubscription.unsubscribe();
  }

  loadAll() {
    this.compositionServ.getAll({ page: this.page - 1, size: this.size, sort: this.sort})
    .subscribe(res => {
      this.compositions = res.body as any[];
      this.totalSize = Number(res.headers.get('x-total-count'));
    })
  }

  pageChange() {
    this.loadAll();
  }
  open(composition?: any) {
    const modalRef = this.modalService.open(CompositionEditComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.composition = Object.assign({}, composition);
  }

  delete(composition) {
    const modalRef = this.modalService.open(ConfirmationComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.message = 'Do you want to delete ' + composition.name + '?';
    modalRef.result.then(r => {
      console.log(r);
      if(r === 'Y') {
        this.compositionServ.delete(composition.id)
        .subscribe(() => {
          this.toastr.success('Composition delete successfully');
          this.compositionServ.reloadEmitter.emit();
        });
      }
    })
    .catch((r) => console.log(r));
  }
}
