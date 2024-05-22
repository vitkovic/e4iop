import { IRequestRespond } from '@/shared/model/request-respond.model';
import { IRiServiceStatus } from '@/shared/model/ri-service-status.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import { IRiService } from '@/shared/model/ri-service.model';

export interface IRiCalendar {
  id?: number;
  startTime?: Date;
  endTime?: Date;
  calNote?: string;
  calRating?: string;
  calComment?: string;
  requestRespond?: IRequestRespond;
  serviceStatus?: IRiServiceStatus;
  portalUsers?: IPortalUser;
  researchInfrastructure?: IResearchInfrastructure;
  service?: IRiService;
}

export class RiCalendar implements IRiCalendar {
  constructor(
    public id?: number,
    public startTime?: Date,
    public endTime?: Date,
    public calNote?: string,
    public calRating?: string,
    public calComment?: string,
    public requestRespond?: IRequestRespond,
    public serviceStatus?: IRiServiceStatus,
    public portalUsers?: IPortalUser,
    public researchInfrastructure?: IResearchInfrastructure,
    public service?: IRiService
  ) {}
}
