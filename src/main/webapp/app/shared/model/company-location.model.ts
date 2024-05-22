import { ICompany } from '@/shared/model/company.model';

export interface ICompanyLocation {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  company?: ICompany;
}

export class CompanyLocation implements ICompanyLocation {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public company?: ICompany
  ) {}
}
