import { IThread } from '@/shared/model/thread.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IMessage {
  id?: number;
  datetime?: Date;
  content?: string;
  isRead?: boolean;
  isDeletedSender?: boolean;
  isDeletedReceiver?: boolean;
  thread?: IThread;
  portalUserSender?: IPortalUser;
  portalUserReceiver?: IPortalUser;
}

export class Message implements IMessage {
  constructor(
    public id?: number,
    public datetime?: Date,
    public content?: string,
    public isRead?: boolean,
    public isDeletedSender?: boolean,
    public isDeletedReceiver?: boolean,
    public thread?: IThread,
    public portalUserSender?: IPortalUser,
    public portalUserReceiver?: IPortalUser
  ) {
    this.isRead = this.isRead || false;
    this.isDeletedSender = this.isDeletedSender || false;
    this.isDeletedReceiver = this.isDeletedReceiver || false;
  }
}
