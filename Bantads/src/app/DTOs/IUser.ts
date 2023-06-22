import { ID } from './ID';

interface IUser {
  id?: ID;
  nome: string;
  email: string;
  telefone: string;
  salario: string;
  cpf: string;
  logradouro: string;
  cidade: string;
  estado: string;
  cep: string;
  tipo: string;
  senha: string;
  numero: string;
}

type IUserLogin = Pick<IUser, 'email' | 'senha'>;

export type { IUser, IUserLogin };
