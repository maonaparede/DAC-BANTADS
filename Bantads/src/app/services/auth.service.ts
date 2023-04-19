import { Injectable } from '@angular/core';
import { IUser, IUserLogin } from '../DTOs/user';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isAuthenticate: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  getUsers() {
    return this.http.get('users');
  }

  async login(userForm: IUserLogin) {
    const result = await this.http.post<any>(`${environment.api}/users`, userForm).subscribe(res => {
      const user = res.find((u: IUser) => {
        return u.email === userForm.email && u.email === userForm.password;
      });
      if (user) {
        alert('login Success');
        this.router.navigate(['client/home']);
      }
    });
    return false;
  }

  createAccount(account: FormGroup) {
    this.http.post<any>(`${environment.api}/users`, account).subscribe(
      res => {
        alert('Signup Successfull');
        this.router.navigate(['login']);
      },
      err => {
        alert('Something went wrong!');
      },
    );

    return new Promise(resolve => {
      resolve(true);
    });
  }

  deleteSession() {}
}
