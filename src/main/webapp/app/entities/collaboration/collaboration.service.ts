import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICollaboration } from '@/shared/model/collaboration.model';
import { deprecate } from 'util';
import { ICompanyRatingsDTO } from '../../shared/model/dto/company-ratings-dto';

const baseApiUrl = 'api/collaborations';
const baseApiCreateCollaborationForAdvertisement = 'api/collaborations/request';
const baseApiConfirmCollaboration = 'api/collaborations/confirm';
const apiCancelCollaboration = 'api/collaborations/cancel';
const apiGetCollaborationsByCompany = 'api/collaborations/company';
const apiGetCollaborationsByCompanyOffer = 'api/collaborations/company-offer';
const apiGetCollaborationsByCompanyRequest = 'api/collaborations/company-request';
const apiRateCollaborationForCompanyOffer = 'api/collaborations/rate-offer';
const apiRateCollaborationForCompanyRequest = 'api/collaborations/rate-request';
const apiGetPendingCollaborationsCountForAdvertisement = 'api/collaborations/count-pending-for-advertisement';
const apiCancelPendingCollaborationsForAdvertisement = 'api/collaborations/cancel-pending-for-advertisement';
const apiGetCompanyRatings = 'api/collaborations/company-ratings';
const apiGetRatedCollaborationsForCompany = 'api/collaborations/company-rated';
const apiGetPageOfRatedCollaborationsForCompany = 'api/collaborations/company-rated-page';
const apiDeleteRating = 'api/collaborations/delete-rating';

export default class CollaborationService {
  public find(id: number): Promise<ICollaboration> {
    return new Promise<ICollaboration>((resolve, reject) => {
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

  public create(entity: ICollaboration): Promise<ICollaboration> {
    return new Promise<ICollaboration>((resolve, reject) => {
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

  public update(entity: ICollaboration): Promise<ICollaboration> {
    return new Promise<ICollaboration>((resolve, reject) => {
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

  public retrieveByCompany(
    companyId: number,
    statusIds: number[],
    collaborationSideFlags: boolean[],
    ratingSideFlags: boolean[],
    paginationQuery?: any
  ): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(
          apiGetCollaborationsByCompany +
            `?companyId=${companyId}` +
            `&` +
            `statusIds=${statusIds}` +
            `&` +
            `collaborationSideFlags=${collaborationSideFlags}` +
            `&` +
            `ratingSideFlags=${ratingSideFlags}` +
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

  /**
   * @deprecated
   * All collaboration filtering is now going through {@link retrieveByCompany} method.
   */
  public retrieveByCompanyOffer(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetCollaborationsByCompanyOffer + `?companyId=${companyId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
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
   * All collaboration filtering is now going through {@link retrieveByCompany} method.
   */
  public retrieveByCompanyRequest(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetCollaborationsByCompanyRequest + `?companyId=${companyId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public createCollaborationForAdvertisement(advertisementId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiCreateCollaborationForAdvertisement}/${advertisementId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public confirmCollaboration(collaborationId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${baseApiConfirmCollaboration}/${collaborationId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public cancelCollaboration(collaborationId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${apiCancelCollaboration}/${collaborationId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public rateCollaborationForCompanyOffer(formData: FormData): Promise<ICollaboration> {
    return new Promise<ICollaboration>((resolve, reject) => {
      axios
        .put(apiRateCollaborationForCompanyOffer, formData, {
          headers: {
            'Content-Type': 'application/json',
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

  public rateCollaborationForCompanyRequest(formData: FormData): Promise<ICollaboration> {
    return new Promise<ICollaboration>((resolve, reject) => {
      axios
        .put(apiRateCollaborationForCompanyRequest, formData, {
          headers: {
            'Content-Type': 'application/json',
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

  public getPendingCollaborationsCountForAdvertisement(advertisementId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiGetPendingCollaborationsCountForAdvertisement}/${advertisementId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public cancelPendingCollaborationsForAdvertisement(advertisementId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${apiCancelPendingCollaborationsForAdvertisement}/${advertisementId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getCompanyRatings(companyId: number): Promise<ICompanyRatingsDTO> {
    return new Promise<CompanyRatingsDTO>((resolve, reject) => {
      axios
        .get(`${apiGetCompanyRatings}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getAllRatedCollaborationsForCompany(companyId: number): Promise<ICollaboration[]> {
    return new Promise<ICollaboration[]>((resolve, reject) => {
      axios
        .get(`${apiGetRatedCollaborationsForCompany}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getPageOfRatedCollaborationsForCompany(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetPageOfRatedCollaborationsForCompany + `?companyId=${companyId}` + `&${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public deleteRating(collaborationId: number, ratingSide: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(apiDeleteRating + `?collaborationId=${collaborationId}` + `&ratingSide=${ratingSide}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
