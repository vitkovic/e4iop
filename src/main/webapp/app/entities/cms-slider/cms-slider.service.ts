import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICmsSlider } from '@/shared/model/cms-slider.model';

const baseApiUrl = 'api/cms-sliders';
const apiUploadSliderImage = 'api/cms-sliders/upload-image';
const apiDeleteSliderImage = 'api/cms-sliders/delete-image';

export default class CmsSliderService {
  public find(id: number): Promise<ICmsSlider> {
    return new Promise<ICmsSlider>((resolve, reject) => {
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

  public create(entity: ICmsSlider): Promise<ICmsSlider> {
    return new Promise<ICmsSlider>((resolve, reject) => {
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

  public update(entity: ICmsSlider): Promise<ICmsSlider> {
    return new Promise<ICmsSlider>((resolve, reject) => {
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

  public uploadSliderImage(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiUploadSliderImage, entity, {
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

  public deleteSliderImage(Id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${apiDeleteSliderImage}/${Id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
