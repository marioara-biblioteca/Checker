import { TestBed } from '@angular/core/testing';

import { TaskProfesoriService } from './task-profesori.service';

describe('TaskProfesoriService', () => {
  let service: TaskProfesoriService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskProfesoriService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
