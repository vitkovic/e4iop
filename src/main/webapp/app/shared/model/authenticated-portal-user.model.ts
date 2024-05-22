import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IAuthenticatedPortalUser {
  id?: number;
  data?: string;
  portalUser?: IPortalUser;
}

export class AuthenticatedPortalUser implements IAuthenticatedPortalUser {
  constructor(public id?: number, public data?: string, public portalUser?: IPortalUser) {}
}
