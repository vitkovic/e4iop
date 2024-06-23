import axios from 'axios';

import { IMeetingParticipantNonB2B } from '@/shared/model/meeting-participant-non-b2b.model';

const baseApiUrl = 'api/meeting-participants-non-b2b';
const apiFindAllForMeeting = 'api/meeting-participants-non-b2b/meeting-all';

export default class MeetingParticipantNonB2BService {
  public find(id: number): Promise<IMeetingParticipantNonB2B> {
    return new Promise<IMeetingParticipantNonB2B>((resolve, reject) => {
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

  public create(entity: IMeetingParticipantNonB2B): Promise<IMeetingParticipantNonB2B> {
    return new Promise<IMeetingParticipantNonB2B>((resolve, reject) => {
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

  public update(entity: IMeetingParticipantNonB2B): Promise<IMeetingParticipantNonB2B> {
    return new Promise<IMeetingParticipantNonB2B>((resolve, reject) => {
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

  public findAllForMeeting(meetingId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiFindAllForMeeting}/${meetingId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
