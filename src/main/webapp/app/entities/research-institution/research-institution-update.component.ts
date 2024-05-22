import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ResearcherService from '../researcher/researcher.service';
import { IResearcher } from '@/shared/model/researcher.model';

import RiResearchOrganizationService from '../ri-research-organization/ri-research-organization.service';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IResearchInstitution, ResearchInstitution } from '@/shared/model/research-institution.model';
import ResearchInstitutionService from './research-institution.service';

const validations: any = {
  researchInstitution: {
    name: {},
  },
};

@Component({
  validations,
})
export default class ResearchInstitutionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;
  public researchInstitution: IResearchInstitution = new ResearchInstitution();

  @Inject('researcherService') private researcherService: () => ResearcherService;

  public researchers: IResearcher[] = [];

  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;

  public riResearchOrganizations: IRiResearchOrganization[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researchInstitutionId) {
        vm.retrieveResearchInstitution(to.params.researchInstitutionId);
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
    this.researchInstitution.researchers = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.researchInstitution.id) {
      this.researchInstitutionService()
        .update(this.researchInstitution)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.researchInstitution.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.researchInstitutionService()
        .create(this.researchInstitution)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.researchInstitution.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveResearchInstitution(researchInstitutionId): void {
    this.researchInstitutionService()
      .find(researchInstitutionId)
      .then(res => {
        this.researchInstitution = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.researcherService()
      .retrieve()
      .then(res => {
        this.researchers = res.data;
      });
    this.riResearchOrganizationService()
      .retrieve()
      .then(res => {
        this.riResearchOrganizations = res.data;
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
}
