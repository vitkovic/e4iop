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
const baseApiUrlSearchCompany = 'api/advertisements/searchcompany';
const baseApiUrlCollab = 'api/collaborations/status';
const baseApiUrlInquiry = 'api/inquiry';
const baseApiUrlSearchCollabDates = 'api/collaborations/searchdates';
const baseApiUrlSearchUsersDates = 'api/portal-users/searchdates';
const baseApiUrlUsers = 'api/portal-users/get';
export default class CMSB2BService {

public retrieveUsers(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlUsers + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

public retrieveInquiries(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlInquiry + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
public retrieveCoolaborationsByStatus(status:number,paginationQuery?: any): Promise<any> {
	
	//console.log(status + " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlCollab +   `?status=${status}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

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
  
  
   public retrieveSearchCollabDates(from: string, to: string, status:number,paginationQuery?: any): Promise<any> {
    var urlgo = '';

    
      urlgo = baseApiUrlSearchCollabDates + `?from=${from}` + `&to=${to}` + `&` + `status=${status}`+  `&` + `${buildPaginationQueryOpts(paginationQuery)}`;
    
   

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
  
  
  public retrieveSearchUsersDates(fetching:boolean, from: string, to: string, paginationQuery?: any): Promise<any> {
    var urlgo = '';

    
      urlgo = baseApiUrlSearchUsersDates + `?from=${from}` + `&to=${to}` + `&` + `eagerload=${fetching}`+  `&` + `${buildPaginationQueryOpts(paginationQuery)}`;
    
   

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
  public retrieveSearchCompany(company:number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearchCompany + `?company=${company}` + `&${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
}