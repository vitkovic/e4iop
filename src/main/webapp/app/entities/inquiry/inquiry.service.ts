import axios from 'axios';

import { ICompany } from '@/shared/model/company.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

const baseApiUrl = 'api/inquiry';

interface InquiryDTO {
  advertisement: IAdvertisement;
  datetime: Date;
  subject: '';
  content: '';
  companySender: ICompany;
  companyReceiver: ICompany;
  portalUserSender: IPortalUser;
}

export default class InquiryService {
  public create(entity: InquiryDTO): Promise<InquiryDTO> {
    return new Promise<InquiryDTO>((resolve, reject) => {
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
}
