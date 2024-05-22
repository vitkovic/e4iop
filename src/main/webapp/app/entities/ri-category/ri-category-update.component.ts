import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRiCategory, RiCategory } from '@/shared/model/ri-category.model';
import RiCategoryService from './ri-category.service';

const validations: any = {
  riCategory: {
    category: {},
    categoryEn: {},
    categoryCyr: {},
  },
};

@Component({
  validations,
})
export default class RiCategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riCategoryService') private riCategoryService: () => RiCategoryService;
  public riCategory: IRiCategory = new RiCategory();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riCategoryId) {
        vm.retrieveRiCategory(to.params.riCategoryId);
      }
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
    if (this.riCategory.id) {
      this.riCategoryService()
        .update(this.riCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riCategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riCategoryService()
        .create(this.riCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riCategory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiCategory(riCategoryId): void {
    this.riCategoryService()
      .find(riCategoryId)
      .then(res => {
        this.riCategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
