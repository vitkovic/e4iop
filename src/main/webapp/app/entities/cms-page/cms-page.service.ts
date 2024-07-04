import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICmsPage } from '@/shared/model/cms-page.model';

const baseApiUrl = 'api/cms-pages';
const apiGetSpecificByType = 'api/cms-pages/specific';
const apiUploadFiles = 'api/cms-pages/upload-files';
const apiDeleteFile = 'api/cms-pages/delete-file';

export default class CmsPageService {
  public find(id: number): Promise<ICmsPage> {
    return new Promise<ICmsPage>((resolve, reject) => {
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

  public create(entity: ICmsPage): Promise<ICmsPage> {
    return new Promise<ICmsPage>((resolve, reject) => {
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

  public update(entity: ICmsPage): Promise<ICmsPage> {
    return new Promise<ICmsPage>((resolve, reject) => {
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

  public getSpecificByType(type: string): Promise<ICmsPage> {
    return new Promise<ICmsPage>((resolve, reject) => {
      axios
        .get(`${apiGetSpecificByType}/${type}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public uploadFiles(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiUploadFiles, entity, {
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

  public deleteFile(id: number, fileId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${apiDeleteFile}/${id}/${fileId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
