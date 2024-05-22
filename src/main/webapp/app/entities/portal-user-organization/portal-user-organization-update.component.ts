import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import RequestForServiceService from '../request-for-service/request-for-service.service';
import { IRequestForService } from '@/shared/model/request-for-service.model';

import PuoLegalStatusService from '../puo-legal-status/puo-legal-status.service';
import { IPuoLegalStatus } from '@/shared/model/puo-legal-status.model';

import RiResearchOrganizationService from '../ri-research-organization/ri-research-organization.service';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IPortalUserOrganization, PortalUserOrganization } from '@/shared/model/portal-user-organization.model';
import PortalUserOrganizationService from './portal-user-organization.service';
import { REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE, PIB_ALREADY_EXISTS_TYPE } from '@/constants';

import ResearchInstitutionService from '../research-institution/research-institution.service';
import { IResearchInstitution } from '@/shared/model/research-institution.model';

import axios from 'axios';

const validations: any = {
  portalUserOrganization: {
    legalNameSr: {},
    legalNameEn: {},
    shortName: {},
    webAddress: {},
    pib: {
      minLength: minLength(9),
      maxLength: maxLength(9),
      required,
    },
    registrationNumber: {
      maxLength: maxLength(8),
      minLength: minLength(8),
      required,
    },
    keywords: {},
    address: {},
    postalCode: {},
    city: {},
    country: {},
    legalNameSrCyr: {},
  },
};

