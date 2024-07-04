import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdvertisement } from '@/shared/model/advertisement.model';


const baseApiUrlSearch = 'api/advertisements/search';

export default class SearchPageService {
  
 public retrieveSearch(): any {
	 console.log("klik");
  }
  
  public retrieveSearchAdv(search:string, paginationQuery?: any): Promise<any> {
	 console.log(baseApiUrlSearch + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearch + `?search=${search}`+  `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
}
