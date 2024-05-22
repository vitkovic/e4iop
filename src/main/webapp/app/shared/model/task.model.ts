import { ICMSTask } from '@/shared/model/cms-task.model';
import { IHistoryTask } from '@/shared/model/history-task.model';
import { IConversation } from '@/shared/model/conversation.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ITask {
  id?: number;
  title?: string;
  description?: string;
  cmsTask?: ICMSTask;
  historyTask?: IHistoryTask;
  conversations?: IConversation;
  users?: IPortalUser[];
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public cmsTask?: ICMSTask,
    public historyTask?: IHistoryTask,
    public conversations?: IConversation,
    public users?: IPortalUser[]
  ) {}
}
