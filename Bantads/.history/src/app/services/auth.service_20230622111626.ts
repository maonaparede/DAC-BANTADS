import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { FormGroup } from '@angular/forms';
import { IUser, IUserLogin } from '../DTOs/IUser';
import { IUserAutocadastro } from '../DTOs/IUserAutocadastro';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  currentUser: IUser;
  private apiUrlR = 'http://localhost:5005';
  private apiUrlL = 'http://localhost:5001';

  public urlGateway = 'http://localhost:5000';

  constructor(private http: HttpClient) {}

  login(user: FormGroup): Observable<any> {
    const { email, senha } = user.value;
    const userData: IUserLogin = { email, senha };

    return this.http
      .post<any>(`${this.apiUrlL}/api/auth/login`, userData, {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      })
      .pipe(
        tap(response => {
          const token = response.body.token;
          this.currentUser = response.body.user;
          localStorage.setItem('token', token ? token : '');
        }),
      );
  }

  createAccount(user: Partial<IUser>): Observable<any> {
    return this.http.post(`${this.apiUrlR}/api/saga/cli/auto`, user);
  }

  login2(user: IUserLogin): Observable<any> {
    const userData: IUserLogin = user

    return this.http
      .post<any>(`${this.urlGateway}/api/auth/login`, userData, {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      })
      .pipe(
        tap(response => {
          console.log('aaaa:' +response);
          const token = response.body.token;
          this.currentUser = response.body.user;
          localStorage.setItem('token', token ? token : '');
        }),
      );
  }

  createAccount2(user: IUserAutocadastro): Observable<any> {
    return this.http.post(`${this.urlGateway}/api/saga/cli/auto`, user);
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getCurrentUser() {
    return this.currentUser;
  }
}
