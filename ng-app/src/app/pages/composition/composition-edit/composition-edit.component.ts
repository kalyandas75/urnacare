import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
} from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { FormlyFieldConfig, FormlyFormOptions } from "@ngx-formly/core";
import { ToastrService } from "ngx-toastr";
import { CompositionService } from "../composition.service";

@Component({
  selector: "app-composition-edit",
  templateUrl: "./composition-edit.component.html",
  styleUrls: ["./composition-edit.component.scss"],
})
export class CompositionEditComponent implements OnInit {
  composition;
  form: FormGroup;
  submitted = false;
  loading = false;
  authorities = ["ROLE_ADMIN", "ROLE_SUPPORT"];

  constructor(
    public activeModal: NgbActiveModal,
    private compositionServ: CompositionService,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    console.log("composition", this.composition);
    this.form = this.fb.group({
      name: [
        !!this.composition.name ? this.composition.name : "",
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
    this.compositionServ.saveComposition(this.form.value).subscribe(
      (res) => {
        this.toastr.success("User saved successfully");
        this.compositionServ.reloadEmitter.emit();
        this.activeModal.close("Y");
      },
      (err) => {
        this.toastr.error("Unable to save user");
      }
    );
  }
}
