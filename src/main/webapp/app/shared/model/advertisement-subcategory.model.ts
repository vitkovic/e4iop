import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
import { IAdvertisementCategory } from '@/shared/model/advertisement-category.model';

export interface IAdvertisementSubcategory {
  id?: number;
  name?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisementSubsubcategories?: IAdvertisementSubsubcategory[];
  advertisementCategory?: IAdvertisementCategory;
}

export class AdvertisementSubcategory implements IAdvertisementSubcategory {
  constructor(
    public id?: number,
    public name?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisementSubsubcategories?: IAdvertisementSubsubcategory[],
    public advertisementCategory?: IAdvertisementCategory
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
