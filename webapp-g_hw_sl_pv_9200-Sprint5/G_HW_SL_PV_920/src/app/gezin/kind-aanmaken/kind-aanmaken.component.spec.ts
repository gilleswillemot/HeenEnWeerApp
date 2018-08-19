import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KindAanmakenComponent } from './kind-aanmaken.component';

describe('KindAanmakenComponent', () => {
  let component: KindAanmakenComponent;
  let fixture: ComponentFixture<KindAanmakenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KindAanmakenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KindAanmakenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
