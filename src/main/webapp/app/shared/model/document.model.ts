import { ICompany } from '@/shared/model/company.model';
import { IBadge } from '@/shared/model/badge.model';
import { ICmsSlider } from '@/shared/model/cms-slider.model';
import { ICmsCarousel } from '@/shared/model/cms-carousel.model';
import { ICmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';
import { ICmsTestimonials } from '@/shared/model/cms-testimonials.model';
import { IDocumentType } from '@/shared/model/document-type.model';
import { IDocumentKind } from '@/shared/model/document-kind.model';
import { ICmsPage } from '@/shared/model/cms-page.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';

export interface IDocument {
  id?: number;
  filename?: string;
  companyLogos?: ICompany[];
  badges?: IBadge[];
  cmsSliders?: ICmsSlider[];
  cmsCarouses?: ICmsCarousel[];
  cmsAdvertisementCategories?: ICmsAdvertisementCategory[];
  cmsTestimonials?: ICmsTestimonials[];
  type?: IDocumentType;
  kind?: IDocumentKind;
  companies?: ICompany[];
  cmsPages?: ICmsPage[];
  advertisements?: IAdvertisement[];
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public filename?: string,
    public companyLogos?: ICompany[],
    public badges?: IBadge[],
    public cmsSliders?: ICmsSlider[],
    public cmsCarouses?: ICmsCarousel[],
    public cmsAdvertisementCategories?: ICmsAdvertisementCategory[],
    public cmsTestimonials?: ICmsTestimonials[],
    public type?: IDocumentType,
    public kind?: IDocumentKind,
    public companies?: ICompany[],
    public cmsPages?: ICmsPage[],
    public advertisements?: IAdvertisement[]
  ) {}
}
