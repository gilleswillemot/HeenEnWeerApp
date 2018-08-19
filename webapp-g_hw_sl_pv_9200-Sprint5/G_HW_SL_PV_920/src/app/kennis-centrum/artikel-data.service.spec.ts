import { TestBed, inject } from '@angular/core/testing';

import { ArtikelDataService } from './artikel-data.service';

describe('KennisCentrumService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ArtikelDataService]
    });
  });

  it('should be created', inject([ArtikelDataService], (service: ArtikelDataService) => {
    expect(service).toBeTruthy();
  }));
});
