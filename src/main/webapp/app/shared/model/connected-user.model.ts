import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IConnectedUser {
  id?: number;
  data?: string;
  portalUser?: IPortalUser;
}

export class ConnectedUser implements IConnectedUser {
  constructor(public id?: number, public data?: string, public portalUser?: IPortalUser) {}
}
