import { IMeeting } from '@/shared/model/meeting.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IMeetingParticipant {
  id?: number;
  hasAccepted?: boolean;
  meeting?: IMeeting;
  portalUser?: IPortalUser;
}

export class MeetingParticipant implements IMeetingParticipant {
  constructor(public id?: number, public hasAccepted?: boolean, public meeting?: IMeeting, public portalUser?: IPortalUser) {
    this.hasAccepted = this.hasAccepted || false;
  }
}
