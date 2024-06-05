import { IMeeting } from '@/shared/model/meeting.model';
import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IMeetingParticipant {
  id?: number;
  hasAccepted?: boolean;
  hasRemoved?: boolean;
  isOrganizer?: boolean;
  meeting?: IMeeting;
  company?: ICompany;
  portalUser?: IPortalUser;
}

export class MeetingParticipant implements IMeetingParticipant {
  constructor(
    public id?: number,
    public hasAccepted?: boolean,
    public hasRemoved?: boolean,
    public isOrganizer?: boolean,
    public meeting?: IMeeting,
    public company?: IPortalUser,
    public portalUser?: IPortalUser
  ) {
    this.hasAccepted = this.hasAccepted || false;
  }
}
