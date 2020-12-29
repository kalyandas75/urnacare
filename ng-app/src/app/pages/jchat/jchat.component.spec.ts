import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JChatComponent } from './jchat.component';

describe('JChatComponent', () => {
  let component: JChatComponent;
  let fixture: ComponentFixture<JChatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JChatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
