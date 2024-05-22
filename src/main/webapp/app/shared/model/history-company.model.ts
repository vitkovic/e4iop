import { ICompany } from '@/shared/model/company.model';

export interface IHistoryCompany {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  date?: Date;
  company?: ICompany;
}

export class HistoryCompany implements IHistoryCompany {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public date?: Date,
    public company?: ICompany
  ) {}
}
