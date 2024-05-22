import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import CollaborationRatingService from '../collaboration-rating/collaboration-rating.service';
import { ICollaborationRating } from '@/shared/model/collaboration-rating.model';

import CollaborationStatusService from '../collaboration-status/collaboration-status.service';
import { ICollaborationStatus } from '@/shared/model/collaboration-status.model';

import AlertService from '@/shared/alert/alert.service';
import { ICollaboration, Collaboration } from '@/shared/model/collaboration.model';
import CollaborationService from './collaboration.service';

const validations: any = {
  collaboration: {
    datetime: {
      required,
    },
    isAccepted: {},
    commentOffer: {},
    commentRequest: {},
  },
};

@Component({
  validations,
})
export default class CollaborationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('collaborationService') private collaborationService: () => CollaborationService;
  public collaboration: ICollaboration = new Collaboration();

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];

  @Inject('collaborationRatingService') private collaborationRatingService: () => CollaborationRatingService;

  @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;

  public collaborationStatuses: ICollaborationStatus[] = [];

  public collaborationRatings: ICollaborationRating[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collaborationId) {
        vm.retrieveCollaboration(to.params.collaborationId);
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
    if (this.collaboration.id) {
      this.collaborationService()
        .update(this.collaboration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.collaboration.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.collaborationService()
        .create(this.collaboration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.collaboration.created', { param: param.id });
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
      this.collaboration[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.collaboration[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.collaboration[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.collaboration[field] = null;
    }
  }

  public retrieveCollaboration(collaborationId): void {
    this.collaborationService()
      .find(collaborationId)
      .then(res => {
        res.datetime = new Date(res.datetime);
        this.collaboration = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
    this.collaborationRatingService()
      .retrieve()
      .then(res => {
        this.collaborationRatings = res.data;
      });
    this.collaborationRatingService()
      .retrieve()
      .then(res => {
        this.collaborationRatings = res.data;
      });
    this.collaborationStatusService()
      .retrieve()
      .then(res => {
        this.collaborationStatuses = res.data;
      });
  }
}
