import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ScientificDomainService from '../scientific-domain/scientific-domain.service';
import { IScientificDomain } from '@/shared/model/scientific-domain.model';

import RiResearchOrganizationService from '../ri-research-organization/ri-research-organization.service';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';

import ResearchInfrastructureService from '../research-infrastructure/research-infrastructure.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

import RiServiceService from '../ri-service/ri-service.service';
import { IRiService } from '@/shared/model/ri-service.model';

import AlertService from '@/shared/alert/alert.service';
import { IScientificSubdomain, ScientificSubdomain } from '@/shared/model/scientific-subdomain.model';
import ScientificSubdomainService from './scientific-subdomain.service';

const validations: any = {
  scientificSubdomain: {
    subdomain: {},
    subdomainEn: {},
    subdomainCyr: {},
  },
};

@Component({
  validations,
})
export default class ScientificSubdomainUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;
  public scientificSubdomain: IScientificSubdomain = new ScientificSubdomain();

  @Inject('scientificDomainService') private scientificDomainService: () => ScientificDomainService;

  public scientificDomains: IScientificDomain[] = [];

  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;

  public riResearchOrganizations: IRiResearchOrganization[] = [];

  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  public researchInfrastructures: IResearchInfrastructure[] = [];

  @Inject('riServiceService') private riServiceService: () => RiServiceService;

  public riServices: IRiService[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.scientificSubdomainId) {
        vm.retrieveScientificSubdomain(to.params.scientificSubdomainId);
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
    if (this.scientificSubdomain.id) {
      this.scientificSubdomainService()
        .update(this.scientificSubdomain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.scientificSubdomain.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.scientificSubdomainService()
        .create(this.scientificSubdomain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.scientificSubdomain.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveScientificSubdomain(scientificSubdomainId): void {
    this.scientificSubdomainService()
      .find(scientificSubdomainId)
      .then(res => {
        this.scientificSubdomain = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.scientificDomainService()
      .retrieve()
      .then(res => {
        this.scientificDomains = res.data;
      });
    this.riResearchOrganizationService()
      .retrieve()
      .then(res => {
        this.riResearchOrganizations = res.data;
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
