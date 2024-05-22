import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRipoActivityStatus, RipoActivityStatus } from '@/shared/model/ripo-activity-status.model';
import RipoActivityStatusService from './ripo-activity-status.service';

const validations: any = {
  ripoActivityStatus: {
    status: {},
    statusEn: {},
    statusCyr: {},
  },
};

@Component({
  validations,
})
export default class RipoActivityStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('ripoActivityStatusService') private ripoActivityStatusService: () => RipoActivityStatusService;
  public ripoActivityStatus: IRipoActivityStatus = new RipoActivityStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ripoActivityStatusId) {
        vm.retrieveRipoActivityStatus(to.params.ripoActivityStatusId);
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
    if (this.ripoActivityStatus.id) {
      this.ripoActivityStatusService()
        .update(this.ripoActivityStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.ripoActivityStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.ripoActivityStatusService()
        .create(this.ripoActivityStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.ripoActivityStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRipoActivityStatus(ripoActivityStatusId): void {
    this.ripoActivityStatusService()
      .find(ripoActivityStatusId)
      .then(res => {
        this.ripoActivityStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
