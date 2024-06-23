import { IMeeting } from '@/shared/model/meeting.model';
import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

export interface ICalendarEventDTO {
  meeting?: IMeeting;
  meetingParticipant?: IMeetingParticipant;
  color?: string;
  textColor?: string;
}

export class CalendarEventDTO implements ICalendarEventDTO {
  constructor(
    public meeting?: IMeeting,
    public meetingParticipant?: IMeetingParticipant,
    public color?: string,
    public textColor?: string
  ) {}
}
