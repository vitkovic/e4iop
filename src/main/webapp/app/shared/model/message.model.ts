import { IThread } from '@/shared/model/thread.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IMessage {
  id?: number;
  datetime?: Date;
  content?: string;
  isReadSender?: boolean;
  isReadReceiver?: boolean;
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
    public isReadSender?: boolean,
    public isReadReceiver?: boolean,
    public isDeletedSender?: boolean,
    public isDeletedReceiver?: boolean,
    public thread?: IThread,
    public portalUserSender?: IPortalUser,
    public portalUserReceiver?: IPortalUser
  ) {
    this.isReadSender = this.isReadSender || false;
    this.isReadReceiver = this.isReadReceiver || false;
    this.isDeletedSender = this.isDeletedSender || false;
    this.isDeletedReceiver = this.isDeletedReceiver || false;
  }
}
