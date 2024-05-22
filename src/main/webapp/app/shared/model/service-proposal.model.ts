import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IServiceProposal {
  id?: number;
  date?: Date;
  name?: string;
  description?: string;
  purpose?: string;
  portalUser?: IPortalUser;
}

export class ServiceProposal implements IServiceProposal {
  constructor(
    public id?: number,
    public date?: Date,
    public name?: string,
    public description?: string,
    public purpose?: string,
    public portalUser?: IPortalUser
  ) {}
}
