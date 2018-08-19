import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiviteitBewerkenComponent } from './activiteit-bewerken.component';

describe('ActiviteitBewerkenComponent', () => {
  let component: ActiviteitBewerkenComponent;
  let fixture: ComponentFixture<ActiviteitBewerkenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiviteitBewerkenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiviteitBewerkenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
