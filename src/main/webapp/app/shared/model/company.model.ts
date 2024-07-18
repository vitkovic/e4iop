import { IPortalUser } from '@/shared/model/portal-user.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import { IThread } from '@/shared/model/thread.model';
import { IBadge } from '@/shared/model/badge.model';
import { IDocument } from '@/shared/model/document.model';

export interface ICompany {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  rsnisId?: number;
  aprId?: number;
  name?: string;
  about?: string;
  fields?: string;
  isEinovator?: boolean;
  foundationDate?: Date;
  pib?: string;
  registrationNumber?: string;
  businessName?: string;
  address?: string;
  website?: string;
  email?: string;
  primaryActivity?: string;
  portalUsers?: IPortalUser[];
  advertisements?: IAdvertisement[];
  collaborationOffers?: ICollaboration[];
  collaborationRequests?: ICollaboration[];
  advertisementSupporters?: IAdvertisementSupporter[];
  threadsSenders?: IThread[];
  threadsReceivers?: IThread[];
  badges?: IBadge[];
  documents?: IDocument[];
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
  logo?: IDocument;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public rsnisId?: number,
    public aprId?: number,
    public name?: string,
    public about?: string,
    public fields?: string,
    public isEinovator?: boolean,
    public foundationDate?: Date,
    public pib?: string,
    public registrationNumber?: string,
    public businessName?: string,
    public address?: string,
    public website?: string,
    public email?: string,
    public primaryActivity?: string,
    public portalUsers?: IPortalUser[],
    public advertisements?: IAdvertisement[],
    public collaborationOffers?: ICollaboration[],
    public collaborationRequests?: ICollaboration[],
    public advertisementSupporters?: IAdvertisementSupporter[],
    public threadsSenders?: IThread[],
    public threadsReceivers?: IThread[],
    public badges?: IBadge[],
    public documents?: IDocument[],
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser,
    public logo?: IDocument
  ) {}
}
