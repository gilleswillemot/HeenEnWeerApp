import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GezinAanmakenComponent } from './gezin-aanmaken.component';

describe('GezinAanmakenComponent', () => {
  let component: GezinAanmakenComponent;
  let fixture: ComponentFixture<GezinAanmakenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GezinAanmakenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GezinAanmakenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
