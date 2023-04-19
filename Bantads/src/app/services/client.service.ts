import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { TransactionModel } from '../models/transaction.model';
import { AccountModel } from '../models/account.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };
  private apiUrlAccountModel = environment.url;
  private apiUrlTransacaos = environment.url;

  constructor(private http: HttpClient) {}

  create(account: AccountModel): Observable<AccountModel> {
    return this.http.post<AccountModel>(this.apiUrlAccountModel, JSON.stringify(account), this.httpOptions);
  }

  getAll(): Observable<AccountModel[]> {
    return this.http.get<AccountModel[]>(this.apiUrlAccountModel + '/list');
  }

  getAllTransactions(): Observable<TransactionModel[]> {
    return this.http.get<TransactionModel[]>(this.apiUrlTransacaos);
  }

  postTransaction(transaction: TransactionModel): Observable<TransactionModel> {
    return this.http.post<TransactionModel>(this.apiUrlTransacaos, JSON.stringify(transaction), this.httpOptions);
  }

  searchAccountById(id: number): Observable<AccountModel> {
    return this.http.get<AccountModel>(this.apiUrlAccountModel + '/' + id, this.httpOptions);
  }

  searchAccountByUserId(id: number): Observable<AccountModel[]> {
    return this.http.get<AccountModel[]>(this.apiUrlAccountModel + '/' + id, this.httpOptions);
  }

  updateClientAccount(client: AccountModel): Observable<AccountModel> {
    return this.http.put<AccountModel>(
      this.apiUrlAccountModel + '/' + client.id,
      JSON.stringify(client),
      this.httpOptions,
    );
  }

  getClientsByManager(idManager: number): Observable<AccountModel[]> {
    return this.http.get<AccountModel[]>(this.apiUrlAccountModel + '/por-gerente/' + idManager, this.httpOptions);
  }

  getClientsPendingByManager(idManager: number): Observable<AccountModel[]> {
    return this.http.get<AccountModel[]>(this.apiUrlAccountModel + '/pendentes/' + idManager, this.httpOptions);
  }
}
