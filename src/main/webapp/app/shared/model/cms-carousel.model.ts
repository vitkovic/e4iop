import { IPortalUser } from '@/shared/model/portal-user.model';
import { IDocument } from '@/shared/model/document.model';

export interface ICmsCarousel {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  title?: string;
  link?: string;
  newTab?: boolean;
  ordinalNumber?: number;
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
  image?: IDocument;
}

export class CmsCarousel implements ICmsCarousel {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public title?: string,
    public link?: string,
    public newTab?: boolean,
    public ordinalNumber?: number,
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser,
    public image?: IDocument
  ) {
    this.newTab = this.newTab || false;
  }
}
