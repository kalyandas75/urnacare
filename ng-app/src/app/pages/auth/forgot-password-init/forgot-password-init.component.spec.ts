import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPasswordInitComponent } from './forgot-password-init.component';

describe('ForgotPasswordInitComponent', () => {
  let component: ForgotPasswordInitComponent;
  let fixture: ComponentFixture<ForgotPasswordInitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgotPasswordInitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPasswordInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
