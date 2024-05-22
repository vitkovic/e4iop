import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import ResearchInstitutionService from '../research-institution/research-institution.service';
import { IResearchInstitution } from '@/shared/model/research-institution.model';

import AlertService from '@/shared/alert/alert.service';
import { IResearcher, Researcher } from '@/shared/model/researcher.model';
import ResearcherService from './researcher.service';

const validations: any = {
  researcher: {
    firstName: {},
    lastName: {},
    middleName: {},
    birthDate: {},
  },
};

@Component({
  validations,
})
export default class ResearcherUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('researcherService') private researcherService: () => ResearcherService;
  public researcher: IResearcher = new Researcher();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;

  public researchInstitutions: IResearchInstitution[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researcherId) {
        vm.retrieveResearcher(to.params.researcherId);
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
    if (this.researcher.id) {
      this.researcherService()
        .update(this.researcher)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.researcher.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.researcherService()
        .create(this.researcher)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.researcher.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveResearcher(researcherId): void {
    this.researcherService()
      .find(researcherId)
      .then(res => {
        this.researcher = res;
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
    this.researchInstitutionService()
      .retrieve()
      .then(res => {
        this.researchInstitutions = res.data;
      });
  }
}
