import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import {RegisterComponent} from "./pages/register/register.component";
import {ClientHomeComponent} from "./pages/client/home/home.component";
import {ManagerHomeComponent} from "./pages/manager/home/home.component";
import {AdminHomeComponent} from "./pages/admin/home/home.component";


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'client/home', component: ClientHomeComponent },
  { path: 'manager/home', component: ManagerHomeComponent },
  { path: 'admin/home', component: AdminHomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
