import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { FormlyFormOptions, FormlyFieldConfig } from '@ngx-formly/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AppointmentService } from 'src/app/shared/service/appointment.service';
import { Observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap, switchMap, catchError, filter } from 'rxjs/operators';
import { DrugService } from '../../drug/drug.service';
import { FORMULATIONS } from 'src/app/app.constant';

@Component({
  selector: 'app-consultation-edit',
  templateUrl: './consultation-edit.component.html',
  styles: [
  ]
})
export class ConsultationEditComponent implements OnInit {
  appointment;
  form: FormGroup;
  searching = false;
  searchFailed = false;
  frequencies = [
    'qd',
    'bid',
    'tid',
    'qhs',
    'q2h',
    'q4h',
    'q6h',
    'qod',
    'prn'
  ];
  units = [
    'nos',
    'ml',
    'tsp',
    'Tbsp',
    'drops'
  ];
  formulations = FORMULATIONS;
  durationUnits = [
    'DAYS',
    'WEEKS',
    'MONTHS'
  ];
  constructor(public activeModal: NgbActiveModal, 
    private appointmentService: AppointmentService,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private drugService: DrugService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      chiefComplaint: [''],
      observation: [''],
      suggestedInvestigation: [''],
      diagnosis: [''],
      specialInstructions: [''],
      prescriptionDrugs: this.fb.array([], {updateOn: 'blur'})
    });
    this.addPrescriptionDrug();
  }

  private getFormArray(name: string) {
    return (this.form.get(name) as FormArray);
  }


  onCreate() {
    const consultation = JSON.parse(JSON.stringify(this.form.value));
    
    consultation.prescriptionDrugs.forEach(pd => {
      pd.drugId = pd.drug.id;
      delete pd.drug;
    });
    console.log('consultation', consultation);
    
    this.appointmentService.addConsultation(this.appointment.id, consultation)
    .subscribe(res => {
      this.appointmentService.refreshListEmitter.emit();
      this.toastr.success('Consultation Added Successfully');
      this.activeModal.close('Y');
    }, err => {
      this.toastr.error('Could not add a consultation');
    });
  }


  addPrescriptionDrug() {
    //getFormArray('addresses').controls[i].get('type')
    const prescriptionDrugsArray = this.getFormArray('prescriptionDrugs');
    if(!!prescriptionDrugsArray && prescriptionDrugsArray.length > 0) {
      if(!prescriptionDrugsArray.controls[prescriptionDrugsArray.length - 1].get('drug').value) {
        return;
      }
    }
    prescriptionDrugsArray.push(this.fb.group({
      drug: [null],
      dose: [1],
      unit: ['nos'],
      frequency: ['qd'],
      duration: ['1'],
      durationUnit: ['DAYS'],
      formulation: ['Tablet']
    }));
  }

  removePrescriptionDrug(index: number) {
    this.getFormArray('prescriptionDrugs').removeAt(index);
  }

  formatter = (r: any) => r.brand + (!!r.strength ? '(' + r.strength + ') ': '') + ' (' + r.compositionName + ') ' + r.formulation;

  search = (text$: Observable<string>) =>
  text$.pipe(
    filter(t => t.length > 2),
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
