import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfielBewerkenComponent } from './profiel-bewerken.component';

describe('ProfielBewerkenComponent', () => {
  let component: ProfielBewerkenComponent;
  let fixture: ComponentFixture<ProfielBewerkenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfielBewerkenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfielBewerkenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
