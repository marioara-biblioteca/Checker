import { TestBed } from '@angular/core/testing';

import { TaskStudentService } from './task-student.service';

describe('TaskStudentService', () => {
  let service: TaskStudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskStudentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
