import { IDocument } from '@/shared/model/document.model';
import { ICmsPageType } from '@/shared/model/cms-page-type.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface ICmsPage {
  id?: number;
  createdAt?: Date;
  modifiedAt?: Date;
  title?: string;
  content?: string;
  documents?: IDocument[];
  type?: ICmsPageType;
  createdBy?: IPortalUser;
  modifiedBy?: IPortalUser;
}

export class CmsPage implements ICmsPage {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public title?: string,
    public content?: string,
    public documents?: IDocument[],
    public type?: ICmsPageType,
    public createdBy?: IPortalUser,
    public modifiedBy?: IPortalUser
  ) {}
}
