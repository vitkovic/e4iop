import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IHistoryPortalUser {
  id?: number;
  firstName?: string;
  familyName?: string;
  phone?: string;
  position?: string;
  date?: Date;
  portalUser?: IPortalUser;
}

export class HistoryPortalUser implements IHistoryPortalUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public familyName?: string,
    public phone?: string,
    public position?: string,
    public date?: Date,
    public portalUser?: IPortalUser
  ) {}
}
