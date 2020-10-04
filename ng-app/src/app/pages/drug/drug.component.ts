import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { ConfirmationComponent } from 'src/app/components/confirmation/confirmation.component';
import { DrugEditComponent } from './drug-edit/drug-edit.component';
import { DrugService } from './drug.service';

@Component({
  selector: 'app-drug',
  templateUrl: './drug.component.html',
  styleUrls: ['./drug.component.scss']
})
export class DrugComponent implements OnInit {

  page = 1;
  sort = 'brand,asc';
  size = 20;
  totalSize = 0;
  drugs: any[];
  reloadEmitterSubscription: Subscription;
  constructor(private drugService: DrugService, private modalService: NgbModal, private toastr: ToastrService) { }


  ngOnInit(): void {
    this.loadAll();
    this.reloadEmitterSubscription = this.drugService.reloadEmitter.subscribe(() => this.loadAll());
  }

  ngOnDestroy(): void {
    this.reloadEmitterSubscription.unsubscribe();
  }

  loadAll() {
    this.drugService.getAll({ page: this.page - 1, size: this.size, sort: this.sort})
    .subscribe(res => {
      this.drugs  = res.body as any[];
      this.totalSize = Number(res.headers.get('x-total-count'));
    })
  }

  pageChange() {
    this.loadAll();
  }

  open(drug?: any) {
    const modalRef = this.modalService.open(DrugEditComponent,  { size: 'lg', scrollable: true, centered: false, backdrop: 'static' });
    modalRef.componentInstance.drug = Object.assign({}, drug);
  }

  delete(drug) {
    const modalRef = this.modalService.open(ConfirmationComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.message = 'Do you want to delete ' + drug.brand + '?';
    modalRef.result.then(r => {
      if(r === 'Y') {
        this.drugService.delete(drug.id)
        .subscribe(() => {
          this.toastr.success('manufacturer delete successfully');
          this.drugService.reloadEmitter.emit();
        });
      }
    })
    .catch((r) => console.log(r));
  }

}
