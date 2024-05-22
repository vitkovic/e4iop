import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPortalUser } from '@/shared/model/portal-user.model';

const baseApiUrl = 'api/portal-users';
const baseApiUrlActivation = 'api/portal-users/for-activation';
const userApiUrl = 'api/portal-users/user';

export default class PortalUserService {
  constructor(private store: Store<any>) {}

  public find(id: number): Promise<IPortalUser> {
    return new Promise<IPortalUser>((resolve, reject) => {
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

  public findForActivation(id: number): Promise<IPortalUser> {
    return new Promise<IPortalUser>((resolve, reject) => {
      axios
        .get(`${baseApiUrlActivation}/${id}`)
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

  public hasAnyRole(roles: any): Promise<boolean> {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return new Promise(resolve => {
            resolve(true);
          });
        }
      }
    } else {
      return this.getRoles();
    }

    return new Promise(resolve => {
      resolve(false);
    });
  }

  public retrieveForOrganizationOfCurrentUser(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/for-organization')
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveForOrganization(puId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/for-given-organization/' + puId)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getRoles(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}-get-roles`)
        .then(res => {
          let rol = res.data;
          this.store.commit('setRoles', rol);
          resolve(true);
        })
        .catch(err => {
          resolve(false);
        });
    });
  }

  public countAll(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/portal-users-pregled/count')
        .then(res => {
          resolve(res.data);
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

  public create(entity: IPortalUser): Promise<IPortalUser> {
    return new Promise<IPortalUser>((resolve, reject) => {
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

  public update(entity: IPortalUser): Promise<IPortalUser> {
    return new Promise<IPortalUser>((resolve, reject) => {
      axios
        .put(`${baseApiUrlActivation}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public updateRoles(entity: IPortalUser): Promise<IPortalUser> {
    return new Promise<IPortalUser>((resolve, reject) => {
      axios
        .put(`${baseApiUrlActivation}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public get userRoles(): any {
    return this.store.getters.roles;
  }

  public findByUserId(id: number): Promise<IPortalUser> {
    return new Promise<IPortalUser>((resolve, reject) => {
      axios
        .get(`${userApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
