import { IPortalUser } from '@/shared/model/portal-user.model';
import { IAdvertisementCategory } from '@/shared/model/advertisement-category.model';
import { IDocument } from '@/shared/model/document.model';

export interface ICmsAdvertisementCategory {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
  advertisementCategory?: IAdvertisementCategory;
  image?: IDocument;
}

export class CmsAdvertisementCategory implements ICmsAdvertisementCategory {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser,
    public advertisementCategory?: IAdvertisementCategory,
    public image?: IDocument
  ) {}
}
