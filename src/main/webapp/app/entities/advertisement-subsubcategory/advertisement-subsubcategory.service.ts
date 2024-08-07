import axios from 'axios';

import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';

const baseApiUrl = 'api/advertisement-subsubcategories';
const apiGetAllOrdered = 'api/advertisement-subsubcategories/ordered';

export default class AdvertisementSubsubcategoryService {
  public find(id: number): Promise<IAdvertisementSubsubcategory> {
    return new Promise<IAdvertisementSubsubcategory>((resolve, reject) => {
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

  public create(entity: IAdvertisementSubsubcategory): Promise<IAdvertisementSubsubcategory> {
    return new Promise<IAdvertisementSubsubcategory>((resolve, reject) => {
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

  public update(entity: IAdvertisementSubsubcategory): Promise<IAdvertisementSubsubcategory> {
    return new Promise<IAdvertisementSubsubcategory>((resolve, reject) => {
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

  public getAllOrdered(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetAllOrdered)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
