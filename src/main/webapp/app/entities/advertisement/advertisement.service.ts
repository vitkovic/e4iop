import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdvertisement } from '@/shared/model/advertisement.model';

const baseApiUrl = 'api/advertisements';
const baseApiUrlSearch = 'api/advertisements/search';
const baseApiUrlSearchAll = 'api/advertisements/get';
const updateCreatedByApiUrl = 'api/advertisements/update-created';
const updateModifiedByApiUrl = 'api/advertisements/update-modified';
const apiUploadFiles = 'api/advertisements/upload-files';
const apiUploadImages = 'api/advertisements/upload-images';
const apiDeleteImage = 'api/advertisements/delete-image';
const apiUploadDocuments = 'api/advertisements/upload-documents';
const apiDeleteDocument = 'api/advertisements/delete-document';
const apiUpdateStatus = 'api/advertisements/update-status';
const apiBrowse = 'api/advertisements/browse';
const apiFindAllByCompany = 'api/advertisements/company';
const apiFindAllByCompanyIdAndStatusId = 'api/advertisements/company-status';
const apiFindAllByCompanyIdAndNotStatusId = 'api/advertisements/company-not-status';
const apiFindAllByStatusId = 'api/advertisements/status';
const apiFindAllByCompanyIdAndTypeId = 'api/advertisements/company-type';

export default class AdvertisementService {
	
	
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
  public retrieveSearch(search:string, category:number, paginationQuery?: any): Promise<any> {
    const urlgo='';
    
	if (search == '' || typeof(search)=='undefined' || search == null) 
	{
		urlgo = baseApiUrlSearchAll +`?` + `${buildPaginationQueryOpts(paginationQuery)}`
	 	
	} else {
	
		urlgo = baseApiUrlSearch + `?search=${search}`+ `&category=${category}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`
		
	
	}
	
	console.log(urlgo);
	
	// console.log(baseApiUrlSearch + `?search=${search}`+ `&category=${category}`+ `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
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
  public retrieveBrowse(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiBrowse + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveForCompany(companyId: number, status?: string, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(
          apiFindAllByCompany + `?companyId=${companyId}` + `&` + `status=${status}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`
        )
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAllByCompanyAndStatusId(companyId: number, statusId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(
          apiFindAllByCompanyIdAndStatusId +
            `?companyId=${companyId}` +
            `&` +
            `statusId=${statusId}` +
            `&` +
            `${buildPaginationQueryOpts(paginationQuery)}`
        )
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAllByCompanyAndNotStatusId(companyId: number, statusId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(
          apiFindAllByCompanyIdAndNotStatusId +
            `?companyId=${companyId}` +
            `&` +
            `statusId=${statusId}` +
            `&` +
            `${buildPaginationQueryOpts(paginationQuery)}`
        )
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAllByStatusId(statusId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiFindAllByStatusId + `?statusId=${statusId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveAllByCompanyAndTypeId(companyId: number, typeId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(
          apiFindAllByCompanyIdAndTypeId +
            `?companyId=${companyId}` +
            `&` +
            `typeId=${typeId}` +
            `&` +
            `${buildPaginationQueryOpts(paginationQuery)}`
        )
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

  public create(entity: IAdvertisement): Promise<IAdvertisement> {
    return new Promise<IAdvertisement>((resolve, reject) => {
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

  public update(entity: IAdvertisement): Promise<IAdvertisement> {
    return new Promise<IAdvertisement>((resolve, reject) => {
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

  public updateCreatedBy(advertisementId: number, userId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${updateCreatedByApiUrl}/${advertisementId}/${userId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public updateModifiedBy(advertisementId: number, userId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${updateModifiedByApiUrl}/${advertisementId}/${userId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public uploadFiles(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiUploadFiles, entity, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public uploadImages(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiUploadImages, entity, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public uploadDocuments(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiUploadDocuments, entity, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveImage(filename): string {
    return 'api/retrieve/image/' + filename;
  }

  public retrieveDocument(filename): string {
    return 'api/retrieve/document/' + filename;
  }

  public deleteImage(id: number, imageId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${apiDeleteImage}/${id}/${imageId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public deleteDocument(id: number, documentId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${apiDeleteDocument}/${id}/${documentId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public updateStatus(id: number, statusId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${apiUpdateStatus}/${id}/${statusId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
