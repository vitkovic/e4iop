import axios from 'axios';

import { IResearchInstitution } from '@/shared/model/research-institution.model';

const baseApiUrl = 'api/research-institutions';
const baseApiUrlOrdered = 'api/research-institutions-ordered';
const baseApiUrlAutoComplete = 'api/research-institutions-autocomplete';

export default class ResearchInstitutionService {
  public find(id: number): Promise<IResearchInstitution> {
    return new Promise<IResearchInstitution>((resolve, reject) => {
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
          console.log('retrieve');
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveOrdered(lang: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlOrdered}/${lang}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAutocomplete(name: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlAutoComplete}/${name}`)
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

  public create(entity: IResearchInstitution): Promise<IResearchInstitution> {
    return new Promise<IResearchInstitution>((resolve, reject) => {
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

  public update(entity: IResearchInstitution): Promise<IResearchInstitution> {
    return new Promise<IResearchInstitution>((resolve, reject) => {
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
