import axios from 'axios';

import { IMeeting } from '@/shared/model/meeting.model';

const baseApiUrl = 'api/meetings';
const apiFindAllForCompany = 'api/meetings/company';
const apiCreateMeetingWithParticipants = 'api/meetings/new';
const apiEditMeetingWithParticipants = 'api/meetings/edit';
const apiCreateICSForMeeting = 'api/meetings/create-ics';
const apiUpdateMeetingNotes = 'api/meetings/update-notes';

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

  public createMeetingWithParticipants(
    meeting: IMeeting,
    organizerId: number,
    participantIds: number[] = [],
    nonB2BparticipantIds: string[] = []
  ): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
      axios
        .post(
          `${apiCreateMeetingWithParticipants}/?organizerId=${organizerId}&participantIds=${participantIds}&nonB2BparticipantIds=${nonB2BparticipantIds}`,
          meeting
        )
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public editMeetingWithParticipants(
    meeting: IMeeting,
    meetingId: number,
    participantIdsToAdd: number[] = [],
    participantIdsToRemove: number[] = [],
    nonB2BparticipantIdsToAdd: string[] = [],
    nonB2BparticipantIdsToRemove: string[] = []
  ): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
      axios
        .put(
          `${apiEditMeetingWithParticipants}` +
            `/?meetingId=${meetingId}` +
            `&participantIdsToAdd=${participantIdsToAdd}` +
            `&participantIdsToRemove=${participantIdsToRemove}` +
            `&nonB2BparticipantIdsToAdd=${nonB2BparticipantIdsToAdd}` +
            `&nonB2BparticipantIdsToRemove=${nonB2BparticipantIdsToRemove}`,
          meeting
        )
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

  public createICS(meetingId: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .post(`${apiCreateICSForMeeting}/${meetingId}`)
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
   * Notes are now personal for every MeetingParticipant, appropriate method
   * can be found in component {@link meeting-participant.service}.
   */
  public updateMeetingNotes(formData: FormData): Promise<IMeeting> {
    return new Promise<IMeeting>((resolve, reject) => {
      axios
        .put(apiUpdateMeetingNotes, formData, {
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
