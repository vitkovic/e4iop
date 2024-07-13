import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompany } from '@/shared/model/company.model';

const baseApiUrl = 'api/companies';
const baseApiUrlSearch = 'api/companies/search';
const updateCompanyCreatedApiUrl = 'api/companies/update-created';
const updateCompanyModifiedApiUrl = 'api/companies/update-modified';
const apiUploadLogo = 'api/companies/upload-logo';
const apiUploadImages = 'api/companies/upload-images'; // Deprecated
const apiUploadDocuments = 'api/companies/upload-documents'; // Deprecated
const apiUploadFiles = 'api/companies/upload-files';
const deleteLogoApi = 'api/companies/delete-logo';
const deleteImageApi = 'api/companies/delete-image'; // Deprecated
const deleteDocumentApi = 'api/companies/delete-document'; // Deprecated
const apiDeleteFile = 'api/companies/delete-file';
const apiGetAllAutocompleteByName = 'api/companies/autocomplete';

export default class CompanyService {
  public find(id: number): Promise<ICompany> {
    return new Promise<ICompany>((resolve, reject) => {
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
  public retrieveSearch(search: string, paginationQuery?: any): Promise<any> {
    console.log(baseApiUrlSearch + `?search=${search}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`);
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlSearch + `?search=${search}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
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

  public create(entity: ICompany): Promise<ICompany> {
    return new Promise<ICompany>((resolve, reject) => {
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

  public update(entity: ICompany): Promise<ICompany> {
    return new Promise<ICompany>((resolve, reject) => {
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

  public updateCompanyModifiedBy(companyId: number, userId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${updateCompanyModifiedApiUrl}/${companyId}/${userId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public updateCompanyCreatedBy(companyId: number, userId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${updateCompanyCreatedApiUrl}/${companyId}/${userId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public uploadCompanyLogo(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiUploadLogo, entity, {
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

  /**
   * @deprecated
   * Upload images is now going through {@link uploadFiles} method.
   */
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

  /**
   * @deprecated
   * Upload documents is now going through {@link uploadFiles} method.
   */
  public uploadCompanyDocuments(entity: FormData): Promise<any> {
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

  public retrieveImage(filename): string {
    return 'api/retrieve/image/' + filename;
  }

  public retrieveDocument(filename): string {
    return 'api/retrieve/document/' + filename;
  }

  public deleteLogo(companyId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${deleteLogoApi}/${companyId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * @deprecated
   * Delete image is now going through {@link deleteFile} method.
   */
  public deleteImage(companyId: number, imageId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${deleteImageApi}/${companyId}/${imageId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * @deprecated
   * Delete document is now going through {@link deleteFile} method.
   */
  public deleteDocument(companyId: number, documentId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${deleteDocumentApi}/${companyId}/${documentId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public deleteFile(companyId: number, fileId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${apiDeleteFile}/${companyId}/${fileId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getAllAutocompleteByName(name: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetAllAutocompleteByName + `/${name}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getAllAutocompleteByNameWithoutExlcuded(name: string, excludedIds: number[]): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetAllAutocompleteByName + `/${name}/${excludedIds}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
