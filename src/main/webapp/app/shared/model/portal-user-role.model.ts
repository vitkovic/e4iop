import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IPortalUserRole {
  id?: number;
  role?: string;
  abbreviation?: string;
  fullName?: string;
  users?: IPortalUser[];
}

export class PortalUserRole implements IPortalUserRole {
  constructor(
    public id?: number,
    public role?: string,
    public abbreviation?: string,
    public fullName?: string,
    public users?: IPortalUser[]
  ) {}
}
