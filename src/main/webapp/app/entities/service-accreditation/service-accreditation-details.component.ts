import { Component, Vue, Inject } from 'vue-property-decorator';

import { IServiceAccreditation } from '@/shared/model/service-accreditation.model';
import ServiceAccreditationService from './service-accreditation.service';

@Component
export default class ServiceAccreditationDetails extends Vue {
  @Inject('serviceAccreditationService') private serviceAccreditationService: () => ServiceAccreditationService;
  public serviceAccreditation: IServiceAccreditation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceAccreditationId) {
        vm.retrieveServiceAccreditation(to.params.serviceAccreditationId);
      }
    });
  }

  public retrieveServiceAccreditation(serviceAccreditationId) {
    this.serviceAccreditationService()
      .find(serviceAccreditationId)
      .then(res => {
        this.serviceAccreditation = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
