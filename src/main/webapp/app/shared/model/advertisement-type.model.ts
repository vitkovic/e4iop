import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IAdvertisementType {
  id?: number;
  type?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
}

export class AdvertisementType implements IAdvertisementType {
  constructor(
    public id?: number,
    public type?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
