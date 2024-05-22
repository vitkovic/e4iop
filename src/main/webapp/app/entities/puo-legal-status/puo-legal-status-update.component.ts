import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IPuoLegalStatus, PuoLegalStatus } from '@/shared/model/puo-legal-status.model';
import PuoLegalStatusService from './puo-legal-status.service';

const validations: any = {
  puoLegalStatus: {
    value: {},
    description: {},
    descriptionEn: {},
    descriptionCyr: {},
  },
};

@Component({
  validations,
})
export default class PuoLegalStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('puoLegalStatusService') private puoLegalStatusService: () => PuoLegalStatusService;
  public puoLegalStatus: IPuoLegalStatus = new PuoLegalStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.puoLegalStatusId) {
        vm.retrievePuoLegalStatus(to.params.puoLegalStatusId);
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
    if (this.puoLegalStatus.id) {
      this.puoLegalStatusService()
        .update(this.puoLegalStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.puoLegalStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.puoLegalStatusService()
        .create(this.puoLegalStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.puoLegalStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePuoLegalStatus(puoLegalStatusId): void {
    this.puoLegalStatusService()
      .find(puoLegalStatusId)
      .then(res => {
        this.puoLegalStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
