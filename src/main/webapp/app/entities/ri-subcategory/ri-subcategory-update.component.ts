import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ResearchInfrastructureService from '../research-infrastructure/research-infrastructure.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

import AlertService from '@/shared/alert/alert.service';
import { IRiSubcategory, RiSubcategory } from '@/shared/model/ri-subcategory.model';
import RiSubcategoryService from './ri-subcategory.service';

const validations: any = {
  riSubcategory: {
    subcategory: {},
    subcategoryEn: {},
    subCategoryCyr: {},
  },
};

@Component({
  validations,
})
export default class RiSubcategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riSubcategoryService') private riSubcategoryService: () => RiSubcategoryService;
  public riSubcategory: IRiSubcategory = new RiSubcategory();

  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  public researchInfrastructures: IResearchInfrastructure[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riSubcategoryId) {
        vm.retrieveRiSubcategory(to.params.riSubcategoryId);
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
    if (this.riSubcategory.id) {
      this.riSubcategoryService()
        .update(this.riSubcategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riSubcategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riSubcategoryService()
        .create(this.riSubcategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riSubcategory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiSubcategory(riSubcategoryId): void {
    this.riSubcategoryService()
      .find(riSubcategoryId)
      .then(res => {
        this.riSubcategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.researchInfrastructureService()
      .retrieve()
      .then(res => {
        this.researchInfrastructures = res.data;
      });
  }
}
