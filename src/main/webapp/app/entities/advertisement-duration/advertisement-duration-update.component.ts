import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementDuration, AdvertisementDuration } from '@/shared/model/advertisement-duration.model';
import AdvertisementDurationService from './advertisement-duration.service';

const validations: any = {
  advertisementDuration: {
    duration: {
      required,
      numeric,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementDurationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementDurationService') private advertisementDurationService: () => AdvertisementDurationService;
  public advertisementDuration: IAdvertisementDuration = new AdvertisementDuration();

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementDurationId) {
        vm.retrieveAdvertisementDuration(to.params.advertisementDurationId);
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
    if (this.advertisementDuration.id) {
      this.advertisementDurationService()
        .update(this.advertisementDuration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementDuration.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementDurationService()
        .create(this.advertisementDuration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementDuration.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementDuration(advertisementDurationId): void {
    this.advertisementDurationService()
      .find(advertisementDurationId)
      .then(res => {
        this.advertisementDuration = res;
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
