import { IPortalUser } from '@/shared/model/portal-user.model';
import { IRequestForService } from '@/shared/model/request-for-service.model';
import { IPuoLegalStatus } from '@/shared/model/puo-legal-status.model';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';
import { IResearchInstitution } from '@/shared/model/research-institution.model';

export interface IPortalUserOrganization {
  id?: number;
  legalNameSr?: string;
  legalNameEn?: string;
  shortName?: string;
  webAddress?: string;
  pib?: string;
  registrationNumber?: string;
  keywords?: string;
  address?: string;
  postalCode?: string;
  city?: string;
  country?: string;
  legalNameSrCyr?: string;
  portalUsers?: IPortalUser[];
  requests?: IRequestForService[];
  legalStatus?: IPuoLegalStatus;
  researchOrganization?: IRiResearchOrganization;
  nioInstitution?: IResearchInstitution;
}

export class PortalUserOrganization implements IPortalUserOrganization {
  constructor(
    public id?: number,
    public legalNameSr?: string,
    public legalNameEn?: string,
    public shortName?: string,
    public webAddress?: string,
    public pib?: string,
    public registrationNumber?: string,
    public keywords?: string,
    public address?: string,
    public postalCode?: string,
    public city?: string,
    public country?: string,
    public legalNameSrCyr?: string,
    public portalUsers?: IPortalUser[],
    public requests?: IRequestForService[],
    public legalStatus?: IPuoLegalStatus,
    public researchOrganization?: IRiResearchOrganization,
    public nioInstitution?: IResearchInstitution
  ) {}
}
