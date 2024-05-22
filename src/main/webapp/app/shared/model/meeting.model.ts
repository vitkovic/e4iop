import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IMeetingType } from '@/shared/model/meeting-type.model';

export interface IMeeting {
  id?: number;
  datetime?: Date;
  isAcepted?: boolean;
  title?: string;
  description?: string;
  comment?: string;
  notes?: string;
  meetingParticipants?: IMeetingParticipant[];
  portalUserOrganizer?: IPortalUser;
  advertisement?: IAdvertisement;
  type?: IMeetingType;
}

export class Meeting implements IMeeting {
  constructor(
    public id?: number,
    public datetime?: Date,
    public isAcepted?: boolean,
    public title?: string,
    public description?: string,
    public comment?: string,
    public notes?: string,
    public meetingParticipants?: IMeetingParticipant[],
    public portalUserOrganizer?: IPortalUser,
    public advertisement?: IAdvertisement,
    public type?: IMeetingType
  ) {
    this.isAcepted = this.isAcepted || false;
  }
}
