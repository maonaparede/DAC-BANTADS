export class User {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public password?: string,
    public role?: string,
    public cpf?: string,
    public phone?: string,
    public state?: number,
    public city?: number,
    public cep?: string,
    public street?: string,
    public number?: number,
    public complement?: string,
    public active?: boolean,
  ) {}
}
