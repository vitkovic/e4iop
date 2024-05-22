import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, requiredIf } from 'vuelidate/lib/validators';

import RequestForServiceService from '../request-for-service/request-for-service.service';
import { IRequestForService } from '@/shared/model/request-for-service.model';

import RiCalendarService from '../ri-calendar/ri-calendar.service';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';

import ServiceAvailabilityService from '../service-availability/service-availability.service';
import { IServiceAvailability } from '@/shared/model/service-availability.model';

import ServiceAccreditationService from '../service-accreditation/service-accreditation.service';
import { IServiceAccreditation } from '@/shared/model/service-accreditation.model';

import ServiceSubtypeService from '../service-subtype/service-subtype.service';
import { IServiceSubtype } from '@/shared/model/service-subtype.model';

import ScientificSubdomainService from '../scientific-subdomain/scientific-subdomain.service';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';

import ResearchInfrastructureService from '../research-infrastructure/research-infrastructure.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IRiService, RiService } from '@/shared/model/ri-service.model';
import RiServiceService from './ri-service.service';

import AccountService from '@/account/account.service';

import axios from 'axios';

import UploadFilesService from '@/utility/upload/upload_service';

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons';

//axios.defaults.baseURL = 'http://localhost:8080/'; // for dev only i autocomplete

const validations: any = {
  riService: {
    nameSr: {},
    nameEn: {},
    descriptionSr: {},
    descriptionEn: {},
    keywords: {},
    keywordsCyr: {},
    keywordsEn: {},
    accreditationOrganizationName: {},
    termsOfUseSr: {},
    termsOfUseEn: {},
    privacyPolicySr: {},
    privacyPolicyEn: {},
    accessPolicySr: {},
    accessPolicyEn: {},
    pricingPolicySr: {},
    pricingPolicyEn: {},
    nameSrCyr: {},
    termsOfUseSrCyr: {},
    accessPolicySrCyr: {},
    pricingPolicySrCyr: {},
    descriptionSrCyr: {},
    researchInfrastructure: {
      required,
    },
    price: {},
    currency: {},
    owner: {
      required: requiredIf(function () {
        return this.hasAnyRole(['PA']) || this.hasAnyAuthority('ROLE_ADMIN');
      }),
    },
  },
};

library.add(faQuestionCircle);

