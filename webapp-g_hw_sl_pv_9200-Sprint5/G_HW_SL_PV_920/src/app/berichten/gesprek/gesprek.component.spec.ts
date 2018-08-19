import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GesprekComponent } from './gesprek.component';

describe('GesprekComponent', () => {
  let component: GesprekComponent;
  let fixture: ComponentFixture<GesprekComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GesprekComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GesprekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
