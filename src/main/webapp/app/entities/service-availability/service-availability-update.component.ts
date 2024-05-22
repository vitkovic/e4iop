import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IServiceAvailability, ServiceAvailability } from '@/shared/model/service-availability.model';
import ServiceAvailabilityService from './service-availability.service';

const validations: any = {
  serviceAvailability: {
    availability: {},
    meaning: {},
    availabilityEn: {},
    availabilityCyr: {},
    meaningEn: {},
    meaningCyr: {},
  },
};

@Component({
  validations,
})
export default class ServiceAvailabilityUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('serviceAvailabilityService') private serviceAvailabilityService: () => ServiceAvailabilityService;
  public serviceAvailability: IServiceAvailability = new ServiceAvailability();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceAvailabilityId) {
        vm.retrieveServiceAvailability(to.params.serviceAvailabilityId);
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
    if (this.serviceAvailability.id) {
      this.serviceAvailabilityService()
        .update(this.serviceAvailability)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceAvailability.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.serviceAvailabilityService()
        .create(this.serviceAvailability)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceAvailability.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveServiceAvailability(serviceAvailabilityId): void {
    this.serviceAvailabilityService()
      .find(serviceAvailabilityId)
      .then(res => {
        this.serviceAvailability = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
