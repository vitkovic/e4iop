import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IAdvertisementType } from '@/shared/model/advertisement-type.model';
import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';

const baseApiUrl = 'api/advertisements';
const baseApiUrlView = 'api/advertisements/view';
const baseApiUrlSearch = 'api/advertisements/search';
const baseApiUrlSearchSub = 'api/advertisements/search-sub';

const baseApiUrlSearchStatus = 'api/advertisements/searchstatus';
const baseApiUrlSearchStatusSub = 'api/advertisements/searchstatus-sub';

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
const apiGetNewAdvertisementsTypeOffer = 'api/advertisements/new-advertisements-offer';
const apiGetNewAdvertisementsTypeDemand = 'api/advertisements/new-advertisements-demand';

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
    alert(urlgo);
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

  public retrieveSearchSub(search: string, category: number, paginationQuery?: any): Promise<any> {
    var urlgo = '';

    if (search == '' || typeof search == 'undefined' || search == null) {
      urlgo = baseApiUrlSearchAll + `?` + `${buildPaginationQueryOpts(paginationQuery)}`;
    } else {
      urlgo = baseApiUrlSearchSub + `?search=${search}` + `&category=${category}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;
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

  public retrieveBaseSearchSub(search: string, category: number, paginationQuery?: any): Promise<any> {
    const urlgo =
      baseApiUrlSearchSub + `?search=${search}` + `&category=${category}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`;

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

  public retrieveSearchByStatusId(search: string, statusId: number, category: number, paginationQuery?: any): Promise<any> {
    const urlgo =
      baseApiUrlSearchStatusSub +
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

  public retrieveSearchByStatusIdSub(search: string, statusId: number, category: number, paginationQuery?: any): Promise<any> {
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

  public getNewAdvertisementsTypeOffer(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiGetNewAdvertisementsTypeOffer}?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getNewAdvertisementsTypeDemand(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiGetNewAdvertisementsTypeDemand}?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public advertisementCategorizationBranch(instance: IAdvertisement | IAdvertisementSubsubcategory, language = 'sr'): string {
    let branch = '';
    const currentLanguage = language;

    let option: IAdvertisementSubsubcategory;

    if ('subsubcategory' in instance) {
      const advertisement = instance as IAdvertisement;
      option = advertisement.subsubcategory;
    } else {
      option = instance as IAdvertisementSubsubcategory;
    }

    if (currentLanguage === 'sr') {
      branch =
        // option.advertisementSubcategory.advertisementCategory.name + ' / ' +
        option.advertisementSubcategory.name + ' / ' + option.name;

      return branch;
    }

    if (currentLanguage === 'src') {
      branch =
        // option.advertisementSubcategory.advertisementCategory.nameSrc +
        // ' / ' +
        option.advertisementSubcategory.nameSrc + ' / ' + option.nameSrc;

      return branch;
    }

    if (currentLanguage === 'en') {
      branch =
        // option.advertisementSubcategory.advertisementCategory.nameEn +
        // ' / ' +
        option.advertisementSubcategory.nameEn + ' / ' + option.nameEn;

      return branch;
    }

    return branch;
  }

  public advertisementKindsString(advertisement: IAdvertisement, language = 'sr'): string {
    const currentLanguage = language;

    return advertisement.kinds
      .map(kind => {
        if (currentLanguage === 'sr') {
          return kind.kind;
        } else if (currentLanguage === 'src') {
          return kind.kindSrc;
        } else if (currentLanguage === 'en') {
          return kind.kindEn;
        }
        return '';
      })
      .join(', ');
  }

  public advertisementKindTranslation(kind: IAdvertisementKind, language = 'sr'): string {
    const currentLanguage = language;

    if (currentLanguage === 'sr') {
      return `${kind.kind}`;
    } else if (currentLanguage === 'src') {
      return `${kind.kindSrc}`;
    } else if (currentLanguage === 'en') {
      return `${kind.kindEn}`;
    }

    return '';
  }

  public advertisementTypeTranslation(type: IAdvertisementType, language = 'sr'): string {
    const currentLanguage = language;

    if (currentLanguage === 'sr') {
      return `${type.type}`;
    } else if (currentLanguage === 'src') {
      return `${type.typeSrc}`;
    } else if (currentLanguage === 'en') {
      return `${type.typeEn}`;
    }

    return '';
  }
}
