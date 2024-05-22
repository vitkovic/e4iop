import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IAdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';

export interface IAdvertisementSubsubcategory {
  id?: number;
  name?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisements?: IAdvertisement[];
  advertisementSubcategory?: IAdvertisementSubcategory;
}

export class AdvertisementSubsubcategory implements IAdvertisementSubsubcategory {
  constructor(
    public id?: number,
    public name?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisements?: IAdvertisement[],
    public advertisementSubcategory?: IAdvertisementSubcategory
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
