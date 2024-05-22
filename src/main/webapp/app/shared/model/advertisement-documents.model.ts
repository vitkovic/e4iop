import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IAdvertisementDocuments {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  adds?: IAdvertisement[];
}

export class AdvertisementDocuments implements IAdvertisementDocuments {
  constructor(public id?: number, public title?: string, public content?: string, public note?: string, public adds?: IAdvertisement[]) {}
}
