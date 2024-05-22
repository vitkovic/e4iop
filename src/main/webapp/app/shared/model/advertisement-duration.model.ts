import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IAdvertisementDuration {
  id?: number;
  duration?: number;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
}

export class AdvertisementDuration implements IAdvertisementDuration {
  constructor(
    public id?: number,
    public duration?: number,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
