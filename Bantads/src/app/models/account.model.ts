export class AccountModel {
  constructor(
    public id?: number,
    public idUser?: number,
    public date?: Date,
    public limit?: number,
    public active?: boolean,
    public deniedReason?: string,
    public deniedDate?: Date,
    public balance?: number,
    public idManager?: number,
    public salary?: number | string,
  ) {}
}
