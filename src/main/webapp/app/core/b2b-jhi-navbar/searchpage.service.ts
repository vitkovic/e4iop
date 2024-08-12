import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdvertisement } from '@/shared/model/advertisement.model';


const baseApiUrlSearch = 'api/advertisements/search';

	const baseApiUrlSearchAdv = '/api/advertisements/search';
	const baseApiUrlSearchCmp = '/api/companies/search';
	const baseApiUrlSearchQA = '/api/cms-questions/search';
	const baseApiUrlSearchNews = '/api/cms-news/search';

export default class SearchPageService {
	
   public itemsPerPage = 10000000000;
   public queryCount: number = null;
   public page = 1;
   public propOrder = 'id';
   public reverse = false;
 
 
 public retrieveSearch(): any {
	// console.log("klik");
  }
  
  public retrieveSearchAdv(search:string): Promise<any> {
	  
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
	  
	  
	// console.log(baseApiUrlSearchAdv + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearchAdv + `?search=${search}`+ `&category=1` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
   public retrieveSearchCmp(search:string): Promise<any> {
	  
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
	  
	  
	  
	  
	 console.log(baseApiUrlSearchCmp + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearchCmp + `?search=${search}`+ `&category=1` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
  
  public retrieveSearchQA(search:string): Promise<any> {
	  
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
	  
	  
	  
	  
	 console.log(baseApiUrlSearchQA + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearchQA + `?search=${search}`+ `&category=1` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
  public retrieveSearchNW(search:string): Promise<any> {
	  
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
	  
	  
	  
	  
	 console.log(baseApiUrlSearchNews + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearchNews + `?search=${search}`+ `&category=1` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
  
  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }
  
}
