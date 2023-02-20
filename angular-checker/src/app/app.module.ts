import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HelloWorldService } from './service/hello-world.service';
import { MenuComponent } from './menu/menu.component';
import { HelloWorldComponent } from './hello-world/hello-world.component';
import { LoginComponent } from './login/login.component';
import { HomeStudentComponent } from './home-student/home-student.component';
import { HomeProfesorComponent } from './home-profesor/home-profesor.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { CursuriComponent } from './cursuri/cursuri.component';
import { CursuriStudentComponent } from './cursuri-student/cursuri-student.component';
import { TaskStudentComponent } from './task-student/task-student.component';
import { TaskProfesoriComponent } from './task-profesori/task-profesori.component';
import { CursuriProfesorComponent } from './cursuri-profesor/cursuri-profesor.component';
import { AdminTabeleComponent } from './admin-tabele/admin-tabele.component';
import { CookieService } from 'ngx-cookie-service';
import { TesteProfesorComponent } from './teste-profesor/teste-profesor.component';
import { UploadStudentComponent } from './upload-student/upload-student.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HelloWorldComponent,
    LoginComponent,
    HomeStudentComponent,
    HomeProfesorComponent,
    HomeAdminComponent,
    CursuriComponent,
    CursuriStudentComponent,
    TaskStudentComponent,
    TaskProfesoriComponent,
    CursuriProfesorComponent,
    AdminTabeleComponent,
    TesteProfesorComponent,
    UploadStudentComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
FormsModule,
ReactiveFormsModule
  ],
  providers: [
    HelloWorldService,
    CookieService,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }