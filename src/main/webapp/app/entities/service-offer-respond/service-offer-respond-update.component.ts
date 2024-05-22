import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AnswerToOfferService from '../answer-to-offer/answer-to-offer.service';
import { IAnswerToOffer } from '@/shared/model/answer-to-offer.model';

import RequestRespondService from '../request-respond/request-respond.service';
import { IRequestRespond } from '@/shared/model/request-respond.model';

import AlertService from '@/shared/alert/alert.service';
import { IServiceOfferRespond, ServiceOfferRespond } from '@/shared/model/service-offer-respond.model';
import ServiceOfferRespondService from './service-offer-respond.service';

const validations: any = {
  serviceOfferRespond: {
    date: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class ServiceOfferRespondUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('serviceOfferRespondService') private serviceOfferRespondService: () => ServiceOfferRespondService;
  public serviceOfferRespond: IServiceOfferRespond = new ServiceOfferRespond();

  @Inject('answerToOfferService') private answerToOfferService: () => AnswerToOfferService;

  public answerToOffers: IAnswerToOffer[] = [];

  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;

  public requestResponds: IRequestRespond[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      debugger;
      if (to.params.serviceOfferRespondId) {
        vm.retrieveServiceOfferRespond(to.params.serviceOfferRespondId);
      } else if (to.params.requestRespondId) {
        vm.retrieveServiceOfferRespondForRequestRespond(to.params.requestRespondId);
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

  public save(): void {
    this.isSaving = true;
    if (this.serviceOfferRespond.id) {
      this.serviceOfferRespondService()
        .update(this.serviceOfferRespond)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceOfferRespond.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.serviceOfferRespondService()
        .create(this.serviceOfferRespond)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceOfferRespond.created', { param: param.id });
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
      this.serviceOfferRespond[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.serviceOfferRespond[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.serviceOfferRespond[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.serviceOfferRespond[field] = null;
    }
  }

  public retrieveServiceOfferRespond(serviceOfferRespondId): void {
    this.serviceOfferRespondService()
      .find(serviceOfferRespondId)
      .then(res => {
        res.date = new Date(res.date);
        this.serviceOfferRespond = res;
      });
  }

  public retrieveServiceOfferRespondForRequestRespond(requestRespondId): void {
    this.serviceOfferRespondService()
      .findForRequestRespond(requestRespondId)
      .then(res => {
        if (res.id) {
          res.date = new Date(res.date);
          this.serviceOfferRespond = res;
        } else {
          this.requestRespondService()
            .find(requestRespondId)
            .then(res => {
              this.serviceOfferRespond.requestRespond = res;
            });
        }
      })
      .catch(error => {
        this.requestRespondService()
          .find(requestRespondId)
          .then(res => {
            this.serviceOfferRespond.requestRespond = res;
          });
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.answerToOfferService()
      .retrieve()
      .then(res => {
        this.answerToOffers = res.data;
      });

    this.requestRespondService()
      .retrieve()
      .then(res => {
        this.requestResponds = res.data;
      });
  }
}
