import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';

export enum AdvertisementSupporterStatusOptions {
  ACCEPTED = 'Invitation accepted',
  REJECTED = 'Invitation rejected',
  NO_RESPONSE = 'No response',
}

export interface IAdvertisementSupporterStatus {
  id?: number;
  nameSr?: string;
  nameSrc?: string;
  nameEn?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisementSupporters?: IAdvertisementSupporter[];
}

export class AdvertisementSupporterStatus implements IAdvertisementSupporterStatus {
  constructor(
    public id?: number,
    public nameSr?: string,
    public nameSrc?: string,
    public nameEn?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisementSupporters?: IAdvertisementSupporter[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
