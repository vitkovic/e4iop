import { IResearcher } from '@/shared/model/researcher.model';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import { IRequestForService } from '@/shared/model/request-for-service.model';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';
import { IRiProcurementProposal } from '@/shared/model/ri-procurement-proposal.model';
import { IServiceProposal } from '@/shared/model/service-proposal.model';
import { IPortalUserRole } from '@/shared/model/portal-user-role.model';
import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';
import { IUser } from './user.model';
import { ICompany } from '@/shared/model/company.model';

export interface IPortalUser {
  id?: string;
  firstName?: string;
  familyName?: string;
  phone?: string;
  position?: string;
  researcher?: IResearcher;
  managedOrganizations?: IRiResearchOrganization[];
  contactOrganizations?: IRiResearchOrganization[];
  managedInfrastructures?: IResearchInfrastructure[];
  contactInfrastructures?: IResearchInfrastructure[];
  requests?: IRequestForService[];
  calendars?: IRiCalendar[];
  riProposals?: IRiProcurementProposal[];
  serviceProposals?: IServiceProposal[];
  portalUserRoles?: IPortalUserRole[];
  userOrganization?: IPortalUserOrganization;
  user?: IUser;
  company?: ICompany;
}

export class PortalUser implements IPortalUser {
  constructor(
    public id?: string,
    public firstName?: string,
    public familyName?: string,
    public phone?: string,
    public position?: string,
    public researcher?: IResearcher,
    public managedOrganizations?: IRiResearchOrganization[],
    public contactOrganizations?: IRiResearchOrganization[],
    public managedInfrastructures?: IResearchInfrastructure[],
    public contactInfrastructures?: IResearchInfrastructure[],
    public requests?: IRequestForService[],
    public calendars?: IRiCalendar[],
    public riProposals?: IRiProcurementProposal[],
    public serviceProposals?: IServiceProposal[],
    public portalUserRoles?: IPortalUserRole[],
    public userOrganization?: IPortalUserOrganization,
    public user?: IUser,
    public company?: ICompany
  ) {}
}
