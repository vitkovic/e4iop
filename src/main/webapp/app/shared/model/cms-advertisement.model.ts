import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface ICMSAdvertisement {
  id?: number;
  title?: string;
  content?: string;
  note?: string;
  add?: IAdvertisement;
}

export class CMSAdvertisement implements ICMSAdvertisement {
  constructor(public id?: number, public title?: string, public content?: string, public note?: string, public add?: IAdvertisement) {}
}
