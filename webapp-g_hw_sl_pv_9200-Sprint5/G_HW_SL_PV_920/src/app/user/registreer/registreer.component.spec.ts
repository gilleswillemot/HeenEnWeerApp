import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistreerComponent } from './registreer.component';

describe('RegistreerComponent', () => {
  let component: RegistreerComponent;
  let fixture: ComponentFixture<RegistreerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistreerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistreerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
