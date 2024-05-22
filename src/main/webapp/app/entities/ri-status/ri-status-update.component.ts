import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRiStatus, RiStatus } from '@/shared/model/ri-status.model';
import RiStatusService from './ri-status.service';

const validations: any = {
  riStatus: {
    status: {},
    meaning: {},
    statusEn: {},
    statusCyr: {},
    meaningEn: {},
    meaningCyr: {},
  },
};

@Component({
  validations,
})
export default class RiStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riStatusService') private riStatusService: () => RiStatusService;
  public riStatus: IRiStatus = new RiStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riStatusId) {
        vm.retrieveRiStatus(to.params.riStatusId);
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
    if (this.riStatus.id) {
      this.riStatusService()
        .update(this.riStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riStatusService()
        .create(this.riStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiStatus(riStatusId): void {
    this.riStatusService()
      .find(riStatusId)
      .then(res => {
        this.riStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
