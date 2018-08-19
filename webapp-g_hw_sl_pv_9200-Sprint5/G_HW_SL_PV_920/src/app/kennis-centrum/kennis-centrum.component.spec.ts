import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KennisCentrumComponent } from './kennis-centrum.component';

describe('KennisCentrumComponent', () => {
  let component: KennisCentrumComponent;
  let fixture: ComponentFixture<KennisCentrumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KennisCentrumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KennisCentrumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
