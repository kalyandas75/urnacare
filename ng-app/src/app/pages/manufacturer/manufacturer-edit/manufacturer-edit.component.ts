import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { FormlyFieldConfig, FormlyFormOptions } from "@ngx-formly/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from "ngx-toastr";
import { ManufacturerService } from "../manufacturer.service";

@Component({
  selector: "app-manufacturer-edit",
  templateUrl: "./manufacturer-edit.component.html",
  styleUrls: ["./manufacturer-edit.component.scss"],
})
export class ManufacturerEditComponent implements OnInit {
  manufacturer;
  form: FormGroup;
  submitted = false;
  loading = false;
  authorities = ["ROLE_ADMIN", "ROLE_SUPPORT"];

  constructor(
    public activeModal: NgbActiveModal,
    private manufacturerServ: ManufacturerService,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    console.log("user", this.manufacturer);
    this.form = this.fb.group({
      name: [
        !!this.manufacturer.name ? this.manufacturer.name : "",
        [Validators.required],
      ],
    });
  }

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    console.log("sending", this.form.value);
    this.manufacturerServ.saveManufacturer(this.form.value).subscribe(
      (res) => {
        this.toastr.success("User saved successfully");
        this.manufacturerServ.reloadEmitter.emit();
        this.activeModal.close("Y");
      },
      (err) => {
        this.toastr.error("Unable to save user");
      }
    );
  }
}
