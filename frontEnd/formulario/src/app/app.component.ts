import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'formulario';

  registrarformulario(){
    console.log("Estamos en registrar formulario")
    //RouterLink = '';
  }

  ConsultarRespuestas(){
    console.log("Consultar respuestas")
  }

}
