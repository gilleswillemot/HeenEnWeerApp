import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GesprekAanmakenComponent } from './gesprek-aanmaken.component';

describe('GesprekAanmakenComponent', () => {
  let component: GesprekAanmakenComponent;
  let fixture: ComponentFixture<GesprekAanmakenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GesprekAanmakenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GesprekAanmakenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
