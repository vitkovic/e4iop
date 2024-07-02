import axios from 'axios';

import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';

const baseApiUrl = 'api/advertisement-supporters';
const apiManageAdvertisementSupporters = 'api/advertisement-supporters/manage';
const apiRetrieveAdvertisementSupporters = 'api/advertisement-supporters/retrieve';
const apiAcceptForCompany = 'api/advertisement-supporters/accept';

export default class AdvertisementSupporterService {
  public find(id: number): Promise<IAdvertisementSupporter> {
    return new Promise<IAdvertisementSupporter>((resolve, reject) => {
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

  public create(entity: IAdvertisementSupporter): Promise<IAdvertisementSupporter> {
    return new Promise<IAdvertisementSupporter>((resolve, reject) => {
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

  public update(entity: IAdvertisementSupporter): Promise<IAdvertisementSupporter> {
    return new Promise<IAdvertisementSupporter>((resolve, reject) => {
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

  public manageAdvertisementSupporters(entity: FormData): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .post(apiManageAdvertisementSupporters, entity, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAdvertisementSupporters(advertisementId: number): Promise<IAdvertisementSupporter[]> {
    return new Promise<IAdvertisementSupporter[]>((resolve, reject) => {
      axios
        .get(`${apiRetrieveAdvertisementSupporters}/${advertisementId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public acceptForCompany(advertisementId: number, companyId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${apiAcceptForCompany}/${advertisementId}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
