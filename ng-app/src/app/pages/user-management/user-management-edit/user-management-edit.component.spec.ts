import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManagementEditComponent } from './user-management-edit.component';

describe('UserManagementEditComponent', () => {
  let component: UserManagementEditComponent;
  let fixture: ComponentFixture<UserManagementEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserManagementEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManagementEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
