import { Component, Vue, Inject } from 'vue-property-decorator';

import { IServiceType } from '@/shared/model/service-type.model';
import ServiceTypeService from './service-type.service';

@Component
export default class ServiceTypeDetails extends Vue {
  @Inject('serviceTypeService') private serviceTypeService: () => ServiceTypeService;
  public serviceType: IServiceType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceTypeId) {
        vm.retrieveServiceType(to.params.serviceTypeId);
      }
    });
  }

  public retrieveServiceType(serviceTypeId) {
    this.serviceTypeService()
      .find(serviceTypeId)
      .then(res => {
        this.serviceType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
