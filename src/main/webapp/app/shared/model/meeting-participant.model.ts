import { IMeeting } from '@/shared/model/meeting.model';
import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IMeetingParticipantStatus } from '@/shared/model/meeting-participant-status.model';

export interface IMeetingParticipant {
  id?: number;
  hasAccepted?: boolean;
  hasRemoved?: boolean;
  isOrganizer?: boolean;
  meeting?: IMeeting;
  company?: ICompany;
  portalUser?: IPortalUser;
  status?: IMeetingParticipantStatus;
  notes?: string;
}

export class MeetingParticipant implements IMeetingParticipant {
  constructor(
    public id?: number,
    public hasAccepted?: boolean,
    public hasRemoved?: boolean,
    public isOrganizer?: boolean,
    public meeting?: IMeeting,
    public company?: IPortalUser,
    public portalUser?: IPortalUser,
    public status?: IMeetingParticipantStatus,
    public notes?: string
  ) {
    this.hasAccepted = this.hasAccepted || false;
  }
}
