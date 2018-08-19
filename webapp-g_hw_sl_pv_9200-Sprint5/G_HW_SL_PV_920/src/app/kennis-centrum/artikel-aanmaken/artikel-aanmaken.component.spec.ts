import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtikelAanmakenComponent } from './artikel-aanmaken.component';

describe('ArtikelAanmakenComponent', () => {
  let component: ArtikelAanmakenComponent;
  let fixture: ComponentFixture<ArtikelAanmakenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtikelAanmakenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtikelAanmakenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
