import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

export interface IMeetingParticipantStatus {
  id?: number;
  statusSr?: string;
  statusSrc?: string;
  statusEn?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  meetingParticipants?: IMeetingParticipant[];
}

export class MeetingParticipantStatus implements IMeetingParticipantStatus {
  constructor(
    public id?: number,
    public statusSr?: string,
    public statusSrc?: string,
    public statusEn?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public meetingParticipants?: IMeetingParticipant[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
