import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HelloWorldComponent } from './hello-world/hello-world.component';
import { LoginComponent } from './login/login.component';
import { HomeStudentComponent } from './home-student/home-student.component';
import { HomeProfesorComponent } from './home-profesor/home-profesor.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { CursuriStudentComponent } from './cursuri-student/cursuri-student.component';
import { TaskStudentComponent } from './task-student/task-student.component';
import { TaskProfesoriService } from './service/task-profesori.service';
import { TaskProfesoriComponent } from './task-profesori/task-profesori.component';
import { CursuriProfesorComponent } from './cursuri-profesor/cursuri-profesor.component';
import { AdminTabeleComponent } from './admin-tabele/admin-tabele.component';
import { TesteProfesorComponent } from './teste-profesor/teste-profesor.component';
import { UploadStudentComponent } from './upload-student/upload-student.component';

const routes: Routes = [
  {path: '', component: HelloWorldComponent},
  {path: 'hello-world', component: HelloWorldComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home_student', component: HomeStudentComponent},
  {path: 'home_profesor', component: HomeProfesorComponent},
  {path: 'home_admin', component: HomeAdminComponent},

  {path:'admin_tabele',component:AdminTabeleComponent},

{path:'subject_student',component:CursuriStudentComponent},
{path:'subject_profesor',component:CursuriProfesorComponent},

{path:'task_student',component:TaskStudentComponent},
{path:'task_student/:id',component:TaskStudentComponent},
{path:'upload_student',component:UploadStudentComponent},
{path:'upload_student/:id',component:UploadStudentComponent},


{path:'task_profesor',component:TaskProfesoriComponent},
{path:'task_profesor/:id',component:TaskProfesoriComponent},
{path:'teste_profesor',component:TesteProfesorComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }