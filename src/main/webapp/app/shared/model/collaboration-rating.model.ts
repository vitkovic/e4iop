import { ICollaboration } from '@/shared/model/collaboration.model';

export interface ICollaborationRating {
  id?: number;
  number?: number;
  description?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  collaborationOffers?: ICollaboration[];
  collaborationRequests?: ICollaboration[];
}

export class CollaborationRating implements ICollaborationRating {
  constructor(
    public id?: number,
    public number?: number,
    public description?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public collaborationOffers?: ICollaboration[],
    public collaborationRequests?: ICollaboration[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
