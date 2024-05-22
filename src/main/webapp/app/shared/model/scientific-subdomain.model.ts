import { IScientificDomain } from '@/shared/model/scientific-domain.model';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import { IRiService } from '@/shared/model/ri-service.model';

export interface IScientificSubdomain {
  id?: number;
  subdomain?: string;
  subdomainEn?: string;
  subdomainCyr?: string;
  domain?: IScientificDomain;
  organizations?: IRiResearchOrganization[];
  infrastructures?: IResearchInfrastructure[];
  services?: IRiService[];
}

export class ScientificSubdomain implements IScientificSubdomain {
  constructor(
    public id?: number,
    public subdomain?: string,
    public subdomainEn?: string,
    public subdomainCyr?: string,
    public domain?: IScientificDomain,
    public organizations?: IRiResearchOrganization[],
    public infrastructures?: IResearchInfrastructure[],
    public services?: IRiService[]
  ) {}
}
