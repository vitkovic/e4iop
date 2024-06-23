import { IMeeting } from '@/shared/model/meeting.model';

export interface IMeetingParticipantNonB2B {
  id?: number;
  meeting?: IMeeting;
  email?: string;
}

export class MeetingParticipantNonB2B implements IMeetingParticipantNonB2B {
  constructor(public id?: number, public meeting?: IMeeting, public email?: string) {}
}
