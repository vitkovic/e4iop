import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, requiredUnless } from 'vuelidate/lib/validators';

import PortalUserOrganizationService from '../portal-user-organization/portal-user-organization.service';
import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';

import ResearchInstitutionService from '../research-institution/research-institution.service';
import { IResearchInstitution } from '@/shared/model/research-institution.model';

import ResearchInfrastructureService from '../research-infrastructure/research-infrastructure.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

import RipoActivityStatusService from '../ripo-activity-status/ripo-activity-status.service';
import { IRipoActivityStatus } from '@/shared/model/ripo-activity-status.model';

import ScientificSubdomainService from '../scientific-subdomain/scientific-subdomain.service';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IRiResearchOrganization, RiResearchOrganization } from '@/shared/model/ri-research-organization.model';
import RiResearchOrganizationService from './ri-research-organization.service';
import { NIO_MUST_BE_UNIQUE_TYPE, PUO_MUST_BE_UNIQUE_TYPE, NOT_AUTHORIZED } from '@/constants';

const validations: any = {
  riResearchOrganization: {
    name: {},
    descriptionSr: {
      maxLength: maxLength(1000),
    },
    decsriptionEn: {
      maxLength: maxLength(1000),
    },
    logo: {},
    descriptionSrCyr: {
      maxLength: maxLength(1000),
    },
    puOrganization: {
      required,
      /*
      required: requiredUnless(function () {
        return this.riResearchOrganization.researchInstitution;
      }),
      */
    },
    //researchInstitution: {
    //  required,
    /*
      required: requiredUnless(function () {
        return this.riResearchOrganization.puOrganization;
      }),
      */
    //},
  },
};

