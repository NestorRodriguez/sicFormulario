import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'formulario';

  registrarformulario(){
    console.log("Estamos en registrar formulario")
  }

  ConsultarRespuestas(){
    console.log("Consultar respuestas")
  }

}
