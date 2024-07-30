import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';
import TranslationService from '@/locale/translation.service';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import { IAdvertisement } from '@/shared/model/advertisement.model';
const baseApiUrlSearch = 'api/advertisements/search';
const baseApiUrlSearchAll = 'api/advertisements/get';
const baseApiUrl = 'api/advertisements';
const baseApiUrlView = 'api/advertisements/view';
const baseApiUrlSearchType = 'api/advertisements/searchtype';
const baseApiUrlSearchKind = 'api/advertisements/searchkind';
const baseApiUrlSearchDates = 'api/advertisements/searchdates';


export default class CMSB2BService {

  public find(id: number): Promise<IAdvertisement> {
    return new Promise<IAdvertisement>((resolve, reject) => {
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
        .get(baseApiUrlSearchAll + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
   public retrieveSearch(search: string, category: number, paginationQuery?: any): Promise<any> {
    var urlgo = '';

    
      urlgo = baseApiUrlSearch + `?search=${search}` + `&category=${category}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;
    
   

    return new Promise<any>((resolve, reject) => {
      axios
        .get(urlgo)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
   public retrieveSearchDates(from: string, to: string, paginationQuery?: any): Promise<any> {
    var urlgo = '';

    
      urlgo = baseApiUrlSearchDates + `?from=${from}` + `&to=${to}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;
    
   

    return new Promise<any>((resolve, reject) => {
      axios
        .get(urlgo)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
   public retrieveSearchType(type: number, paginationQuery?: any): Promise<any> {
    const urlgo = baseApiUrlSearchType + `?type=${type}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;

    return new Promise<any>((resolve, reject) => {
      axios
        .get(urlgo)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
   public retrieveSearchKind(kind: number, paginationQuery?: any): Promise<any> {
    const urlgo = baseApiUrlSearchKind + `?kind=${kind}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;

    return new Promise<any>((resolve, reject) => {
      axios
        .get(urlgo)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  public retrieveSearchCompany(paginationQuery?: any): Promise<any> {
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
  
}