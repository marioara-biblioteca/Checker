import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTabeleComponent } from './admin-tabele.component';

describe('AdminTabeleComponent', () => {
  let component: AdminTabeleComponent;
  let fixture: ComponentFixture<AdminTabeleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminTabeleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminTabeleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
