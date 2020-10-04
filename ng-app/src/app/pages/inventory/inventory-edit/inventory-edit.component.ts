import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from "ngx-toastr";
import { Observable, of, Subscription } from "rxjs";
import { debounceTime, distinctUntilChanged, tap, switchMap, catchError, filter } from 'rxjs/operators';
import { CompositionEditComponent } from "../../composition/composition-edit/composition-edit.component";
import { CompositionService } from "../../composition/composition.service";
import { DrugService } from '../../drug/drug.service';
import { ManufacturerEditComponent } from "../../manufacturer/manufacturer-edit/manufacturer-edit.component";
import { ManufacturerService } from "../../manufacturer/manufacturer.service";
import { InventoryManagementService } from "../inventory-management.service";

@Component({
  selector: "app-inventory-edit",
  templateUrl: "./inventory-edit.component.html",
  styleUrls: ["./inventory-edit.component.scss"],
})
export class InventoryEditComponent implements OnInit, OnDestroy {
  inventory;
  form: FormGroup;
  submitted = false;
  loading = false;
  genders = ["Female", "Male", "Other"];
  authorities = ["ROLE_ADMIN", "ROLE_SUPPORT"];
  compositions: any[];
  manufacturers: any[];
  formulations = [
    "Tablet",
    "Capsule",
    "Injection",
    "Drops",
    "Tonic",
    "Lotion",
    "Cream",
    "Powder",
    "Device",
  ];
  units = ["mg", "g", "ml", "ltr", "nos"];
  reloadEmitterSubscription: Subscription;

  drug: any;
  searching = false;
  searchFailed = false;

  constructor(
    public activeModal: NgbActiveModal,
    private inventoryManagementServ: InventoryManagementService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private compositionServ: CompositionService,
    private manufacturerServ: ManufacturerService,
    private modalService: NgbModal,
    private drugService: DrugService
  ) {}

  ngOnInit(): void {
    console.log("inventory", this.inventory);
    if(this.inventory.id) {
      this.drug = {
        id: this.inventory.drugId,
        brand: this.inventory.brand,
        compositionName: this.inventory.compositionName,
        manufacturerName: this.inventory.manufacturerName,
        formulation: this.inventory.formulation,
        strength: this.inventory.strength
      };
    }
    this.form = this.fb.group({
      batchNumber: [
        !!this.inventory.id ? this.inventory.batchNumber : "",
        [Validators.required],
      ],
      cgst: [
        !!this.inventory.id ? this.inventory.cgst : 0,
        [Validators.required,Validators.min(0), Validators.max(100)],
      ],
      expiryDate: [
        !!this.inventory.id ? this.inventory.expiryDate : "",
        [Validators.required],
      ],
      gst: [
        !!this.inventory.id ? this.inventory.gst : 0,
        [Validators.required,Validators.min(0), Validators.max(100)],
      ],
      hsnCode: [
        !!this.inventory.id ? this.inventory.hsnCode : "",
        [Validators.required],
      ],
      igst: [
        !!this.inventory.id ? this.inventory.igst : 0,
        [Validators.required,Validators.min(0), Validators.max(100)],
      ],
      maxDiscountRate: [
        !!this.inventory.id ? this.inventory.maxDiscountRate : 0,
        [Validators.required,Validators.min(0), Validators.max(100)],
      ],
      noOfUnits: [
        !!this.inventory.id ? this.inventory.noOfUnits : 1,
        [Validators.required, Validators.min(1)],
      ],
      packSize: [
        !!this.inventory.id ? this.inventory.packSize : 1,
        [Validators.required, Validators.min(1)],
      ],
      price: [
        !!this.inventory.id ? this.inventory.price : 0,
        [Validators.required, Validators.min(0)],
      ],
      sgst: [
        !!this.inventory.id ? this.inventory.sgst : 0,
        [Validators.required,Validators.min(0), Validators.max(100)],
      ],
      unit: [
        !!this.inventory.id ? this.inventory.unit : 1,
        [Validators.required, Validators.min(1)],
      ],
    });
  }

  get f() {
    return this.form.controls;
  }

  ngOnDestroy(): void {
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    console.log("sending", this.form.value);
    const value = this.form.value;
    value.drugId = this.drug.id;
    let saveObs = null;
    if(this.inventory.id) {
      saveObs = this.inventoryManagementServ.update(this.inventory.id, value)
    } else {
      saveObs = this.inventoryManagementServ.create(value);
    }
    
    saveObs.subscribe(
      (res) => {
        this.toastr.success("Inventory saved successfully");
        this.inventoryManagementServ.reloadEmitter.emit();
        this.activeModal.close("Y");
      },
      (err) => {
        this.toastr.error("Unable to save inventory");
      }
    );
  }

  formatter = (x: {brand: string, compositionName: string }) => x.brand + '~' + x.compositionName;

  search = (text$: Observable<string>) =>
  text$.pipe(
    //filter(t => t.length > 2),
    debounceTime(300),
    distinctUntilChanged(),
    tap(() => this.searching = true),
    switchMap(term =>
      this.drugService.search(term).pipe(
        tap(() => this.searchFailed = false),
        catchError(() => {
          this.searchFailed = true;
          return of([]);
        }))
    ),
    tap(() => this.searching = false)
  )


}
