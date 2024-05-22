import { ITask } from '@/shared/model/task.model';

export interface IHistoryTask {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  date?: Date;
  task?: ITask;
}

export class HistoryTask implements IHistoryTask {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public date?: Date,
    public task?: ITask
  ) {}
}
