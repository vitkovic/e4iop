import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';
const baseApiUrlSearch = 'api/cms-news/search';

import { ICmsNews } from '@/shared/model/cms-news.model';

const baseApiUrl = 'api/cms-news';
const apiUploadFiles = 'api/cms-news/upload-files';
const apiDeleteFile = 'api/cms-news/delete-file';

export default class CmsNewsService {
  public find(id: number): Promise<ICmsNews> {
    return new Promise<ICmsNews>((resolve, reject) => {
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
  public retrieveSearch(search: string, paginationQuery?: any): Promise<any> {
    console.log(baseApiUrlSearch + `?search=${search}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearch + `?search=${search}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
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

  public create(entity: ICmsNews): Promise<ICmsNews> {
    return new Promise<ICmsNews>((resolve, reject) => {
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

  public update(entity: ICmsNews): Promise<ICmsNews> {
    return new Promise<ICmsNews>((resolve, reject) => {
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
