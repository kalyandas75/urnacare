import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, filter, switchMap, tap } from 'rxjs/operators';
import { FORMULATIONS, ValidateValueInList } from 'src/app/app.constant';
import { CompositionService } from '../../composition/composition.service';
import { ManufacturerService } from '../../manufacturer/manufacturer.service';
import { DrugService } from '../drug.service';

@Component({
  selector: 'app-drug-edit',
  templateUrl: './drug-edit.component.html',
  styleUrls: ['./drug-edit.component.scss']
})
export class DrugEditComponent implements OnInit {
  drug;
  form: FormGroup;
  submitted = false;
  loading = false;
  manufacturers: any[];
  compositions: any[];
  formulations = FORMULATIONS;

  searchingC = false;
  searchCFailed = false;
  searchingM = false;
  searchMFailed = false;
  constructor(public activeModal: NgbActiveModal, 
    private drugService: DrugService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private compositionService: CompositionService,
    private manufacturerService: ManufacturerService) { }


    ngOnInit(): void {
      console.log(this.drug, this.drug.prescriptionRequired,  !!this.drug ? this.drug.prescriptionRequired : true);
      this.form = this.fb.group({
        brand: [!!this.drug.id ? this.drug.brand : '', [Validators.required]],
        strength: [!!this.drug.id ? this.drug.strength : ''],
        compositionId: [!!this.drug.id ? { id: this.drug.compositionId, name: this.drug.compositionName } : null, [Validators.required]],
        manufacturerId: [!!this.drug.id ? { id: this.drug.manufacturerId, name: this.drug.manufacturerName } : null, [Validators.required]],
        formulation: [!!this.drug.id ? this.drug.formulation : '', [Validators.required]],
        prescriptionRequired: [!!this.drug.id ? !!this.drug.prescriptionRequired : true]
      });

    }
    get f() { return this.form.controls; }
  
    onSubmit() {
      this.submitted = true;
      if(this.form.invalid) {
        return;
      }
      console.log('sending', this.form.value);
      const d = this.form.value;
      let dToSave = {
        brand: d.brand,
        strength: d.strength,
        compositionId: d.compositionId.id,
        manufacturerId: d.manufacturerId.id,
        formulation: d.formulation,
        prescriptionRequired: d.prescriptionRequired
      };
      let saveObservable = null;
      if(this.drug && this.drug.id) {
        saveObservable = this.drugService.update(this.drug.id, dToSave);
      } else {
        saveObservable = this.drugService.create(dToSave);
      }
      saveObservable
      .subscribe(res => {
        this.toastr.success('Drug saved successfully');
        this.drugService.reloadEmitter.emit();
        this.activeModal.close('Y');
      }, err => {
        this.toastr.error('Unable to save drug');
      });
      
    }

    formatter = (x: { id: number, name: string }) => x.name;

    searchC = (text$: Observable<string>) =>
    text$.pipe(
      tap((term) => {
        this.f['compositionId'].setValidators([ValidateValueInList([], term)]);
        this.f['compositionId'].updateValueAndValidity();
      }),
      debounceTime(300),
      // filter(t => t.length > 2),
      distinctUntilChanged(),
      tap(() => this.searchingC = true),
      switchMap(term =>
        this.compositionService.search(term).pipe(
          tap((values) => {
            this.searchCFailed = false;
            this.f['compositionId'].setValidators([ValidateValueInList(values as any[], term)]);
            this.f['compositionId'].updateValueAndValidity();
          }),
          catchError(() => {
            this.searchCFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searchingC = false)
    )

    searchM = (text$: Observable<string>) =>
    text$.pipe(
      tap((term) => {
        this.f['manufacturerId'].setValidators([ValidateValueInList([], term)]);
        this.f['manufacturerId'].updateValueAndValidity();
      }),
      debounceTime(300),
      // filter(t => t.length > 2),
      distinctUntilChanged(),
      tap(() => this.searchingM = true),
      switchMap(term =>
        this.manufacturerService.search(term).pipe(
          tap((values) => {
            this.searchCFailed = false;
            this.f['manufacturerId'].setValidators([ValidateValueInList(values as any[], term)]);
            this.f['manufacturerId'].updateValueAndValidity();
          }),
          catchError(() => {
            this.searchMFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searchingM = false)
    )

}
