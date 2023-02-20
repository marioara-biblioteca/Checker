import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskStudentComponent } from './task-student.component';

describe('TaskStudentComponent', () => {
  let component: TaskStudentComponent;
  let fixture: ComponentFixture<TaskStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TaskStudentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
