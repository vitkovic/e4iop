import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IServiceSubtype, ServiceSubtype } from '@/shared/model/service-subtype.model';
import ServiceSubtypeService from './service-subtype.service';

const validations: any = {
  serviceSubtype: {
    subtype: {},
    subtypeEn: {},
    subtypeCyr: {},
  },
};

@Component({
  validations,
})
export default class ServiceSubtypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('serviceSubtypeService') private serviceSubtypeService: () => ServiceSubtypeService;
  public serviceSubtype: IServiceSubtype = new ServiceSubtype();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceSubtypeId) {
        vm.retrieveServiceSubtype(to.params.serviceSubtypeId);
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
    if (this.serviceSubtype.id) {
      this.serviceSubtypeService()
        .update(this.serviceSubtype)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceSubtype.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.serviceSubtypeService()
        .create(this.serviceSubtype)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceSubtype.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveServiceSubtype(serviceSubtypeId): void {
    this.serviceSubtypeService()
      .find(serviceSubtypeId)
      .then(res => {
        this.serviceSubtype = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
