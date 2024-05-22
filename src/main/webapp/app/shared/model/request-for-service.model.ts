import { IRequestRespond } from '@/shared/model/request-respond.model';
import { IRiService } from '@/shared/model/ri-service.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';

export interface IRequestForService {
  id?: number;
  date?: Date;
  description?: string;
  title?: string;
  startTime?: Date;
  endTime?: Date;
  deadline?: Date;
  respond?: IRequestRespond;
  services?: IRiService;
  users?: IPortalUser;
  organizations?: IPortalUserOrganization;
  rating?: number;
}

export class RequestForService implements IRequestForService {
  constructor(
    public id?: number,
    public date?: Date,
    public description?: string,
    public title?: string,
    public startTime?: Date,
    public endTime?: Date,
    public deadline?: Date,
    public respond?: IRequestRespond,
    public services?: IRiService,
    public users?: IPortalUser,
    public organizations?: IPortalUserOrganization,
    public rating?: number
  ) {}
}
