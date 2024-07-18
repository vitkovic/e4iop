import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

export interface IInquiryDTO {
  advertisement?: IAdvertisement;
  datetime?: Date;
  subject?: string;
  content?: string;
  companySender?: ICompany;
  companyReceiver?: ICompany;
  portalUserSender?: IPortalUser;
}

export class InquiryDTO implements IInquiryDTO {
  constructor(
    public advertisement?: IAdvertisement,
    public datetime?: Date,
    public subject?: string,
    public content?: string,
    public companySender?: ICompany,
    public companyReceiver?: ICompany,
    public portalUserSender?: IPortalUser
  ) {}
}
