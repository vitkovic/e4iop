import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IHistoryAdvertisement {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  date?: Date;
  add?: IAdvertisement;
}

export class HistoryAdvertisement implements IHistoryAdvertisement {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public note?: string,
    public date?: Date,
    public add?: IAdvertisement
  ) {}
}
