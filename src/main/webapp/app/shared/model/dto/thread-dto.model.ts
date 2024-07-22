import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICompany } from '@/shared/model/company.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IMeeting } from '@/shared/model/meeting.model';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';

export interface IThreadDTO {
  id?: number;
  subject?: string;
  isFromAdministration?: boolean;
  companySender?: ICompany;
  companyReceiver?: ICompany;
  advertisement?: IAdvertisement;
  collaboration?: ICollaboration;
  meeting?: IMeeting;
  advertisementSupporter?: IAdvertisementSupporter;
  messageCount?: number;
  lastMessageDatetime?: Date;
  lastMessageContent?: string;
  unreadExists?: boolean;
}

export class ThreadDTO implements IThreadDTO {
  constructor(
    public id?: number,
    public subject?: string,
    public isFromAdministration?: boolean,
    public companySender?: ICompany,
    public companyReceiver?: ICompany,
    public advertisement?: IAdvertisement,
    public collaboration?: ICollaboration,
    public meeting?: IMeeting,
    public advertisementSupporter?: IAdvertisementSupporter,
    public messageCount?: number,
    public lastMessageDatetime?: Date,
    public lastMessageContent?: string,
    public unreadExists?: boolean
  ) {}
}
