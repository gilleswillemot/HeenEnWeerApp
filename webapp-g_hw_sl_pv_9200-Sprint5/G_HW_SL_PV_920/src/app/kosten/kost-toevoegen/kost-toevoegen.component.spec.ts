import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KostToevoegenComponent } from './kost-toevoegen.component';

describe('KostToevoegenComponent', () => {
  let component: KostToevoegenComponent;
  let fixture: ComponentFixture<KostToevoegenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KostToevoegenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KostToevoegenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
