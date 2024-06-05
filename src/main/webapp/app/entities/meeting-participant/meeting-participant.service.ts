import axios from 'axios';

import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

const baseApiUrl = 'api/meeting-participants';
const apiFindAllNotRemovedForCompany = 'api/meeting-participants/company-all';
const apiFindAllForMeeting = 'api/meeting-participants/meeting-all';
const apiFindCompanyOrganizerForMeeting = 'api/meeting-participants/meeting-organizer';
const apiAcceptMeetingForCompany = 'api/meeting-participants/accept/meeting-company';
const apiRemoveMeetingForCompany = 'api/meeting-participants/remove/meeting-company';

export default class MeetingParticipantService {
  public find(id: number): Promise<IMeetingParticipant> {
    return new Promise<IMeetingParticipant>((resolve, reject) => {
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

  public create(entity: IMeetingParticipant): Promise<IMeetingParticipant> {
    return new Promise<IMeetingParticipant>((resolve, reject) => {
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

  public update(entity: IMeetingParticipant): Promise<IMeetingParticipant> {
    return new Promise<IMeetingParticipant>((resolve, reject) => {
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

  public findAllNotRemovedForCompany(companyId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${apiFindAllNotRemovedForCompany}/${companyId}`)
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

  public findCompanyOrganizerForMeeting(meetingId: number): Promise<IMeetingParticipant> {
    return new Promise<IMeetingParticipant>((resolve, reject) => {
      axios
        .get(`${apiFindCompanyOrganizerForMeeting}/${meetingId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public acceptMeetingForCompany(meetingId: number, companyId: number): Promise<IMeetingParticipant> {
    return new Promise<IMeetingParticipant>((resolve, reject) => {
      axios
        .put(`${apiAcceptMeetingForCompany}/${meetingId}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public removeMeetingForCompany(meetingId: number, companyId: number): Promise<IMeetingParticipant> {
    return new Promise<IMeetingParticipant>((resolve, reject) => {
      axios
        .put(`${apiRemoveMeetingForCompany}/${meetingId}/${companyId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
