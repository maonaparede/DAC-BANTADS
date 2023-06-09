import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { FormGroup } from '@angular/forms';
import { IUser, IUserLogin } from '../DTOs/IUser';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  currentUser: IUser;
  private apiUrl = `${environment.url}`;

  constructor(private http: HttpClient) {}

  login(user: FormGroup): Observable<any> {
    const { email, password } = user.value;
    const userData: IUserLogin = { email, password };

    return this.http
      .post<any>(`${this.apiUrl}/login`, userData, {
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
    console.log('ANTES DO RETURN ', user);
    return this.http.post(`${this.apiUrl}/register`, user);
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
