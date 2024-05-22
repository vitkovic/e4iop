import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IAdvertisementImages {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  adds?: IAdvertisement[];
}

export class AdvertisementImages implements IAdvertisementImages {
  constructor(public id?: number, public title?: string, public content?: string, public note?: string, public adds?: IAdvertisement[]) {}
}
