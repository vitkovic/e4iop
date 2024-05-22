import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRiProcurementProposal } from '@/shared/model/ri-procurement-proposal.model';

const baseApiUrl = 'api/ri-procurement-proposals';
const baseApiUrlLike = 'api/infrastructure-proposals-likes';

export default class RiProcurementProposalService {
  public find(id: number): Promise<IRiProcurementProposal> {
    return new Promise<IRiProcurementProposal>((resolve, reject) => {
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

  public create(entity: IRiProcurementProposal): Promise<IRiProcurementProposal> {
    return new Promise<IRiProcurementProposal>((resolve, reject) => {
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

  public update(entity: IRiProcurementProposal): Promise<IRiProcurementProposal> {
    return new Promise<IRiProcurementProposal>((resolve, reject) => {
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

  public like(entity: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrlLike}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
