import { Component, Vue, Inject } from 'vue-property-decorator';

import { IServiceAvailability } from '@/shared/model/service-availability.model';
import ServiceAvailabilityService from './service-availability.service';

@Component
export default class ServiceAvailabilityDetails extends Vue {
  @Inject('serviceAvailabilityService') private serviceAvailabilityService: () => ServiceAvailabilityService;
  public serviceAvailability: IServiceAvailability = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceAvailabilityId) {
        vm.retrieveServiceAvailability(to.params.serviceAvailabilityId);
      }
    });
  }

  public retrieveServiceAvailability(serviceAvailabilityId) {
    this.serviceAvailabilityService()
      .find(serviceAvailabilityId)
      .then(res => {
        this.serviceAvailability = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
