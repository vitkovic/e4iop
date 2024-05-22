import { ICompany } from '@/shared/model/company.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaborationRating } from '@/shared/model/collaboration-rating.model';
import { ICollaborationStatus } from '@/shared/model/collaboration-status.model';
import { IThread } from '@/shared/model/thread.model';

export interface ICollaboration {
  id?: number;
  datetime?: Date;
  isAccepted?: boolean;
  commentOffer?: string;
  commentRequest?: string;
  companyOffer?: ICompany;
  companyRequest?: ICompany;
  advertisement?: IAdvertisement;
  ratingOffer?: ICollaborationRating;
  ratingRequest?: ICollaborationRating;
  status?: ICollaborationStatus;
  threads?: IThread[];
}

export class Collaboration implements ICollaboration {
  constructor(
    public id?: number,
    public datetime?: Date,
    public isAccepted?: boolean,
    public commentOffer?: string,
    public commentRequest?: string,
    public companyOffer?: ICompany,
    public companyRequest?: ICompany,
    public advertisement?: IAdvertisement,
    public ratingOffer?: ICollaborationRating,
    public ratingRequest?: ICollaborationRating,
    public status?: ICollaborationStatus,
    public threads?: IThread[]
  ) {}
}
