import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IServiceType, ServiceType } from '@/shared/model/service-type.model';
import ServiceTypeService from './service-type.service';

const validations: any = {
  serviceType: {
    type: {},
    typeEn: {},
    typeCyr: {},
  },
};

@Component({
  validations,
})
export default class ServiceTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('serviceTypeService') private serviceTypeService: () => ServiceTypeService;
  public serviceType: IServiceType = new ServiceType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceTypeId) {
        vm.retrieveServiceType(to.params.serviceTypeId);
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
    if (this.serviceType.id) {
      this.serviceTypeService()
        .update(this.serviceType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.serviceTypeService()
        .create(this.serviceType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveServiceType(serviceTypeId): void {
    this.serviceTypeService()
      .find(serviceTypeId)
      .then(res => {
        this.serviceType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
