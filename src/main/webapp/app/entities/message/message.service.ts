import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMessage } from '@/shared/model/message.model';

const baseApiUrl = 'api/messages';
const apiGetAllByThreadId = 'api/messages/thread';
const apiPostByThreadId = 'api/messages/thread';
const apiGetCountByThreadId = 'api/messages/thread/count';

export default class MessageService {
  public find(id: number): Promise<IMessage> {
    return new Promise<IMessage>((resolve, reject) => {
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

  public create(entity: IMessage): Promise<IMessage> {
    return new Promise<IMessage>((resolve, reject) => {
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

  public createMessageInThread(formData: FormData): Promise<IMessage> {
    return new Promise<IMessage>((resolve, reject) => {
      axios
        .post(apiPostByThreadId, formData, {
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

  public update(entity: IMessage): Promise<IMessage> {
    return new Promise<IMessage>((resolve, reject) => {
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

  public getAllByThreadId(threadId: number): Promise<Array<IMessage>> {
    return new Promise<Array<IMessage>>((resolve, reject) => {
      axios
        .get(`${apiGetAllByThreadId}/${threadId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public getCountByThreadId(threadId: number): Promise<Number> {
    return new Promise<Number>((resolve, reject) => {
      axios
        .get(`${apiGetCountByThreadId}/${threadId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