@Component({
  validations,
})
export default class RiResearchOrganizationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;
  public riResearchOrganization: IRiResearchOrganization = new RiResearchOrganization();

  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;

  public portalUserOrganizations: IPortalUserOrganization[] = [];

  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;

  public researchInstitutions: IResearchInstitution[] = [];

  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  public researchInfrastructures: IResearchInfrastructure[] = [];

  @Inject('ripoActivityStatusService') private ripoActivityStatusService: () => RipoActivityStatusService;

  public ripoActivityStatuses: IRipoActivityStatus[] = [];

  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;

  public scientificSubdomains: IScientificSubdomain[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  public dontShowPortalOrganization: boolean = false;
  public dontShowResearchInstitution: boolean = false;

  private search_data = [];
  private patternRI = false;
  public txtfield = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riResearchOrganizationId) {
        vm.retrieveRiResearchOrganization(to.params.riResearchOrganizationId);
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
    this.riResearchOrganization.subdomains = [];
  }

  public changeInstitution(event) {
    if (this.riResearchOrganization.researchInstitution != null) {
      this.dontShowPortalOrganization = true;
    } else {
      this.dontShowPortalOrganization = false;
    }
  }

  // Ova metoda se vise ne koristi nakon autocomplete opcije za PUO.
  // Pogledati novu metodu "updateFormFieldsBasedOnPUO".
  public changePuoOrganization(event): void {
    this.portalUserOrganizationService()
      .find(this.riResearchOrganization.puOrganization.id)
      .then(res => {
        this.riResearchOrganization.name = res.legalNameSr;
        this.portalUserService()
          .retrieveForOrganization(this.riResearchOrganization.puOrganization.id)
          .then(res => {
            this.portalUsers = res.data;
          });
      });
  }

  public save(): void {
    this.isSaving = true;
    if (this.riResearchOrganization.id) {
      this.riResearchOrganizationService()
        .update(this.riResearchOrganization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riResearchOrganization.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          if (error.response.status === 400 && error.response.data.type === NIO_MUST_BE_UNIQUE_TYPE) {
            const por = this.$t('riportalApp.riResearchOrganization.errors.nioMustBeUnique');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else if (error.response.status === 400 && error.response.data.type === PUO_MUST_BE_UNIQUE_TYPE) {
            const por = this.$t('riportalApp.riResearchOrganization.errors.puoMustBeUnique');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else if (error.response.status === 400 && error.response.data.type === NOT_AUTHORIZED) {
            const por = this.$t('riportalApp.riResearchOrganization.errors.notAuthorized');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else {
            const por = this.$t('riportalApp.global.errors.upisNijeUspeo');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          }
        });
    } else {
      this.riResearchOrganizationService()
        .create(this.riResearchOrganization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riResearchOrganization.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          if (error.response.status === 400 && error.response.data.type === NIO_MUST_BE_UNIQUE_TYPE) {
            const por = this.$t('riportalApp.riResearchOrganization.errors.nioMustBeUnique');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else if (error.response.status === 400 && error.response.data.type === PUO_MUST_BE_UNIQUE_TYPE) {
            const por = this.$t('riportalApp.riResearchOrganization.errors.puoMustBeUnique');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else if (error.response.status === 400 && error.response.data.type === NOT_AUTHORIZED) {
            const por = this.$t('riportalApp.riResearchOrganization.errors.notAuthorized');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          } else {
            const por = this.$t('riportalApp.global.errors.upisNijeUspeo');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          }
        });
    }
  }

  public retrieveRiResearchOrganization(riResearchOrganizationId): void {
    this.riResearchOrganizationService()
      .find(riResearchOrganizationId)
      .then(res => {
        this.riResearchOrganization = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.portalUserOrganizationService()
      .retrieveWithoutPagination()
      .then(res => {
        this.portalUserOrganizations = res.data;
        this.search_data = this.portalUserOrganizations;
      });
    this.researchInstitutionService()
      .retrieve()
      .then(res => {
        this.researchInstitutions = res.data;
      });
    this.researchInfrastructureService()
      .retrieve()
      .then(res => {
        this.researchInfrastructures = res.data;
      });
    this.ripoActivityStatusService()
      .retrieve()
      .then(res => {
        this.ripoActivityStatuses = res.data;
      });
    this.scientificSubdomainService()
      .retrieve()
      .then(res => {
        this.scientificSubdomains = res.data;
      });

    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
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

  public getDataAutoForPUO(): void {
    this.patternRI = true;
    this.txtfield = this.$refs.portalUserOrganisationField as HTMLInputElement;

    if (this.txtfield.value != '') {
      this.portalUserOrganizationService()
        .retrieveAutocomplete(this.txtfield.value, this.currentLanguage)
        .then(res => {
          this.portalUserOrganizations = res.data;
          this.search_data = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      this.portalUserOrganizationService()
        .retrieveWithoutPagination()
        .then(res => {
          this.portalUserOrganizations = res.data;
          this.search_data = res.data;
        });
    }
  }

  updatePUOInputField(puo): void {
    this.txtfield = this.$refs.portalUserOrganisationField as HTMLInputElement;

    if (this.currentLanguage == 'sr') {
      this.txtfield.value = puo.legalNameSr;
    } else if (this.currentLanguage == 'src') {
      this.txtfield.value = puo.legalNameSrCyr;
    } else if (this.currentLanguage == 'en') {
      this.txtfield.value = puo.legalNameEn;
    }

    // this.search_data = [];
  }

  public updateFormFieldsBasedOnPUO(puo) {
    this.riResearchOrganization.puOrganization = puo;
    this.riResearchOrganization.name = this.riResearchOrganization.puOrganization.legalNameSr;

    this.portalUserService()
      .retrieveForOrganization(this.riResearchOrganization.puOrganization.id)
      .then(res => {
        this.portalUsers = res.data;
      });
  }

  public toggleSearchList(event, toggleControl: string) {
    if (event.type === 'focusout') {
      this[toggleControl] = false;
    } else {
      this[toggleControl] = !this[toggleControl];
    }
  }
}
