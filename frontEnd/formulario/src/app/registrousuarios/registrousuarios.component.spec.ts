import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrousuariosComponent } from './registrousuarios.component';

describe('RegistrousuariosComponent', () => {
  let component: RegistrousuariosComponent;
  let fixture: ComponentFixture<RegistrousuariosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrousuariosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrousuariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
