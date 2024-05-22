import { IRequestForService } from '@/shared/model/request-for-service.model';
import { IServiceOfferRespond } from '@/shared/model/service-offer-respond.model';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';
import { IRequestAnswer } from '@/shared/model/request-answer.model';

export interface IRequestRespond {
  id?: number;
  date?: Date;
  startTime?: Date;
  endTime?: Date;
  price?: number;
  message?: string;
  requestForService?: IRequestForService;
  serviceOfferRespond?: IServiceOfferRespond;
  calendar?: IRiCalendar;
  requestAnswer?: IRequestAnswer;
}

export class RequestRespond implements IRequestRespond {
  constructor(
    public id?: number,
    public date?: Date,
    public startTime?: Date,
    public endTime?: Date,
    public price?: number,
    public message?: string,
    public requestForService?: IRequestForService,
    public serviceOfferRespond?: IServiceOfferRespond,
    public calendar?: IRiCalendar,
    public requestAnswer?: IRequestAnswer
  ) {}
}
