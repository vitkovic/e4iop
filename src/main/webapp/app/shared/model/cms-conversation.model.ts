import { IConversation } from '@/shared/model/conversation.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ICMSConversation {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  conversation?: IConversation;
  users?: IPortalUser[];
}

export class CMSConversation implements ICMSConversation {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public conversation?: IConversation,
    public users?: IPortalUser[]
  ) {}
}
