import { IAdvertisement } from '@/shared/model/advertisement.model';

export enum AdvertisementStatusOptions {
  ACTIVE = 'Active',
  INACTIVE = 'Inactive',
  ARCHIVED = 'Archived',
}

export interface IAdvertisementStatus {
  id?: number;
  status?: string;
  statusSrc?: string;
  statusEn?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
}

export class AdvertisementStatus implements IAdvertisementStatus {
  constructor(
    public id?: number,
    public status?: string,
    public statusSrc?: string,
    public statusEn?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
