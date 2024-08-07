import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';


import { ICmsQuestion } from '@/shared/model/cms-question.model';

const baseApiUrl = 'api/cms-questions';
const baseApiUrlSearch = 'api/cms-questions/search';
export default class CmsQuestionService {

  
 
  public find(id: number): Promise<ICmsQuestion> {
    return new Promise<ICmsQuestion>((resolve, reject) => {
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
public retrieveSearch(search:string,  paginationQuery?: any): Promise<any> {
	// console.log(baseApiUrlSearch + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearch + `?search=${search}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
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

  public create(entity: ICmsQuestion): Promise<ICmsQuestion> {
    return new Promise<ICmsQuestion>((resolve, reject) => {
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

  public update(entity: ICmsQuestion): Promise<ICmsQuestion> {
    return new Promise<ICmsQuestion>((resolve, reject) => {
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
