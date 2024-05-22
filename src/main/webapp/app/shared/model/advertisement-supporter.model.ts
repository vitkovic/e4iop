import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICompany } from '@/shared/model/company.model';

export interface IAdvertisementSupporter {
  id?: number;
  hasAccepted?: boolean;
  advertisement?: IAdvertisement;
  company?: ICompany;
}

export class AdvertisementSupporter implements IAdvertisementSupporter {
  constructor(public id?: number, public hasAccepted?: boolean, public advertisement?: IAdvertisement, public company?: ICompany) {
    this.hasAccepted = this.hasAccepted || false;
  }
}
