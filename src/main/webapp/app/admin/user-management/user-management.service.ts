import axios from 'axios';
import Vue from 'vue';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import { Authority } from '@/shared/security/authority';

const apiFindAllForCompany = 'api/users/company';
const apiFindAllB2BUsers = 'api/users/b2b';
const apiFindAllB2BCMSUsers = 'api/users/b2b-cms';
const apiActivateUser = 'api/users/activate';

export default class UserManagementService {
  public get(userId: number): Promise<any> {
    return axios.get(`api/users/${userId}`);
  }

  public create(user): Promise<any> {
    return axios.post('api/users', user);
  }

  public update(user): Promise<any> {
    return axios.put('api/users', user);
  }

  public remove(userId: number): Promise<any> {
    return axios.delete(`api/users/${userId}`);
  }

  public retrieve(req?: any): Promise<any> {
    return axios.get(`api/users?${buildPaginationQueryOpts(req)}`);
  }

  public retrieveAuthorities(): Promise<any> {
    return axios.get('api/users/authorities');
  }

  public findAllForCompany(companyId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiFindAllForCompany}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findAllB2BUsers(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiFindAllB2BUsers + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findAllB2BCMSUsers(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiFindAllB2BCMSUsers + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public activateUser(login: string, activate: boolean): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${apiActivateUser}/${login}/${activate}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
