import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';

const baseApiUrl = 'api/portal-user-organizations';
const baseApiUrlPregled = 'api/portal-user-organizations-pregled';
const baseApiUrlbyInstitution = 'api/portal-user-organizations-institutions';
const baseApiUrlAutoComplete = 'api/portal-user-organizations/search';

export default class PortalUserOrganizationService {
  public find(id: number): Promise<IPortalUserOrganization> {
    return new Promise<IPortalUserOrganization>((resolve, reject) => {
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

  public search(search): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrl + '/search-report', search)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveDetails(search?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrl + '/report/details', search)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveForReport(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/report' + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findPregled(id: number): Promise<IPortalUserOrganization> {
    return new Promise<IPortalUserOrganization>((resolve, reject) => {
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
        .get('api/portal-user-organizations-pregled/count')
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveWithoutPagination(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlPregled + `/without-pagination`)
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

  public create(entity: IPortalUserOrganization): Promise<IPortalUserOrganization> {
    return new Promise<IPortalUserOrganization>((resolve, reject) => {
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

  public update(entity: IPortalUserOrganization): Promise<IPortalUserOrganization> {
    return new Promise<IPortalUserOrganization>((resolve, reject) => {
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

  public retrieveByInstitution(instid: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlbyInstitution}/${instid}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAutocomplete(name: string, language: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlAutoComplete}/${name}/${language}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
