import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IPortalUserStatus {
  id?: number;
  valueSr?: string;
  valueEn?: string;
  valueSrCyr?: string;
  users?: IPortalUser[];
}

export class PortalUserStatus implements IPortalUserStatus {
  constructor(
    public id?: number,
    public value?: string,
    public valueEn?: string,
    public valueSrCyr?: string,
    public users?: IPortalUser[]
  ) {}
}