@Component({
  components: {
    FontAwesomeIcon,
  },
  validations,
})
export default class RiServiceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riServiceService') private riServiceService: () => RiServiceService;
  public riService: IRiService = new RiService();

  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;

  public requestForServices: IRequestForService[] = [];

  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;

  public riCalendars: IRiCalendar[] = [];

  @Inject('serviceAvailabilityService') private serviceAvailabilityService: () => ServiceAvailabilityService;

  public serviceAvailabilities: IServiceAvailability[] = [];

  @Inject('serviceAccreditationService') private serviceAccreditationService: () => ServiceAccreditationService;

  public serviceAccreditations: IServiceAccreditation[] = [];

  @Inject('serviceSubtypeService') private serviceSubtypeService: () => ServiceSubtypeService;

  public serviceSubtypes: IServiceSubtype[] = [];

  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;

  public scientificSubdomains: IScientificSubdomain[] = [];

  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  @Inject('accountService') private accountService: () => AccountService;

  public researchInfrastructures: IResearchInfrastructure[] = [];
  public isSaving = false;
  public currentLanguage = '';
  private keystrokesNUMSr = 0;
  private keystrokesNUMEn = 0;
  private search_data = [];
  private search_dataEn = [];
  private query = [];
  private linked0 = null;
  private linked1 = null;
  private linked2 = null;
  private linked3 = null;
  private linked4 = null;
  private linked5 = null;
  private linked6 = null;
  private linked7 = null;
  private riportalEntityShortName = 're';
  private currentTooltip: string | null = null;

  formLink(link): void {
    UploadFilesService.getFile(link);
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riServiceId) {
        vm.retrieveRiService(to.params.riServiceId);
      }
      vm.initRelationships();
    });
  }
  getData(): void {
    if (this.keystrokesNUMSr > 2 || this.riService.nameSr.length > 2) {
      var urlstr = '/api/ri-services-autocomplete/' + this.riService.nameSr;
      axios.get(urlstr).then(response => {
        if (Array.isArray(response.data)) {
          this.search_data = response.data;
        }
      });
    } else {
      this.keystrokesNUMSr++;
    }
  }
  getDataEn(): void {
    if (this.keystrokesNUMEn > 2 || this.riService.nameEn.length > 2) {
      var urlstr = '/api/ri-services-autocomplete/en/' + this.riService.nameEn;
      axios.get(urlstr).then(response => {
        if (Array.isArray(response.data)) {
          this.search_dataEn = response.data;
        }
      });
    } else {
      this.keystrokesNUMEn++;
    }
  }
  getNameSr(data, txt): void {
    this.query = data.nameSr;
    this.riService[txt] = data.nameSr;
    this.riService = data;
    this.search_data = [];
    this.search_dataEn = [];
    this.keystrokesNUMSr = 0;
  }

  getNameEn(data, txt): void {
    this.query = data.nameEn;
    this.riService[txt] = data.nameEn;
    this.riService = data;
    this.search_data = [];
    this.search_dataEn = [];
    this.keystrokesNUMEn = 0;
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.riService.subdomains = [];
  }

  public populateCyrOrLatValues(): void {
    if (this.currentLanguage === 'sr' || this.currentLanguage === 'en') {
      this.riService.nameSrCyr = this.riService.nameSr;
      this.riService.descriptionSrCyr = this.riService.descriptionSr;
      this.riService.keywordsCyr = this.riService.keywords;
    } else {
      this.riService.nameSr = this.riService.nameSrCyr;
      this.riService.descriptionSr = this.riService.descriptionSrCyr;
      this.riService.keywords = this.riService.keywordsCyr;
    }
  }

  public save(): void {
    this.isSaving = true;
    this.populateCyrOrLatValues();
    if (this.riService.id) {
      this.riServiceService()
        .update(this.riService)
        .then(param => {
          this.isSaving = false;
          this.setUploads();
          this.$notify({
            text: JSON.stringify(this.$t('riportalApp.riService.updated', { param: param.id })).replace(/["]/g, ''),
            type: 'info',
            duration: 3000,
          });
          //  const message = this.$t('riportalApp.riService.updated', { param: param.id });
          //  this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riServiceService()
        .create(this.riService)
        .then(param => {
          this.isSaving = false;
          this.riService.id = param.id;
          this.setUploads();
          this.$notify({
            text: JSON.stringify(this.$t('riportalApp.riService.created', { param: param.id })).replace(/["]/g, ''),
            type: 'info',
            duration: 3000,
          });
          // const message = this.$t('riportalApp.riService.created', { param: param.id });
          // this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiService(riServiceId): void {
    this.riServiceService()
      .find(riServiceId)
      .then(res => {
        this.riService = res;
        this.setUploads();
      });
  }

  public setUploads(): void {
    console.log(this.riService + 'linked0');
    if (this.riService.termsOfUseSr) this.linked0 = 1;
    if (this.riService.termsOfUseEn) this.linked1 = 1;
    if (this.riService.privacyPolicySr) this.linked2 = 1;
    if (this.riService.privacyPolicyEn) this.linked3 = 1;
    if (this.riService.accessPolicySr) this.linked4 = 1;
    if (this.riService.accessPolicyEn) this.linked5 = 1;
    if (this.riService.pricingPolicySr) this.linked6 = 1;
    if (this.riService.pricingPolicyEn) this.linked7 = 1;
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
    this.riCalendarService()
      .retrieve()
      .then(res => {
        this.riCalendars = res.data;
      });
    this.serviceAvailabilityService()
      .retrieve()
      .then(res => {
        this.serviceAvailabilities = res.data;
      });
    this.serviceAccreditationService()
      .retrieve()
      .then(res => {
        this.serviceAccreditations = res.data;
      });
    this.serviceSubtypeService()
      .retrieve()
      .then(res => {
        this.serviceSubtypes = res.data;
      });
    this.scientificSubdomainService()
      .retrieve()
      .then(res => {
        this.scientificSubdomains = res.data;
      });
    this.researchInfrastructureService()
      .retrieve()
      .then(res => {
        this.researchInfrastructures = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  public showTooltip(input: string): void {
    this.currentTooltip = input;
  }

  public hideTooltip(input: string): void {
    this.currentTooltip = null;
  }
}
