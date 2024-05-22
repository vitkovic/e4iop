import { IRequestForService } from '@/shared/model/request-for-service.model';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';
import { IServiceAvailability } from '@/shared/model/service-availability.model';
import { IServiceAccreditation } from '@/shared/model/service-accreditation.model';
import { IServiceSubtype } from '@/shared/model/service-subtype.model';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

export interface IRiService {
  id?: number;
  nameSr?: string;
  nameEn?: string;
  descriptionSr?: string;
  descriptionEn?: string;
  keywords?: string;
  keywordsCyr?: string;
  keywordsEn?: string;
  accreditationOrganizationName?: string;
  termsOfUseSr?: string;
  termsOfUseEn?: string;
  privacyPolicySr?: string;
  privacyPolicyEn?: string;
  accessPolicySr?: string;
  accessPolicyEn?: string;
  pricingPolicySr?: string;
  pricingPolicyEn?: string;
  nameSrCyr?: string;
  termsOfUseSrCyr?: string;
  accessPolicySrCyr?: string;
  pricingPolicySrCyr?: string;
  descriptionSrCyr?: string;
  requests?: IRequestForService[];
  calendars?: IRiCalendar[];
  availability?: IServiceAvailability;
  accredation?: IServiceAccreditation;
  subtypes?: IServiceSubtype;
  subdomains?: IScientificSubdomain[];
  researchInfrastructure?: IResearchInfrastructure;
  price?: number;
  currency?: string;
}

export class RiService implements IRiService {
  constructor(
    public id?: number,
    public nameSr?: string,
    public nameEn?: string,
    public descriptionSr?: string,
    public descriptionEn?: string,
    public keywords?: string,
    public keywordsCyr?: string,
    public keywordsEn?: string,
    public accreditationOrganizationName?: string,
    public termsOfUseSr?: string,
    public termsOfUseEn?: string,
    public privacyPolicySr?: string,
    public privacyPolicyEn?: string,
    public accessPolicySr?: string,
    public accessPolicyEn?: string,
    public pricingPolicySr?: string,
    public pricingPolicyEn?: string,
    public nameSrCyr?: string,
    public termsOfUseSrCyr?: string,
    public accessPolicySrCyr?: string,
    public pricingPolicySrCyr?: string,
    public descriptionSrCyr?: string,
    public requests?: IRequestForService[],
    public calendars?: IRiCalendar[],
    public availability?: IServiceAvailability,
    public accredation?: IServiceAccreditation,
    public subtypes?: IServiceSubtype,
    public subdomains?: IScientificSubdomain[],
    public researchInfrastructure?: IResearchInfrastructure,
    public price?: number,
    public currency?: string
  ) {}
}
