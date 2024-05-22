import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementStatus, AdvertisementStatus } from '@/shared/model/advertisement-status.model';
import AdvertisementStatusService from './advertisement-status.service';

const validations: any = {
  advertisementStatus: {
    status: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementStatusService') private advertisementStatusService: () => AdvertisementStatusService;
  public advertisementStatus: IAdvertisementStatus = new AdvertisementStatus();

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementStatusId) {
        vm.retrieveAdvertisementStatus(to.params.advertisementStatusId);
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
    if (this.advertisementStatus.id) {
      this.advertisementStatusService()
        .update(this.advertisementStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementStatusService()
        .create(this.advertisementStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementStatus(advertisementStatusId): void {
    this.advertisementStatusService()
      .find(advertisementStatusId)
      .then(res => {
        this.advertisementStatus = res;
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
