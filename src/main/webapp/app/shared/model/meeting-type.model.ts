import { IMeeting } from '@/shared/model/meeting.model';

export interface IMeetingType {
  id?: number;
  type?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  meetings?: IMeeting[];
}

export class MeetingType implements IMeetingType {
  constructor(
    public id?: number,
    public type?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public meetings?: IMeeting[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
