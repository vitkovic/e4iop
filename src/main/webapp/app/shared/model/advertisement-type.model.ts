import { IAdvertisement } from '@/shared/model/advertisement.model';

export enum AdvertisementTypeOptions {
  OFFER = 'Offer',
  DEMAND = 'Demand',
}

export interface IAdvertisementType {
  id?: number;
  type?: string;
  typeSrc?: string;
  typeEn?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
}

export class AdvertisementType implements IAdvertisementType {
  constructor(
    public id?: number,
    public type?: string,
    public typeSrc?: string,
    public typeEn?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
