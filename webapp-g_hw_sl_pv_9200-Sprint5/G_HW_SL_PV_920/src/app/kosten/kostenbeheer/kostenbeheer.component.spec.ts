import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KostenbeheerComponent } from './kostenbeheer.component';

describe('KostenbeheerComponent', () => {
  let component: KostenbeheerComponent;
  let fixture: ComponentFixture<KostenbeheerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KostenbeheerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KostenbeheerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
