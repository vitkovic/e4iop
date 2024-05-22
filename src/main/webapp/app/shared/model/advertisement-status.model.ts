import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IAdvertisementStatus {
  id?: number;
  status?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
}

export class AdvertisementStatus implements IAdvertisementStatus {
  constructor(
    public id?: number,
    public status?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
