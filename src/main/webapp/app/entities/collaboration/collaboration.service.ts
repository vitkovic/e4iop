import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICollaboration } from '@/shared/model/collaboration.model';

const baseApiUrl = 'api/collaborations';
const baseApiCreateCollaborationForAdvertisement = 'api/collaborations/request';
const baseApiConfirmCollaboration = 'api/collaborations/confirm';
const apiGetCollaborationsByCompany = 'api/collaborations/company';
const apiGetCollaborationsByCompanyOffer = 'api/collaborations/company-offer';
const apiGetCollaborationsByCompanyRequest = 'api/collaborations/company-request';
const apiRateCollaborationForCompanyOffer = 'api/collaborations/rate-offer';
const apiRateCollaborationForCompanyRequest = 'api/collaborations/rate-request';

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

  public retrieveByCompany(companyId: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(apiGetCollaborationsByCompany + `?companyId=${companyId}` + `&` + `${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

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
}
