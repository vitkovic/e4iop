import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ICMSCompany {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  company?: ICompany;
  users?: IPortalUser[];
}

export class CMSCompany implements ICMSCompany {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public company?: ICompany,
    public users?: IPortalUser[]
  ) {}
}
