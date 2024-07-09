import { Component, Vue, Inject } from 'vue-property-decorator';
import { required } from 'vuelidate/lib/validators';
import AdvertisementSupporterStatusService from './advertisement-supporter-status.service';
import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementSupporterStatus, AdvertisementSupporterStatus } from '@/shared/model/advertisement-supporter-status.model';

const validations: any = {
  advertisementSupporterStatus: {
    nameSr: {
      required,
    },
    nameSrc: {
      required,
    },
    nameEn: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementSupporterStatusUpdate extends Vue {
  @Inject('alertService') private alertService!: () => AlertService;
  @Inject('advertisementSupporterStatusService') private advertisementSupporterStatusService!: () => AdvertisementSupporterStatusService;
  public advertisementSupporterStatus: IAdvertisementSupporterStatus = new AdvertisementSupporterStatus();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSupporterStatusId) {
        vm.retrieveAdvertisementSupporterStatus(to.params.advertisementSupporterStatusId);
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
    if (this.advertisementSupporterStatus.id) {
      this.advertisementSupporterStatusService()
        .update(this.advertisementSupporterStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSupporterStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementSupporterStatusService()
        .create(this.advertisementSupporterStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSupporterStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementSupporterStatus(advertisementSupporterStatusId: number): void {
    this.advertisementSupporterStatusService()
      .find(advertisementSupporterStatusId)
      .then(res => {
        this.advertisementSupporterStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
