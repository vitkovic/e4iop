import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ICmsQuestion {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  question?: string;
  answer?: string;
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
}

export class CmsQuestion implements ICmsQuestion {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public question?: string,
    public answer?: string,
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser
  ) {}
}
