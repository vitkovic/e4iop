import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementSubcategoryService from '../advertisement-subcategory/advertisement-subcategory.service';
import { IAdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';

import CmsAdvertisementCategoryService from '../cms-advertisement-category/cms-advertisement-category.service';
import { ICmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementCategory, AdvertisementCategory } from '@/shared/model/advertisement-category.model';
import AdvertisementCategoryService from './advertisement-category.service';

const validations: any = {
  advertisementCategory: {
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
export default class AdvertisementCategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;
  public advertisementCategory: IAdvertisementCategory = new AdvertisementCategory();

  @Inject('advertisementSubcategoryService') private advertisementSubcategoryService: () => AdvertisementSubcategoryService;

  public advertisementSubcategories: IAdvertisementSubcategory[] = [];

  @Inject('cmsAdvertisementCategoryService') private cmsAdvertisementCategoryService: () => CmsAdvertisementCategoryService;

  public cmsAdvertisementCategories: ICmsAdvertisementCategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementCategoryId) {
        vm.retrieveAdvertisementCategory(to.params.advertisementCategoryId);
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
    if (this.advertisementCategory.id) {
      this.advertisementCategoryService()
        .update(this.advertisementCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementCategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementCategoryService()
        .create(this.advertisementCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementCategory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementCategory(advertisementCategoryId): void {
    this.advertisementCategoryService()
      .find(advertisementCategoryId)
      .then(res => {
        this.advertisementCategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.advertisementSubcategoryService()
      .retrieve()
      .then(res => {
        this.advertisementSubcategories = res.data;
      });
    this.cmsAdvertisementCategoryService()
      .retrieve()
      .then(res => {
        this.cmsAdvertisementCategories = res.data;
      });
  }
}
