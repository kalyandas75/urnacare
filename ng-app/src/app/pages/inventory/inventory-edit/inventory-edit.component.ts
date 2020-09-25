import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { CompositionEditComponent } from "../../composition/composition-edit/composition-edit.component";
import { CompositionService } from "../../composition/composition.service";
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

  constructor(
    public activeModal: NgbActiveModal,
    private inventoryManagementServ: InventoryManagementService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private compositionServ: CompositionService,
    private manufacturerServ: ManufacturerService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    console.log("inventory", this.inventory);

    this.getComposition();

    this.reloadEmitterSubscription = this.compositionServ.reloadEmitter.subscribe(
      () => {
        this.getComposition();
      }
    );
    this.getManufacturers();

    this.reloadEmitterSubscription = this.manufacturerServ.reloadEmitter.subscribe(
      () => {
        this.getManufacturers();
      }
    );
    this.form = this.fb.group({
      brand: [
        !!this.inventory.brand ? this.inventory.brand : "",
        [Validators.required],
      ],
      batchNumber: [
        !!this.inventory.batchNumber ? this.inventory.batchNumber : "",
        [Validators.required],
      ],
      cgst: [
        !!this.inventory.cgst ? this.inventory.cgst : "",
        [Validators.required],
      ],
      compositionId: [
        !!this.inventory.compositionId ? this.inventory.compositionId : "",
        [Validators.required],
      ],
      manufacturerId: [
        !!this.inventory.manufacturerId ? this.inventory.manufacturerId : "",
        [Validators.required],
      ],
      expiryDate: [
        !!this.inventory.expiryDate ? this.inventory.expiryDate : "",
        [Validators.required],
      ],
      formulation: [
        !!this.inventory.formulation ? this.inventory.formulation : "",
        [Validators.required],
      ],
      gst: [
        !!this.inventory.gst ? this.inventory.gst : "",
        [Validators.required],
      ],
      hsnCode: [
        !!this.inventory.hsnCode ? this.inventory.hsnCode : "",
        [Validators.required],
      ],
      igst: [
        !!this.inventory.igst ? this.inventory.igst : "",
        [Validators.required],
      ],
      maxDiscountRate: [
        !!this.inventory.maxDiscountRate ? this.inventory.maxDiscountRate : "",
        [Validators.required],
      ],
      noOfUnits: [
        !!this.inventory.noOfUnits ? this.inventory.noOfUnits : "",
        [Validators.required],
      ],
      packSize: [
        !!this.inventory.packSize ? this.inventory.packSize : "",
        [Validators.required],
      ],
      prescriptionRequired: [
        !!this.inventory.prescriptionRequired
          ? this.inventory.prescriptionRequired
          : "",
        [Validators.required],
      ],
      price: [
        !!this.inventory.price ? this.inventory.price : "",
        [Validators.required],
      ],
      sgst: [
        !!this.inventory.sgst ? this.inventory.sgst : "",
        [Validators.required],
      ],
      unit: [
        !!this.inventory.unit ? this.inventory.unit : "",
        [Validators.required],
      ],
    });
  }

  get f() {
    return this.form.controls;
  }

  ngOnDestroy(): void {
    this.reloadEmitterSubscription.unsubscribe();
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    console.log("sending", this.form.value);
    this.inventoryManagementServ.saveInventory(this.form.value).subscribe(
      (res) => {
        this.toastr.success("User saved successfully");
        this.inventoryManagementServ.reloadEmitter.emit();
        this.activeModal.close("Y");
      },
      (err) => {
        this.toastr.error("Unable to save user");
      }
    );
  }

  getManufacturers() {
    this.manufacturerServ.getAllManufacturers().subscribe((res) => {
      this.manufacturers = res.body as any[];
    });
  }

  getComposition() {
    this.compositionServ.getAllCompositions().subscribe((res) => {
      this.compositions = res.body as any[];
    });
  }

  addManufacturer() {
    const modalRef = this.modalService.open(ManufacturerEditComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.manufacturer = Object.assign({});
  }

  addComposition() {
    const modalRef = this.modalService.open(CompositionEditComponent, {
      size: "sm",
      scrollable: true,
      centered: false,
      backdrop: "static",
    });
    modalRef.componentInstance.composition = Object.assign({});
  }
}
