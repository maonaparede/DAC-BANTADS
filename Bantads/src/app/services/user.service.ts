import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };
  private api = environment.url;

  constructor(private http: HttpClient) {}

  getManagers(): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/manager/list', this.httpOptions);
  }

  getClients(): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/client/list', this.httpOptions);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(this.api + '/auth/' + id, this.httpOptions);
  }

  create(usuario: User): Observable<User> {
    return this.http.post<User>(this.api + '/orquestrador/client', JSON.stringify(usuario), this.httpOptions);
  }

  updateUser(usuario: User): Observable<User> {
    return this.http.put<User>(this.api + '/client/' + usuario.id, JSON.stringify(usuario), this.httpOptions);
  }

  deleteManager(id: number): Observable<User> {
    return this.http.delete<User>(this.api + '/manager/' + id, this.httpOptions);
  }

  getUserByEmail(email: string): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/client/por-email/' + email, this.httpOptions);
  }

  getClientByCPF(cpf: string): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/client/por-cpf/' + cpf, this.httpOptions);
  }

  getManagerByCPF(cpf: string): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/manager/por-cpf/' + cpf, this.httpOptions);
  }

  getClientByEmail(email: string): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/client/por-email/' + email, this.httpOptions);
  }

  getManagerByEmail(email: string): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/client/por-email/' + email, this.httpOptions);
  }

  getUserByCPF(cpf: string): Observable<User[]> {
    return this.http.get<User[]>(this.api + '/client/por-cpf/' + cpf, this.httpOptions);
  }
}
