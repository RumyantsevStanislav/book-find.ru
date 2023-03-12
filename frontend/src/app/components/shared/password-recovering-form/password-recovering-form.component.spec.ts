import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordRecoveringFormComponent } from './password-recovering-form.component';

describe('PasswordRecoveringFormComponent', () => {
  let component: PasswordRecoveringFormComponent;
  let fixture: ComponentFixture<PasswordRecoveringFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordRecoveringFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasswordRecoveringFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
