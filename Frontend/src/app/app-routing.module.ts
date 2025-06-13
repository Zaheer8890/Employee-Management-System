import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddStudentComponent } from './add-student/add-student.component';
import { ShowStudentComponent } from './show-student/show-student.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { EditComponent } from './edit/edit.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' }, // Redirect default to login
  { path: 'login', component: LoginComponent }, // Login Page
  { path: 'add-student', component: AddStudentComponent, canActivate: [AuthGuard] }, // Protected
  { path: 'show-student', component: ShowStudentComponent, canActivate: [AuthGuard] },// Protected
  { path: 'edit/:id', component: EditComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'login' } // Redirect unknown routes to login
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

