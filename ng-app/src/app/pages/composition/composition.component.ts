import { Component, OnDestroy, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Subscription } from "rxjs";
import { CompositionEditComponent } from "./composition-edit/composition-edit.component";
import { CompositionService } from "./composition.service";

@Component({
  selector: "app-composition",
  templateUrl: "./composition.component.html",
  styleUrls: ["./composition.component.scss"],
})
export class CompositionComponent implements OnInit, OnDestroy {
  compositions: any[];
  reloadEmitterSubscription: Subscription;

  constructor(
    private compositionServ: CompositionService,
    private modalService: NgbModal
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
    this.compositionServ.getAllCompositions().subscribe((res) => {
      this.compositions = res.body as any[];
    });
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
}
