import { IMessage } from '@/shared/model/message.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICompany } from '@/shared/model/company.model';

export interface IThread {
  id?: number;
  subject?: string;
  messages?: IMessage[];
  advertisements?: IAdvertisement[];
  companySender?: ICompany;
  companyReceiver?: ICompany;
}

export class Thread implements IThread {
  constructor(
    public id?: number,
    public subject?: string,
    public messages?: IMessage[],
    public advertisements?: IAdvertisement[],
    public companySender?: ICompany,
    public companyReceiver?: ICompany
  ) {}
}
