import { ITask } from '@/shared/model/task.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ICMSTask {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  task?: ITask;
  users?: IPortalUser[];
}

export class CMSTask implements ICMSTask {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public task?: ITask,
    public users?: IPortalUser[]
  ) {}
}
