import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementSubsubcategoryService from '../advertisement-subsubcategory/advertisement-subsubcategory.service';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';

import AdvertisementCategoryService from '../advertisement-category/advertisement-category.service';
import { IAdvertisementCategory } from '@/shared/model/advertisement-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementSubcategory, AdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';
import AdvertisementSubcategoryService from './advertisement-subcategory.service';

const validations: any = {
  advertisementSubcategory: {
    name: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementSubcategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementSubcategoryService') private advertisementSubcategoryService: () => AdvertisementSubcategoryService;
  public advertisementSubcategory: IAdvertisementSubcategory = new AdvertisementSubcategory();

  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;

  public advertisementSubsubcategories: IAdvertisementSubsubcategory[] = [];

  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;

  public advertisementCategories: IAdvertisementCategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSubcategoryId) {
        vm.retrieveAdvertisementSubcategory(to.params.advertisementSubcategoryId);
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
    if (this.advertisementSubcategory.id) {
      this.advertisementSubcategoryService()
        .update(this.advertisementSubcategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSubcategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementSubcategoryService()
        .create(this.advertisementSubcategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSubcategory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementSubcategory(advertisementSubcategoryId): void {
    this.advertisementSubcategoryService()
      .find(advertisementSubcategoryId)
      .then(res => {
        this.advertisementSubcategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.advertisementSubsubcategoryService()
      .retrieve()
      .then(res => {
        this.advertisementSubsubcategories = res.data;
      });
    this.advertisementCategoryService()
      .retrieve()
      .then(res => {
        this.advertisementCategories = res.data;
      });
  }
}
