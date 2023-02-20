import { TestBed } from '@angular/core/testing';

import { AdminTabeleService } from './admin-tabele.service';

describe('AdminTabeleService', () => {
  let service: AdminTabeleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminTabeleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
