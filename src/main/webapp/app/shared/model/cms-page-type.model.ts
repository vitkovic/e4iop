import { ICmsPage } from '@/shared/model/cms-page.model';

export enum CmsPageTypeOptions {
  COOKIES = 'cookies',
  PRIVACY_POLICY = 'privacy-policy',
  TERMS_OF_USE = 'terms-of-use',
  ABOUT = 'about',
}

export interface ICmsPageType {
  id?: number;
  type?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  cmsPages?: ICmsPage[];
}

export class CmsPageType implements ICmsPageType {
  constructor(
    public id?: number,
    public type?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public cmsPages?: ICmsPage[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
