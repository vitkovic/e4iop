import { ICollaboration } from '@/shared/model/collaboration.model';

export interface ICollaborationStatus {
  id?: number;
  status?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  collaborations?: ICollaboration[];
}

export class CollaborationStatus implements ICollaborationStatus {
  constructor(
    public id?: number,
    public status?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public collaborations?: ICollaboration[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
