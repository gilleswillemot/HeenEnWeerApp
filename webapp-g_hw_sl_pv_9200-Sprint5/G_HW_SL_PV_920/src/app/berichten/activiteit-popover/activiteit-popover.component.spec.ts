import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiviteitPopoverComponent } from './activiteit-popover.component';

describe('ActiviteitPopoverComponent', () => {
  let component: ActiviteitPopoverComponent;
  let fixture: ComponentFixture<ActiviteitPopoverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiviteitPopoverComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiviteitPopoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
