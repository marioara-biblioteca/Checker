import { TestBed } from '@angular/core/testing';

import { HomeProfesorService } from './home-profesor.service';

describe('HomeProfesorService', () => {
  let service: HomeProfesorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomeProfesorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
