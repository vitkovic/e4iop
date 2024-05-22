import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AdvertisementSubcategoryService from '../advertisement-subcategory/advertisement-subcategory.service';
import { IAdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementSubsubcategory, AdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
import AdvertisementSubsubcategoryService from './advertisement-subsubcategory.service';

const validations: any = {
  advertisementSubsubcategory: {
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
export default class AdvertisementSubsubcategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;
  public advertisementSubsubcategory: IAdvertisementSubsubcategory = new AdvertisementSubsubcategory();

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];

  @Inject('advertisementSubcategoryService') private advertisementSubcategoryService: () => AdvertisementSubcategoryService;

  public advertisementSubcategories: IAdvertisementSubcategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSubsubcategoryId) {
        vm.retrieveAdvertisementSubsubcategory(to.params.advertisementSubsubcategoryId);
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
    if (this.advertisementSubsubcategory.id) {
      this.advertisementSubsubcategoryService()
        .update(this.advertisementSubsubcategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSubsubcategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementSubsubcategoryService()
        .create(this.advertisementSubsubcategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSubsubcategory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementSubsubcategory(advertisementSubsubcategoryId): void {
    this.advertisementSubsubcategoryService()
      .find(advertisementSubsubcategoryId)
      .then(res => {
        this.advertisementSubsubcategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
    this.advertisementSubcategoryService()
      .retrieve()
      .then(res => {
        this.advertisementSubcategories = res.data;
      });
  }
}
