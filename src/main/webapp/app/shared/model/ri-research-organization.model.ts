import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';
import { IResearchInstitution } from '@/shared/model/research-institution.model';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import { IRipoActivityStatus } from '@/shared/model/ripo-activity-status.model';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IRiResearchOrganization {
  id?: number;
  name?: string;
  descriptionSr?: string;
  decsriptionEn?: string;
  logo?: string;
  descriptionSrCyr?: string;
  puOrganization?: IPortalUserOrganization;
  researchInstitution?: IResearchInstitution;
  infrastructures?: IResearchInfrastructure[];
  activityStatus?: IRipoActivityStatus;
  subdomains?: IScientificSubdomain[];
  manager?: IPortalUser;
  contactPerson?: IPortalUser;
}

export class RiResearchOrganization implements IRiResearchOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public descriptionSr?: string,
    public decsriptionEn?: string,
    public logo?: string,
    public descriptionSrCyr?: string,
    public puOrganization?: IPortalUserOrganization,
    public researchInstitution?: IResearchInstitution,
    public infrastructures?: IResearchInfrastructure[],
    public activityStatus?: IRipoActivityStatus,
    public subdomains?: IScientificSubdomain[],
    public manager?: IPortalUser,
    public contactPerson?: IPortalUser
  ) {}
}
