import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursuriProfesorComponent } from './cursuri-profesor.component';

describe('CursuriProfesorComponent', () => {
  let component: CursuriProfesorComponent;
  let fixture: ComponentFixture<CursuriProfesorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursuriProfesorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CursuriProfesorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
