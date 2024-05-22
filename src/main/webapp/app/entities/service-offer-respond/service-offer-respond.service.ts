import axios from 'axios';

import { IServiceOfferRespond } from '@/shared/model/service-offer-respond.model';

const baseApiUrl = 'api/service-offer-responds';
const baseApiUrlFindForRequestRespond = 'api/service-offer-responds/find-for-request-respond';

export default class ServiceOfferRespondService {
  public find(id: number): Promise<IServiceOfferRespond> {
    return new Promise<IServiceOfferRespond>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findForRequestRespond(requestRespond: number): Promise<IServiceOfferRespond> {
    return new Promise<IServiceOfferRespond>((resolve, reject) => {
      axios
        .get(`${baseApiUrlFindForRequestRespond}/${requestRespond}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IServiceOfferRespond): Promise<IServiceOfferRespond> {
    return new Promise<IServiceOfferRespond>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IServiceOfferRespond): Promise<IServiceOfferRespond> {
    return new Promise<IServiceOfferRespond>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
