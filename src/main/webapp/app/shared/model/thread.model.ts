import { IMessage } from '@/shared/model/message.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IMeeting } from '@/shared/model/meeting.model';
import { ICompany } from '@/shared/model/company.model';

export interface IThread {
  id?: number;
  subject?: string;
  isFromAdministration?: boolean;
  messages?: IMessage[];
  advertisements?: IAdvertisement[];
  collaborations?: ICollaboration[];
  meetings?: IMeeting[];
  companySender?: ICompany;
  companyReceiver?: ICompany;
}

export class Thread implements IThread {
  constructor(
    public id?: number,
    public subject?: string,
    public isFromAdministration?: boolean,
    public messages?: IMessage[],
    public advertisements?: IAdvertisement[],
    public collaborations?: ICollaboration[],
    public meetings?: IMeeting[],
    public companySender?: ICompany,
    public companyReceiver?: ICompany
  ) {}
}
