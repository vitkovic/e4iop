import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IRiProcurementProposal {
  id?: number;
  date?: Date;
  name?: string;
  technicalSpecification?: string;
  purpose?: string;
  portalUser?: IPortalUser;
}

export class RiProcurementProposal implements IRiProcurementProposal {
  constructor(
    public id?: number,
    public date?: Date,
    public name?: string,
    public technicalSpecification?: string,
    public purpose?: string,
    public portalUser?: IPortalUser
  ) {}
}
