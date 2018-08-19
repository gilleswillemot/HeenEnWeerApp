import { TestBed, inject } from '@angular/core/testing';

import { GebruikerService } from './gebruiker.service';

describe('GebruikerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GebruikerService]
    });
  });

  it('should be created', inject([GebruikerService], (service: GebruikerService) => {
    expect(service).toBeTruthy();
  }));
});
