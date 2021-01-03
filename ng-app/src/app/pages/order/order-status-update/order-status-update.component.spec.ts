import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderStatusUpdateComponent } from './order-status-update.component';

describe('OrderStatusUpdateComponent', () => {
  let component: OrderStatusUpdateComponent;
  let fixture: ComponentFixture<OrderStatusUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderStatusUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderStatusUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
