import { ID } from './ID';

interface IUser {
  id?: ID;
  name: string;
  address: string;
  city: string;
  state: string;
  neighborhood: string;
  password: string;
  email: string;
  cpf: string;
  cep: string;
  number: string;
  salary: string;
  phone: string;
  role: string;
}

type IUserLogin = Pick<IUser, 'email' | 'password'>;

export type { IUser, IUserLogin };
