import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfielBekijkenComponent } from './profiel-bekijken.component';

describe('ProfielBekijkenComponent', () => {
  let component: ProfielBekijkenComponent;
  let fixture: ComponentFixture<ProfielBekijkenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfielBekijkenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfielBekijkenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
