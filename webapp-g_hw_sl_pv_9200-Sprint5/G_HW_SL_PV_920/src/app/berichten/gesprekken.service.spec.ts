import { TestBed, inject } from '@angular/core/testing';

import { GesprekkenService } from './gesprekken.service';

describe('GesprekkenService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GesprekkenService]
    });
  });

  it('should be created', inject([GesprekkenService], (service: GesprekkenService) => {
    expect(service).toBeTruthy();
  }));
});
