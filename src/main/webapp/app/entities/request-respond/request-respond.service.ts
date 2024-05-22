import axios from 'axios';

import { IRequestRespond } from '@/shared/model/request-respond.model';

const baseApiUrl = 'api/request-responds';
const baseApiUrlFindForService = 'api/request-responds/find-for-service';

export default class RequestRespondService {
  public find(id: number): Promise<IRequestRespond> {
    return new Promise<IRequestRespond>((resolve, reject) => {
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

  public findForRequestService(requestForServiceId: number): Promise<IRequestRespond> {
    return new Promise<IRequestRespond>((resolve, reject) => {
      axios
        .get(`${baseApiUrlFindForService}/${requestForServiceId}`)
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

  public create(entity: IRequestRespond): Promise<IRequestRespond> {
    return new Promise<IRequestRespond>((resolve, reject) => {
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

  public update(entity: IRequestRespond): Promise<IRequestRespond> {
    return new Promise<IRequestRespond>((resolve, reject) => {
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