@Component({
  validations,
})
export default class PortalUserOrganizationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  public portalUserOrganization: IPortalUserOrganization = new PortalUserOrganization();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;

  public requestForServices: IRequestForService[] = [];

  @Inject('puoLegalStatusService') private puoLegalStatusService: () => PuoLegalStatusService;

  public puoLegalStatuses: IPuoLegalStatus[] = [];

  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;

  public riResearchOrganizations: IRiResearchOrganization[] = [];

  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;

  public researchInstitutions: IResearchInstitution[] = [];

  public isSaving = false;
  public currentLanguage = '';

  public tempPUO = null;

  private patternRI = false;
  public txtfield = null;
  private search_data = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserOrganizationId) {
        vm.retrievePortalUserOrganization(to.params.portalUserOrganizationId);
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

  // Method for returning organization from apr api by registration number
  public getDataMatAPI(): void {
    // console.log('usao');

    // if organisation is not found in db look on web db - APR data
    var urlstr = '';
    if (this.portalUserOrganization.registrationNumber.length == 8) {
      urlstr = '/api/portal-user-organizations-from-api/' + this.portalUserOrganization.registrationNumber;
      this.$notify({ text: JSON.stringify(this.$t('register.loadingdata')) }); // as ajax loader / simulatiion
      axios
        .get(urlstr)
        .then(response => {
          this.tempPUO = response.data;
          //this.keystrokesNUM  = 0;
          if (this.tempPUO != null && this.tempPUO.legalNameSr != null) {
            this.$notify({ clean: true });
            this.$notify({
              text: JSON.stringify(
                this.$t('register.organizationfound', { param: this.portalUserOrganization.registrationNumber })
              ).replace(/["]/g, ''), // stop loader and show user that organization was found
              type: 'success',
              duration: 2000,
            });
            //this.portalUserOrganization = this.tempPUO; // this is only set for existing organizations not new.
            this.portalUserOrganization = this.tempPUO;
          } else {
            this.$notify({ clean: true });
            this.$notify({
              text: JSON.stringify(
                this.$t('register.organizationNOTfound', { param: this.portalUserOrganization.registrationNumber })
              ).replace(/["]/g, ''),
              type: 'error',
              duration: 2000,
            });
          }
        })
        .catch(error => {
          this.tempPUO = null;
          this.portalUserOrganization = null;
          //this.keystrokesNUM  = 0;
        });
    }
  }
  // Method for returning organization from apr api by PIB
  public getDataPibAPI(): void {
    //console.log('usao');

    // if organisation is not found in db look on web db - APR data
    var urlstr = '';
    if (this.portalUserOrganization.pib.length == 9) {
      urlstr = '/api/portal-user-organizations-from-api/' + this.portalUserOrganization.pib;
      this.$notify({ text: JSON.stringify(this.$t('register.loadingdata')) }); // as ajax loader / simulatiion
      axios
        .get(urlstr)
        .then(response => {
          this.tempPUO = response.data;
          //this.keystrokesNUM  = 0;
          if (this.tempPUO != null && this.tempPUO.legalNameSr != null) {
            this.$notify({ clean: true });
            this.$notify({
              text: JSON.stringify(this.$t('register.organizationfound', { param: this.portalUserOrganization.pib })).replace(/["]/g, ''), // stop loader and show user that organization was found
              type: 'success',
              duration: 2000,
            });
            //this.portalUserOrganization = this.tempPUO; // this is only set for existing organizations not new.
            this.portalUserOrganization = this.tempPUO;
          } else {
            this.$notify({ clean: true });
            this.$notify({
              text: JSON.stringify(this.$t('register.organizationNOTfound', { param: this.portalUserOrganization.pib })).replace(
                /["]/g,
                ''
              ),
              type: 'error',
              duration: 2000,
            });
          }
        })
        .catch(error => {
          this.tempPUO = null;
          this.portalUserOrganization = null;
          //this.keystrokesNUM  = 0;
        });
    }
  }

  // Ova metoda se vise ne koristi nakon autocomplete opcije za PUO.
  // Pogledati novu metodu "updateFormFieldsBasedOnNIO".
  public changeInstitution(event): void {
    this.researchInstitutionService()
      .find(this.portalUserOrganization.nioInstitution.id)
      .then(res => {
        this.portalUserOrganization.legalNameSr = res.name;
        this.portalUserOrganization.address = res.address;
        this.portalUserOrganization.legalNameEn = res.nameEn;
        this.portalUserOrganization.registrationNumber = res.registerNumber;
        this.portalUserOrganization.pib = res.vatNumber;
        this.portalUserOrganization.webAddress = res.webAdress;
      });
  }

  public save(): void {
    this.isSaving = true;
    if (this.portalUserOrganization.id) {
      this.portalUserOrganizationService()
        .update(this.portalUserOrganization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUserOrganization.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.portalUserOrganizationService()
        .create(this.portalUserOrganization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUserOrganization.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          if (error.response.status === 400 && error.response.data.type === REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE) {
            const por = this.$t('riportalApp.portalUserOrganization.errors.registrationNumberAlreadyExists');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else if (error.response.status === 400 && error.response.data.type === PIB_ALREADY_EXISTS_TYPE) {
            const por = this.$t('riportalApp.portalUserOrganization.errors.pibAlreadyExists');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else {
            const por = this.$t('error.upisNijeUspeo');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          }
        });
    }
  }

  public retrievePortalUserOrganization(portalUserOrganizationId): void {
    this.portalUserOrganizationService()
      .find(portalUserOrganizationId)
      .then(res => {
        this.portalUserOrganization = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.requestForServiceService()
      .retrieve()
      .then(res => {
        this.requestForServices = res.data;
      });
    this.puoLegalStatusService()
      .retrieve()
      .then(res => {
        this.puoLegalStatuses = res.data;
      });
    this.researchInstitutionService()
      .retrieveOrdered(this.currentLanguage)
      .then(res => {
        this.researchInstitutions = res.data;
        this.search_data = this.researchInstitutions;
      });
  }

  public getDataAutoForNIO(): void {
    this.patternRI = true;
    this.txtfield = this.$refs.researchInstitutionField as HTMLInputElement;

    if (this.txtfield.value != '') {
      this.researchInstitutionService()
        .retrieveAutocomplete(this.txtfield.value)
        .then(res => {
          this.researchInstitutions = res.data;
          this.search_data = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      this.researchInstitutionService()
        .retrieveOrdered(this.currentLanguage)
        .then(res => {
          this.researchInstitutions = res.data;
          this.search_data = res.data;
        });
    }
  }

  updateNIOInputField(nio): void {
    this.txtfield = this.$refs.researchInstitutionField as HTMLInputElement;

    if (this.currentLanguage == 'sr') {
      this.txtfield.value = nio.name;
    } else if (this.currentLanguage == 'src') {
      this.txtfield.value = nio.nameCyr;
    } else if (this.currentLanguage == 'en') {
      this.txtfield.value = nio.nameEn;
    }

    // this.search_data = [];
  }

  public updateFormFieldsBasedOnNIO(nio) {
    this.portalUserOrganization.legalNameSr = nio.name;
    this.portalUserOrganization.address = nio.address;
    this.portalUserOrganization.legalNameEn = nio.nameEn;
    this.portalUserOrganization.registrationNumber = nio.registerNumber;
    this.portalUserOrganization.pib = nio.vatNumber;
    this.portalUserOrganization.webAddress = nio.webAdress;
  }

  public toggleSearchList(event, toggleControl: string) {
    if (event.type === 'focusout') {
      this[toggleControl] = false;
    } else {
      this[toggleControl] = !this[toggleControl];
    }
  }
}
