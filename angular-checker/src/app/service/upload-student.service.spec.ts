import { TestBed } from '@angular/core/testing';

import { UploadStudentService } from './upload-student.service';

describe('UploadStudentService', () => {
  let service: UploadStudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UploadStudentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
