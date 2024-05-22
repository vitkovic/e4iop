import { IConversation } from '@/shared/model/conversation.model';

export interface IHistoryConversation {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  date?: Date;
  conversation?: IConversation;
}

export class HistoryConversation implements IHistoryConversation {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public date?: Date,
    public conversation?: IConversation
  ) {}
}
