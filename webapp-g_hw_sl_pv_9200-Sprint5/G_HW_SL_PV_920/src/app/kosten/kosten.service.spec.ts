import { TestBed, inject } from '@angular/core/testing';

import { KostenService } from './kosten.service';

describe('KostenService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [KostenService]
    });
  });

  it('should be created', inject([KostenService], (service: KostenService) => {
    expect(service).toBeTruthy();
  }));
});
