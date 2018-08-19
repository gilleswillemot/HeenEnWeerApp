import { TestBed, inject } from '@angular/core/testing';

import { ActiviteitService } from "./activiteit.service";

describe('ActiviteitService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ActiviteitService]
    });
  });

  it('should be created', inject([ActiviteitService], (service: ActiviteitService) => {
    expect(service).toBeTruthy();
  }));
});
