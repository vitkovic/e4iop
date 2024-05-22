import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRiAccessMode, RiAccessMode } from '@/shared/model/ri-access-mode.model';
import RiAccessModeService from './ri-access-mode.service';

const validations: any = {
  riAccessMode: {
    mode: {},
    modeEn: {},
    modeCyr: {},
  },
};

@Component({
  validations,
})
export default class RiAccessModeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riAccessModeService') private riAccessModeService: () => RiAccessModeService;
  public riAccessMode: IRiAccessMode = new RiAccessMode();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riAccessModeId) {
        vm.retrieveRiAccessMode(to.params.riAccessModeId);
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
    if (this.riAccessMode.id) {
      this.riAccessModeService()
        .update(this.riAccessMode)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riAccessMode.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riAccessModeService()
        .create(this.riAccessMode)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riAccessMode.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiAccessMode(riAccessModeId): void {
    this.riAccessModeService()
      .find(riAccessModeId)
      .then(res => {
        this.riAccessMode = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
