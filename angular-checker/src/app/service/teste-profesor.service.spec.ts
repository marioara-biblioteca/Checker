import { TestBed } from '@angular/core/testing';

import { TesteProfesorService } from './teste-profesor.service';

describe('TesteProfesorService', () => {
  let service: TesteProfesorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TesteProfesorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
