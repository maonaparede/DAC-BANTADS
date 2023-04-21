import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Login } from '../models/login.model';
import { User } from '../models/user.model';
import { FormGroup } from '@angular/forms';

const LS_USER: string = 'user';
const LS_ACCOUNT: string = 'conta';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };
  private apUrlAuth = environment.url;

  constructor(private http: HttpClient) {}

  public get userLogged(): User {
    return localStorage[LS_USER] ? JSON.parse(localStorage[LS_USER]) : null;
  }

  public set userLogged(user: User) {
    localStorage[LS_USER] = JSON.stringify(user);
  }

  public get clientAccount(): User {
    return localStorage[LS_ACCOUNT] ? JSON.parse(localStorage[LS_ACCOUNT]) : null;
  }

  public set clientAccount(user: User) {
    localStorage[LS_ACCOUNT] = JSON.stringify(user);
  }

  login(login: FormGroup): Observable<User> {
    return this.http.post<Login>(this.apUrlAuth + '/login', this.httpOptions);
  }

  logout(): Observable<Object> {
    delete localStorage[LS_USER];
    delete localStorage[LS_ACCOUNT];
    return this.http.post<Login>(this.apUrlAuth + '/logout', this.httpOptions);
  }
}
