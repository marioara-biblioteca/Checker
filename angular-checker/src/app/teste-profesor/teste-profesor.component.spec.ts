import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TesteProfesorComponent } from './teste-profesor.component';

describe('TesteProfesorComponent', () => {
  let component: TesteProfesorComponent;
  let fixture: ComponentFixture<TesteProfesorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TesteProfesorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TesteProfesorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
