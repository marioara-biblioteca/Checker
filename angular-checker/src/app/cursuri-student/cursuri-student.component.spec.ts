import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursuriStudentComponent } from './cursuri-student.component';

describe('CursuriStudentComponent', () => {
  let component: CursuriStudentComponent;
  let fixture: ComponentFixture<CursuriStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursuriStudentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CursuriStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
