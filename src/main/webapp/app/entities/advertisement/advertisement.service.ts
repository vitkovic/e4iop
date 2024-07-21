import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdvertisement } from '@/shared/model/advertisement.model';

const baseApiUrl = 'api/advertisements';
const baseApiUrlView = 'api/advertisements/view';
const baseApiUrlSearch = 'api/advertisements/search';
const baseApiUrlSearchStatus = 'api/advertisements/searchstatus';
const baseApiUrlSearchType = 'api/advertisements/searchtype';
const baseApiUrlSearchTypeStatus = 'api/advertisements/searchtypestatus';
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
const apifindAllForCompanyByStatusAndType = 'api/advertisements/company/status-type';
const apiGetCountAllForCompany = 'api/advertisements/company-count-all';
const apiGetCountActiveForCompany = 'api/advertisements/company-count-active';
const apiGetCountInactiveForCompany = 'api/advertisements/company-count-inactive';

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

  public autoAdv(src: string): Promise<IAdvertisement> {
    return new Promise<IAdvertisement>((resolve, reject) => {
      axios
        .get(`${baseApiUrlAuto}/${src}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findView(id: number): Promise<IAdvertisement> {
    return new Promise<IAdvertisement>((resolve, reject) => {
      axios
        .get(`${baseApiUrlView}/${id}`)
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
  public retrieveSearch(search: string, category: number, paginationQuery?: any): Promise<any> {
    var urlgo = '';

    if (search == '' || typeof search == 'undefined' || search == null) {
      urlgo = baseApiUrlSearchAll + `?` + `${buildPaginationQueryOpts(paginationQuery)}`;
    } else {
      urlgo = baseApiUrlSearch + `?search=${search}` + `&category=${category}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;
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

  public retrieveBaseSearch(search: string, category: number, paginationQuery?: any): Promise<any> {
    const urlgo = baseApiUrlSearch + `?search=${search}` + `&category=${category}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;

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

  public retrieveSearchTypeStatus(type: number, status: number, paginationQuery?: any): Promise<any> {
    const urlgo =
      baseApiUrlSearchTypeStatus + `?type=${type}` + `&` + `status=${status}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;

    //console.log("STATUS");

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

  public retrieveSearchByStatusId(search: string, category: number, paginationQuery?: any): Promise<any> {
    const urlgo =
      baseApiUrlSearchStatus +
      `?search=${search}` +
      +`&statusId=${statusId}` +
      `&category=${category}` +
      `&` +
      `${buildPaginationQueryOpts(paginationQuery)}`;

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

  public findAllForCompanyByStatusAndType(companyId: number, status: string, type: string, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(
          apifindAllForCompanyByStatusAndType +
            `?companyId=${companyId}` +
            `&` +
            `status=${status}` +
            `&` +
            `type=${type}` +
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

  public getCountAllForCompany(companyId: number): Promise<number> {
    return new Promise<number>((resolve, reject) => {
      axios
        .get(`${apiGetCountAllForCompany}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getCountActiveForCompany(companyId: number): Promise<number> {
    return new Promise<number>((resolve, reject) => {
      axios
        .get(`${apiGetCountActiveForCompany}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getCountInactiveForCompany(companyId: number): Promise<number> {
    return new Promise<number>((resolve, reject) => {
      axios
        .get(`${apiGetCountInactiveForCompany}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
