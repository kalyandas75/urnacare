import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderInitComponent } from './order-init.component';

describe('OrderInitComponent', () => {
  let component: OrderInitComponent;
  let fixture: ComponentFixture<OrderInitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderInitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
