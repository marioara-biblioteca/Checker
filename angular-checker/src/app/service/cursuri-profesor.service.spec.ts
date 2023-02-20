import { TestBed } from '@angular/core/testing';
import { CursuriProfesorService } from './cursuri-profesor.service';


describe('CursuriProfesorService', () => {
  let service: CursuriProfesorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CursuriProfesorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
