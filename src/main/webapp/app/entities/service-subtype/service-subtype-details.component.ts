import { Component, Vue, Inject } from 'vue-property-decorator';

import { IServiceSubtype } from '@/shared/model/service-subtype.model';
import ServiceSubtypeService from './service-subtype.service';

@Component
export default class ServiceSubtypeDetails extends Vue {
  @Inject('serviceSubtypeService') private serviceSubtypeService: () => ServiceSubtypeService;
  public serviceSubtype: IServiceSubtype = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceSubtypeId) {
        vm.retrieveServiceSubtype(to.params.serviceSubtypeId);
      }
    });
  }

  public retrieveServiceSubtype(serviceSubtypeId) {
    this.serviceSubtypeService()
      .find(serviceSubtypeId)
      .then(res => {
        this.serviceSubtype = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
