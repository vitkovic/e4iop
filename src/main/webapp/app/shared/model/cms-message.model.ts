import { IMessage } from '@/shared/model/message.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ICMSMessage {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  message?: IMessage;
  users?: IPortalUser[];
}

export class CMSMessage implements ICMSMessage {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public message?: IMessage,
    public users?: IPortalUser[]
  ) {}
}
