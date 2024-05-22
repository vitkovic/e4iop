import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IMeeting } from '@/shared/model/meeting.model';
import { IDocument } from '@/shared/model/document.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { ICompany } from '@/shared/model/company.model';
import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';
import { IAdvertisementDuration } from '@/shared/model/advertisement-duration.model';
import { IAdvertisementType } from '@/shared/model/advertisement-type.model';
import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
import { IThread } from '@/shared/model/thread.model';

export interface IAdvertisement {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  activationDatetime?: Date;
  expirationDatetime?: Date;
  deletionDatetime?: Date;
  title?: string;
  description?: string;
  budget?: number;
  conditions?: string;
  advertisementSupporters?: IAdvertisementSupporter[];
  collaborations?: ICollaboration[];
  meetings?: IMeeting[];
  documents?: IDocument[];
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
  company?: ICompany;
  status?: IAdvertisementStatus;
  duration?: IAdvertisementDuration;
  type?: IAdvertisementType;
  kind?: IAdvertisementKind;
  subsubcategory?: IAdvertisementSubsubcategory;
  threads?: IThread[];
}

export class Advertisement implements IAdvertisement {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public activationDatetime?: Date,
    public expirationDatetime?: Date,
    public deletionDatetime?: Date,
    public title?: string,
    public description?: string,
    public budget?: number,
    public conditions?: string,
    public advertisementSupporters?: IAdvertisementSupporter[],
    public collaborations?: ICollaboration[],
    public meetings?: IMeeting[],
    public documents?: IDocument[],
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser,
    public company?: ICompany,
    public status?: IAdvertisementStatus,
    public duration?: IAdvertisementDuration,
    public type?: IAdvertisementType,
    public kind?: IAdvertisementKind,
    public subsubcategory?: IAdvertisementSubsubcategory,
    public threads?: IThread[]
  ) {}
}
