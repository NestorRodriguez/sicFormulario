import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegistrousuariosComponent } from './registrousuarios/registrousuarios.component';
import { ConsultarrespuestasComponent } from './consultarrespuestas/consultarrespuestas.component';
import {RouterModule, Router, Routes} from '@angular/router';
import { HomeComponent } from './home/home.component';

const router: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'consultar',
    component: ConsultarrespuestasComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrousuariosComponent,
    ConsultarrespuestasComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(router),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
