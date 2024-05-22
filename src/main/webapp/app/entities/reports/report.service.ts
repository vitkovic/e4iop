import axios from 'axios';

const baseApiUrlInfrastructureStatistic = 'api/reports/infrastructure-statistic';
const baseApiUrlServiceStatistic = 'api/reports/service-statistic';
const baseApiUrlServiceStatisticNio = 'api/reports/service-statistic-nio';

export default class ReportService {
  public retrieveInfrastructureStatistic(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlInfrastructureStatistic)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveServiceStatistic(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrlServiceStatistic)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public createStatisticExcel(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/reports/service-statistic/downloadExcel', { responseType: 'blob' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public createStatisticInfrastructureExcel(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/reports/infrastructure-statistic/downloadExcel', { responseType: 'blob' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public createStatisticOrganizationsExcel(search: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post('api/reports/organizations-statistic/downloadExcel', search, { responseType: 'blob' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveDetailsWithServices(search?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrlServiceStatisticNio, search)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
