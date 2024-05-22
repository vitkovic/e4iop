import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IThread } from '@/shared/model/thread.model';

const baseApiUrl = 'api/threads';
const apiGetThreadsByCompany = 'api/threads/company';
const apiGetThreadsByCompanySender = 'api/threads/company-sender';
const apiGetThreadsByCompanyReceiver = 'api/threads/company-receiver';
const apiDeleteIfEmpty = 'api/threads/delete-empty';
const apiHideUserMessages = 'api/threads/hide-user-messages';

export default class ThreadService {
  public find(id: number): Promise<IThread> {
    return new Promise<IThread>((resolve, reject) => {
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

  public retrieveByCompany(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetThreadsByCompany + `?companyId=${companyId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveByCompanySender(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetThreadsByCompanySender + `?companyId=${companyId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveByCompanyReceiver(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetThreadsByCompanyReceiver + `?companyId=${companyId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
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

  public deleteIfEmpty(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${apiDeleteIfEmpty}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public hideUserMessages(id: number, isDeletedSender: boolean): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${apiHideUserMessages}/${id}/${isDeletedSender}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IThread): Promise<IThread> {
    return new Promise<IThread>((resolve, reject) => {
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

  public update(entity: IThread): Promise<IThread> {
    return new Promise<IThread>((resolve, reject) => {
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
