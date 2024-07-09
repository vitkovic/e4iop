import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IAdvertisementSupporterStatus } from '@/shared/model/advertisement-supporter-status.model';
import { ICompany } from '@/shared/model/company.model';

export interface IAdvertisementSupporter {
  id?: number;
  hasAccepted?: boolean;
  advertisement?: IAdvertisement;
  company?: ICompany;
  status?: IAdvertisementSupporterStatus;
}

export class AdvertisementSupporter implements IAdvertisementSupporter {
  constructor(
    public id?: number,
    public hasAccepted?: boolean,
    public advertisement?: IAdvertisement,
    public company?: ICompany,
    public status?: IAdvertisementSupporterStatus
  ) {
    this.hasAccepted = this.hasAccepted || false;
  }
}
