import { TestBed } from '@angular/core/testing';

import { CursuriStudentService } from './cursuri-student.service';

describe('CursuriStudentService', () => {
  let service: CursuriStudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CursuriStudentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
