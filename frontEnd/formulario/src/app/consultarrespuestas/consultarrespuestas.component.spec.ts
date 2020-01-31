import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultarrespuestasComponent } from './consultarrespuestas.component';

describe('ConsultarrespuestasComponent', () => {
  let component: ConsultarrespuestasComponent;
  let fixture: ComponentFixture<ConsultarrespuestasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultarrespuestasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultarrespuestasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
