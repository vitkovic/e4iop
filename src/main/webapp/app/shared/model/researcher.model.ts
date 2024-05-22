import { IPortalUser } from '@/shared/model/portal-user.model';
import { IResearchInstitution } from '@/shared/model/research-institution.model';

export interface IResearcher {
  id?: number;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  birthDate?: Date;
  portalUser?: IPortalUser;
  institutions?: IResearchInstitution[];
}

export class Researcher implements IResearcher {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public birthDate?: Date,
    public portalUser?: IPortalUser,
    public institutions?: IResearchInstitution[]
  ) {}
}
