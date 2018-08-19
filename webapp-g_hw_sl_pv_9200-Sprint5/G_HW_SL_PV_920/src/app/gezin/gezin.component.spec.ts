import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GezinComponent } from './gezin.component';

describe('GezinComponent', () => {
  let component: GezinComponent;
  let fixture: ComponentFixture<GezinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GezinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GezinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
