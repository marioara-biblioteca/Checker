import { TestBed } from '@angular/core/testing';

import { CursuriTabeleService } from './cursuri-tabele.service';

describe('CursuriTabeleService', () => {
  let service: CursuriTabeleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CursuriTabeleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
