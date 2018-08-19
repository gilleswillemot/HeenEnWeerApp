import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DagViewComponent } from './dag-view.component';

describe('DagViewComponent', () => {
  let component: DagViewComponent;
  let fixture: ComponentFixture<DagViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DagViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DagViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
