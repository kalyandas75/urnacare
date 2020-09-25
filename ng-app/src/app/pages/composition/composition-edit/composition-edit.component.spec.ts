import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompositionEditComponent } from './composition-edit.component';

describe('CompositionEditComponent', () => {
  let component: CompositionEditComponent;
  let fixture: ComponentFixture<CompositionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompositionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompositionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
