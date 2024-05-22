import { ICMSConversation } from '@/shared/model/cms-conversation.model';
import { IHistoryConversation } from '@/shared/model/history-conversation.model';
import { IMessage } from '@/shared/model/message.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IConversation {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  cmsConversation?: ICMSConversation;
  historyConversation?: IHistoryConversation;
  messages?: IMessage;
  users?: IPortalUser[];
}

export class Conversation implements IConversation {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public cmsConversation?: ICMSConversation,
    public historyConversation?: IHistoryConversation,
    public messages?: IMessage,
    public users?: IPortalUser[]
  ) {}
}
