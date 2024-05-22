import { IRiService } from '@/shared/model/ri-service.model';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';
import { IRiStatus } from '@/shared/model/ri-status.model';
import { IRiAccessMode } from '@/shared/model/ri-access-mode.model';
import { IRiAccessType } from '@/shared/model/ri-access-type.model';
import { IRiCategory } from '@/shared/model/ri-category.model';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';
import { IRiSubcategory } from '@/shared/model/ri-subcategory.model';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IInfrastructureImage } from '@/shared/model/infrastructure-image.model';

export interface IResearchInfrastructure {
  id?: number;
  nameSr?: string;
  nameEn?: string;
  departmentName?: string;
  riLogo?: string;
  riWebsite?: string;
  descriptionSr?: string;
  descriptionEn?: string;
  technicalSpecificationSr?: string;
  technicalSpecificationEn?: string;
  technicalSpecificationLinkSr?: string;
  technicalSpecificationLinkEn?: string;
  userManualLinkSr?: string;
  userManualLinkEn?: string;
  keywords?: string;
  keywordsEn?: string;
  keywordsCyr?: string;
  nameSrCyr?: string;
  descriptionSrCyr?: string;
  technicalSpecificationSrCyr?: string;
  technicalSpecificationLinkSrCyr?: string;
  userManualLinkSrCyr?: string;
  services?: IRiService[];
  calendars?: IRiCalendar[];
  status?: IRiStatus;
  accessMode?: IRiAccessMode;
  accessType?: IRiAccessType;
  category?: IRiCategory;
  subdomains?: IScientificSubdomain[];
  subcategories?: IRiSubcategory[];
  owner?: IRiResearchOrganization;
  manager?: IPortalUser;
  contactPerson?: IPortalUser;
  termsOfUseSr?: string;
  termsOfUseEn?: string;
  termsOfUseSrCyr?: string;
  images?: IInfrastructureImage[];
  acquisitionYear?: number;
  purchaseValueEuro?: number;
  purchaseValueDin?: number;
  financeSource?: string;
  projectName?: string;
  showFullDescription?: boolean;
}

export class ResearchInfrastructure implements IResearchInfrastructure {
  constructor(
    public id?: number,
    public nameSr?: string,
    public nameEn?: string,
    public departmentName?: string,
    public riLogo?: string,
    public riWebsite?: string,
    public descriptionSr?: string,
    public descriptionEn?: string,
    public technicalSpecificationSr?: string,
    public technicalSpecificationEn?: string,
    public technicalSpecificationLinkSr?: string,
    public technicalSpecificationLinkEn?: string,
    public userManualLinkSr?: string,
    public userManualLinkEn?: string,
    public keywords?: string,
    public keywordsEn?: string,
    public keywordsCyr?: string,
    public nameSrCyr?: string,
    public descriptionSrCyr?: string,
    public technicalSpecificationSrCyr?: string,
    public technicalSpecificationLinkSrCyr?: string,
    public userManualLinkSrCyr?: string,
    public services?: IRiService[],
    public calendars?: IRiCalendar[],
    public status?: IRiStatus,
    public accessMode?: IRiAccessMode,
    public accessType?: IRiAccessType,
    public category?: IRiCategory,
    public subdomains?: IScientificSubdomain[],
    public subcategories?: IRiSubcategory[],
    public owner?: IRiResearchOrganization,
    public manager?: IPortalUser,
    public contactPerson?: IPortalUser,
    public termsOfUseSr?: string,
    public termsOfUseEn?: string,
    public termsOfUseSrCyr?: string,
    public images?: IInfrastructureImage[],
    public acquisitionYear?: number,
    public purchaseValueEuro?: number,
    public purchaseValueDin?: number,
    public financeSource?: string,
    public projectName?: string
  ) {}
}
