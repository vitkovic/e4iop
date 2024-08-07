import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IAdvertisementKind {
  id?: number;
  kind?: string;
  kindSrc?: string;
  kindEn?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
}

export class AdvertisementKind implements IAdvertisementKind {
  constructor(
    public id?: number,
    public kind?: string,
    public kindSrc?: string,
    public kindEn?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
