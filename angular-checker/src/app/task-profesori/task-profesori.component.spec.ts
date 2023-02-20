import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskProfesoriComponent } from './task-profesori.component';

describe('TaskProfesoriComponent', () => {
  let component: TaskProfesoriComponent;
  let fixture: ComponentFixture<TaskProfesoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TaskProfesoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskProfesoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
