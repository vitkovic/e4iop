import axios from 'axios';

import { IRiFinanceSource } from '@/shared/model/ri-finance-source.model';

const baseApiUrl = 'api/ri-finance-sources';

export default class RiFinanceSourceService {
  public find(id: number): Promise<IRiFinanceSource> {
    return new Promise<IRiFinanceSource>((resolve, reject) => {
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

  public create(entity: IRiFinanceSource): Promise<IRiFinanceSource> {
    return new Promise<IRiFinanceSource>((resolve, reject) => {
      axios
        .post(baseApiUrl, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IRiFinanceSource): Promise<IRiFinanceSource> {
    return new Promise<IRiFinanceSource>((resolve, reject) => {
      axios
        .put(baseApiUrl, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
