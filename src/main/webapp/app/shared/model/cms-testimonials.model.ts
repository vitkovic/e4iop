import { IPortalUser } from '@/shared/model/portal-user.model';
import { IDocument } from '@/shared/model/document.model';

export interface ICmsTestimonials {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  title?: string;
  content?: string;
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
  image?: IDocument;
}

export class CmsTestimonials implements ICmsTestimonials {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public title?: string,
    public content?: string,
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser,
    public image?: IDocument
  ) {}
}
