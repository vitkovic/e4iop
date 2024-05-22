import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import RequestForServiceService from '../request-for-service/request-for-service.service';
import { IRequestForService } from '@/shared/model/request-for-service.model';

import ServiceOfferRespondService from '../service-offer-respond/service-offer-respond.service';
import { IServiceOfferRespond } from '@/shared/model/service-offer-respond.model';

import RiCalendarService from '../ri-calendar/ri-calendar.service';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';

import AlertService from '@/shared/alert/alert.service';
import { IRequestRespond, RequestRespond } from '@/shared/model/request-respond.model';
import RequestRespondService from './request-respond.service';

import RequestAnswerService from '../request-answer/request-answer.service';
import { IRequestAnswer } from '@/shared/model/request-answer.model';

import AccountService from '@/account/account.service';

const validations: any = {
  requestRespond: {
    date: {},
    startTime: {},
    endTime: {},
    price: {},
    message: {},
  },
};

@Component({
  validations,
})
export default class RequestRespondUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;
  public requestRespond: IRequestRespond = new RequestRespond();

  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;

  public requestForServices: IRequestForService[] = [];

  @Inject('serviceOfferRespondService') private serviceOfferRespondService: () => ServiceOfferRespondService;

  public serviceOfferResponds: IServiceOfferRespond[] = [];

  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;

  @Inject('requestAnswerService') private requestAnswerService: () => RequestAnswerService;

  public requestAnswers: IRequestAnswer[] = [];

  @Inject('accountService') private accountService: () => AccountService;

  public riCalendars: IRiCalendar[] = [];
  public isSaving = false;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestRespondId) {
        vm.retrieveRequestRespond(to.params.requestRespondId);
      } else if (to.params.requestForServiceId) {
        vm.retrieveRequestRespondForRequestService(to.params.requestForServiceId);
        vm.requestRespond.date = new Date();
      } else {
        vm.requestRespond.date = new Date();
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public sendToServiceOfferRespond(): void {
    this.serviceOfferRespondService()
      .findForRequestRespond(this.requestRespond.id)
      .then(res => {
        if (res.id) {
          this.$router.push({
            name: 'ServiceOfferRespondView',
            params: { serviceOfferRespondId: JSON.stringify(res.id).replace(/["]/g, '') },
          });
        }
      })
      .catch(() => {
        const por = this.$t('riportalApp.serviceOfferRespond.notExists');
        this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'info', duration: 10000 });
      });
  }

  public save(): void {
    this.isSaving = true;
    if (this.requestRespond.id) {
      this.requestRespondService()
        .update(this.requestRespond)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.requestRespond.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.requestRespondService()
        .create(this.requestRespond)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.requestRespond.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.requestRespond[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.requestRespond[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.requestRespond[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.requestRespond[field] = null;
    }
  }

  public retrieveRequestRespond(requestRespondId): void {
    this.requestRespondService()
      .find(requestRespondId)
      .then(res => {
        res.date = new Date(res.date);
        res.startTime = new Date(res.startTime);
        res.endTime = new Date(res.endTime);
        this.requestRespond = res;
      });
  }

  public retrieveRequestRespondForRequestService(requestForServiceId): void {
    this.requestRespondService()
      .findForRequestService(requestForServiceId)
      .then(res => {
        if (res.id) {
          res.date = new Date(res.date);
          res.startTime = new Date(res.startTime);
          res.endTime = new Date(res.endTime);
          this.requestRespond = res;
        } else {
          this.requestForServiceService()
            .find(requestForServiceId)
            .then(res => {
              this.requestRespond.requestForService = res;
            });
        }
      })
      .catch(error => {
        this.requestForServiceService()
          .find(requestForServiceId)
          .then(res => {
            this.requestRespond.requestForService = res;
          });
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.requestForServiceService()
      .retrieve()
      .then(res => {
        this.requestForServices = res.data;
      });
    this.serviceOfferRespondService()
      .retrieve()
      .then(res => {
        this.serviceOfferResponds = res.data;
      });
    this.riCalendarService()
      .retrieve()
      .then(res => {
        this.riCalendars = res.data;
      });
    this.requestAnswerService()
      .retrieve()
      .then(res => {
        this.requestAnswers = res.data;
      });
  }

  public hasAnyRole(roles: any): boolean {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
