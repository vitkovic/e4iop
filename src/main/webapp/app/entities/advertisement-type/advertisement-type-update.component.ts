import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementType, AdvertisementType } from '@/shared/model/advertisement-type.model';
import AdvertisementTypeService from './advertisement-type.service';

const validations: any = {
  advertisementType: {
    type: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
  public advertisementType: IAdvertisementType = new AdvertisementType();

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementTypeId) {
        vm.retrieveAdvertisementType(to.params.advertisementTypeId);
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
    if (this.advertisementType.id) {
      this.advertisementTypeService()
        .update(this.advertisementType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementTypeService()
        .create(this.advertisementType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementType(advertisementTypeId): void {
    this.advertisementTypeService()
      .find(advertisementTypeId)
      .then(res => {
        this.advertisementType = res;
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
  }
}
