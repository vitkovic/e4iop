import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';
import TranslationService from '@/locale/translation.service';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import { IAdvertisement } from '@/shared/model/advertisement.model';


const baseApiUrl = 'api/advertisements';
const baseApiUrlView = 'api/advertisements/view';

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
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  
  public trt():any {
	  
  }
  
  
  
}