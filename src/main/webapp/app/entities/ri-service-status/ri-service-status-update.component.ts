import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRiServiceStatus, RiServiceStatus } from '@/shared/model/ri-service-status.model';
import RiServiceStatusService from './ri-service-status.service';

const validations: any = {
  riServiceStatus: {
    status: {},
    description: {},
    statusEn: {},
    statusCyr: {},
    descriptionEn: {},
    descriptionCyr: {},
  },
};

@Component({
  validations,
})
export default class RiServiceStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riServiceStatusService') private riServiceStatusService: () => RiServiceStatusService;
  public riServiceStatus: IRiServiceStatus = new RiServiceStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riServiceStatusId) {
        vm.retrieveRiServiceStatus(to.params.riServiceStatusId);
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
    if (this.riServiceStatus.id) {
      this.riServiceStatusService()
        .update(this.riServiceStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riServiceStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riServiceStatusService()
        .create(this.riServiceStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riServiceStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiServiceStatus(riServiceStatusId): void {
    this.riServiceStatusService()
      .find(riServiceStatusId)
      .then(res => {
        this.riServiceStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
