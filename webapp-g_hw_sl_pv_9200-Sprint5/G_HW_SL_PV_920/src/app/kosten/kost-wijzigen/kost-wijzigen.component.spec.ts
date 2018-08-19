import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KostWijzigenComponent } from './kost-wijzigen.component';

describe('KostWijzigenComponent', () => {
  let component: KostWijzigenComponent;
  let fixture: ComponentFixture<KostWijzigenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KostWijzigenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KostWijzigenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
