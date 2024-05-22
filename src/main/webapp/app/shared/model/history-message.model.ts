import { IMessage } from '@/shared/model/message.model';

export interface IHistoryMessage {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  date?: Date;
  message?: IMessage;
}

export class HistoryMessage implements IHistoryMessage {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public date?: Date,
    public message?: IMessage
  ) {}
}
