import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

const baseApiUrl = 'api/research-infrastructures';
const baseApiUrlPregled = 'api/research-infrastructures-pregled';

export default class ResearchInfrastructureService {
  public find(id: number): Promise<IResearchInfrastructure> {
    return new Promise<IResearchInfrastructure>((resolve, reject) => {
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

  public uploadImages(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post('api/upload-images', entity, {
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

  public uploadLogo(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post('api/upload-logo', entity, {
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

  public deleteImage(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete('api/research-infrastructures/images/' + id)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findPregled(id: number): Promise<IResearchInfrastructure> {
    return new Promise<IResearchInfrastructure>((resolve, reject) => {
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
        .get('api/research-infrastructures-pregled/count')
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

  public create(entity: IResearchInfrastructure): Promise<IResearchInfrastructure> {
    return new Promise<IResearchInfrastructure>((resolve, reject) => {
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

  public update(entity: IResearchInfrastructure): Promise<IResearchInfrastructure> {
    return new Promise<IResearchInfrastructure>((resolve, reject) => {
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
