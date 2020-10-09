import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrintServiceComponent } from './print-service.component';

describe('PrintServiceComponent', () => {
  let component: PrintServiceComponent;
  let fixture: ComponentFixture<PrintServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrintServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrintServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
