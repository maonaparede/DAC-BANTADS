import { Injectable } from '@angular/core';

@Injectable()
export class User {
  constructor(
    public name?: string,
    public address?: string,
    public city?: string,
    public state?: string,
    public password?: string,
    public email?: string,
    public complement?: string,
    public cpf?: number,
    public cep?: number,
    public number?: number,
    public salary?: number,
    public phone?: number,
    public userType?: number,
  ) {}
}
