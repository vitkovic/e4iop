import { IAdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';
import { ICmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';

export interface IAdvertisementCategory {
  id?: number;
  name?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  advertisementSubcategories?: IAdvertisementSubcategory[];
  cmsAdvertisementCategories?: ICmsAdvertisementCategory[];
}

export class AdvertisementCategory implements IAdvertisementCategory {
  constructor(
    public id?: number,
    public name?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public advertisementSubcategories?: IAdvertisementSubcategory[],
    public cmsAdvertisementCategories?: ICmsAdvertisementCategory[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
