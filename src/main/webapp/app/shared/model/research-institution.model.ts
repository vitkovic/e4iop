import { IResearcher } from '@/shared/model/researcher.model';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';

export interface IResearchInstitution {
  id?: number;
  name?: string;
  number?: string;
  address?: string;
  registerNumber?: string;
  vatNumber?: string;
  webAdress?: string;
  typeName?: string;
  ownerStructureName?: string;
  ceoName?: string;
  ceoLastName?: string;
  ceoIbi?: string;
  state?: string;
  nameEn?: string;
  nameCyr?: string;
  researchers?: IResearcher[];
  ripo?: IRiResearchOrganization;
}

export class ResearchInstitution implements IResearchInstitution {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public registerNumber?: string,
    public vatNumber?: string,
    public webAdress?: string,
    public typeName?: string,
    public ownerStructureName?: string,
    public ceoName?: string,
    public ceoLastName?: string,
    public ceoIbi?: string,
    public state?: string,
    public nameEn?: string,
    public nameCyr?: string,
    public researchers?: IResearcher[],
    public ripo?: IRiResearchOrganization
  ) {}
}
