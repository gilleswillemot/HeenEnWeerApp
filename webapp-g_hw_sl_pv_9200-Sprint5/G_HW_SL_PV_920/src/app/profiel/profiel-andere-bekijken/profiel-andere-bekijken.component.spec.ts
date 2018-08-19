import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfielAndereBekijkenComponent } from './profiel-andere-bekijken.component';

describe('ProfielAndereBekijkenComponent', () => {
  let component: ProfielAndereBekijkenComponent;
  let fixture: ComponentFixture<ProfielAndereBekijkenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfielAndereBekijkenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfielAndereBekijkenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
