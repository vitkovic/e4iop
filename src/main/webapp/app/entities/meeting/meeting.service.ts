import axios from 'axios';

import { IMeeting } from '@/shared/model/meeting.model';

const baseApiUrl = 'api/meetings';
const apiFindAllForCompany = 'api/meetings/company';
const apiCreateMeetingWithParticipants = 'api/meetings/new';

export default class MeetingService {
  public find(id: number): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
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

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
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

  public create(entity: IMeeting): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
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

  public update(entity: IMeeting): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
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

  public createMeetingWithParticipants(meeting: IMeeting, organizerId: number, participantIds: number[] = []): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
      axios
        .post(`${apiCreateMeetingWithParticipants}/?organizerId=${organizerId}&participantIds=${participantIds}`, meeting)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findAllForCompany(companyId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiFindAllForCompany}/${companyId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
