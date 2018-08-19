import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiviteitGesprekComponent } from './activiteit-gesprek.component';

describe('ActiviteitGesprekComponent', () => {
  let component: ActiviteitGesprekComponent;
  let fixture: ComponentFixture<ActiviteitGesprekComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiviteitGesprekComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiviteitGesprekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
