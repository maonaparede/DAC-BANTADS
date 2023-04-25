import { ID } from './ID';

interface IUser {
  id?: ID;
  name: string;
  address: string;
  city: string;
  state: string;
  password: string;
  email: string;
  complement: string;
  cpf: number;
  cep: number;
  number: number;
  salary: number;
  phone: number;
  userType: number;
}

type IUserLogin = Pick<IUser, 'email' | 'password'>;

export type { IUser, IUserLogin };
