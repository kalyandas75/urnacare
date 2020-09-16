import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAppointmentRequestComponent } from './patient-appointment-request.component';

describe('PatientAppointmentRequestComponent', () => {
  let component: PatientAppointmentRequestComponent;
  let fixture: ComponentFixture<PatientAppointmentRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientAppointmentRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAppointmentRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
