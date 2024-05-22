import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import RequestRespondService from '../request-respond/request-respond.service';
import { IRequestRespond } from '@/shared/model/request-respond.model';

import RiServiceStatusService from '../ri-service-status/ri-service-status.service';
import { IRiServiceStatus } from '@/shared/model/ri-service-status.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import ResearchInfrastructureService from '../research-infrastructure/research-infrastructure.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

import RiServiceService from '../ri-service/ri-service.service';
import { IRiService } from '@/shared/model/ri-service.model';

import AlertService from '@/shared/alert/alert.service';
import { IRiCalendar, RiCalendar } from '@/shared/model/ri-calendar.model';
import RiCalendarService from './ri-calendar.service';

const validations: any = {
  riCalendar: {
    startTime: {},
    endTime: {},
    calNote: {},
    calRating: {},
    calComment: {},
  },
};

@Component({
  validations,
})
export default class RiCalendarUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;
  public riCalendar: IRiCalendar = new RiCalendar();

  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;

  public requestResponds: IRequestRespond[] = [];

  @Inject('riServiceStatusService') private riServiceStatusService: () => RiServiceStatusService;

  public riServiceStatuses: IRiServiceStatus[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  public researchInfrastructures: IResearchInfrastructure[] = [];

  @Inject('riServiceService') private riServiceService: () => RiServiceService;

  public riServices: IRiService[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riCalendarId) {
        vm.retrieveRiCalendar(to.params.riCalendarId);
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
    if (this.riCalendar.id) {
      this.riCalendarService()
        .update(this.riCalendar)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riCalendar.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riCalendarService()
        .create(this.riCalendar)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riCalendar.created', { param: param.id });
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
      this.riCalendar[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.riCalendar[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.riCalendar[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.riCalendar[field] = null;
    }
  }

  public retrieveRiCalendar(riCalendarId): void {
    this.riCalendarService()
      .find(riCalendarId)
      .then(res => {
        res.startTime = new Date(res.startTime);
        res.endTime = new Date(res.endTime);
        this.riCalendar = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.requestRespondService()
      .retrieve()
      .then(res => {
        this.requestResponds = res.data;
      });
    this.riServiceStatusService()
      .retrieve()
      .then(res => {
        this.riServiceStatuses = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.researchInfrastructureService()
      .retrieve()
      .then(res => {
        this.researchInfrastructures = res.data;
      });
    this.riServiceService()
      .retrieve()
      .then(res => {
        this.riServices = res.data;
      });
  }
}
