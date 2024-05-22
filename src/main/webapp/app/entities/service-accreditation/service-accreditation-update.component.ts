import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IServiceAccreditation, ServiceAccreditation } from '@/shared/model/service-accreditation.model';
import ServiceAccreditationService from './service-accreditation.service';

const validations: any = {
  serviceAccreditation: {
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
export default class ServiceAccreditationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('serviceAccreditationService') private serviceAccreditationService: () => ServiceAccreditationService;
  public serviceAccreditation: IServiceAccreditation = new ServiceAccreditation();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceAccreditationId) {
        vm.retrieveServiceAccreditation(to.params.serviceAccreditationId);
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
    if (this.serviceAccreditation.id) {
      this.serviceAccreditationService()
        .update(this.serviceAccreditation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceAccreditation.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.serviceAccreditationService()
        .create(this.serviceAccreditation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceAccreditation.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveServiceAccreditation(serviceAccreditationId): void {
    this.serviceAccreditationService()
      .find(serviceAccreditationId)
      .then(res => {
        this.serviceAccreditation = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
