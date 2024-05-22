import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRiAccessType, RiAccessType } from '@/shared/model/ri-access-type.model';
import RiAccessTypeService from './ri-access-type.service';

const validations: any = {
  riAccessType: {
    type: {},
    typeEn: {},
    typeCyr: {},
  },
};

@Component({
  validations,
})
export default class RiAccessTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riAccessTypeService') private riAccessTypeService: () => RiAccessTypeService;
  public riAccessType: IRiAccessType = new RiAccessType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riAccessTypeId) {
        vm.retrieveRiAccessType(to.params.riAccessTypeId);
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
    if (this.riAccessType.id) {
      this.riAccessTypeService()
        .update(this.riAccessType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riAccessType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riAccessTypeService()
        .create(this.riAccessType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riAccessType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiAccessType(riAccessTypeId): void {
    this.riAccessTypeService()
      .find(riAccessTypeId)
      .then(res => {
        this.riAccessType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
