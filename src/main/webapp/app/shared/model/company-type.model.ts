import { ICompany } from '@/shared/model/company.model';

export interface ICompanyType {
  id?: number;
  type?: string;
  types?: ICompany[];
}

export class CompanyType implements ICompanyType {
  constructor(public id?: number, public type?: string, public types?: ICompany[]) {}
}
