import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRiService } from '@/shared/model/ri-service.model';

const baseApiUrl = 'api/ri-services';
const baseApiUrlPregled = 'api/ri-services-pregled';
const baseApiUrlSearchAll = 'api/ri-services-homepage/search';

export default class RiServiceService {
  public find(id: number): Promise<IRiService> {
    return new Promise<IRiService>((resolve, reject) => {
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

  public findPregled(id: number): Promise<IRiService> {
    return new Promise<IRiService>((resolve, reject) => {
      axios
        .get(`${baseApiUrlPregled}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrievePregled(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlPregled + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public countAll(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/ri-services-pregled/count')
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public search(search): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        //.post(baseApiUrlPregled + `?${buildPaginationQueryOpts(paginationQuery)}`, search)
        .post(baseApiUrlPregled + '/search', search)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public searchAll(search): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlSearchAll, search)
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

  public create(entity: IRiService): Promise<IRiService> {
    return new Promise<IRiService>((resolve, reject) => {
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

  public update(entity: IRiService): Promise<IRiService> {
    return new Promise<IRiService>((resolve, reject) => {
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
