import { IAnswerToOffer } from '@/shared/model/answer-to-offer.model';
import { IRequestRespond } from '@/shared/model/request-respond.model';

export interface IServiceOfferRespond {
  id?: number;
  date?: Date;
  description?: string;
  offerAnswer?: IAnswerToOffer;
  requestRespond?: IRequestRespond;
}

export class ServiceOfferRespond implements IServiceOfferRespond {
  constructor(
    public id?: number,
    public date?: Date,
    public description?: string,
    public offerAnswer?: IAnswerToOffer,
    public requestRespond?: IRequestRespond
  ) {}
}
