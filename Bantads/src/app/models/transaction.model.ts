export class TransactionModel {
  constructor(
    public id?: number,
    public idClient?: number,
    public typeTransaction?: number,
    public valueTransaction?: number,
    public receive?: string,
    public balance?: number,
    public date?: number,
  ) {}
}
