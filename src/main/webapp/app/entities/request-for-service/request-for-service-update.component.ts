import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, requiredIf } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT, DATE_FORMAT, DATE_TIME_FORMAT } from '@/shared/date/filters';

import RequestRespondService from '../request-respond/request-respond.service';
import { IRequestRespond } from '@/shared/model/request-respond.model';

import RiServiceService from '../ri-service/ri-service.service';
import { IRiService, RiService } from '@/shared/model/ri-service.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import PortalUserOrganizationService from '../portal-user-organization/portal-user-organization.service';
import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IRequestForService, RequestForService } from '@/shared/model/request-for-service.model';
import RequestForServiceService from './request-for-service.service';

import AccountService from '@/account/account.service';

const validations: any = {
  requestForService: {
    date: {},
    description: {},
    title: {},
    startTime: {},
    endTime: {},
    deadline: {},
    users: {
      required: requiredIf(function () {
        return this.hasAnyRole(['PA']) || this.hasAnyAuthority('ROLE_ADMIN');
      }),
    },
    organizations: {
      /*
      required: requiredIf(function () {
        return this.hasAnyRole(['PA']) || this.hasAnyAuthority('ROLE_ADMIN');
      }),
      */
    },
  },
};

@Component({
  validations,
})
export default class RequestForServiceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;
  public requestForService: IRequestForService = new RequestForService();

  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;

  public requestResponds: IRequestRespond[] = [];

  @Inject('riServiceService') private riServiceService: () => RiServiceService;

  public riServices: IRiService[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;

  @Inject('accountService') private accountService: () => AccountService;

  public service: IRiService = new RiService();

  public portalUserOrganizations: IPortalUserOrganization[] = [];
  public isSaving = false;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestForServiceId) {
        vm.retrieveRequestForService(to.params.requestForServiceId);
      } else if (to.params.serviceId) {
        vm.prepareService(to.params.serviceId);
        vm.requestForService.date = new Date();
      } else {
        vm.requestForService.date = new Date();
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

  public prepareService(serviceId) {
    this.service.id = serviceId;
    this.requestForService.services = this.service;
  }

  public save(): void {
    this.isSaving = true;
    if (this.requestForService.id) {
      this.requestForServiceService()
        .update(this.requestForService)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.requestForService.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.requestForServiceService()
        .create(this.requestForService)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.requestForService.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
      // return format(date, DATE_TIME_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.requestForService[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.requestForService[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.requestForService[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.requestForService[field] = null;
    }
  }

  public retrieveRequestForService(requestForServiceId): void {
    this.requestForServiceService()
      .find(requestForServiceId)
      .then(res => {
        debugger;
        res.date = new Date(res.date);
        res.startTime = new Date(res.startTime);
        res.endTime = new Date(res.endTime);
        res.deadline = new Date(res.deadline);
        this.requestForService = res;
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
    this.riServiceService()
      .retrievePregled()
      .then(res => {
        this.riServices = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.portalUserOrganizationService()
      .retrieve()
      .then(res => {
        this.portalUserOrganizations = res.data;
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
