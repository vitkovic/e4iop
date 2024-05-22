import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiServiceStatus } from '@/shared/model/ri-service-status.model';
import RiServiceStatusService from './ri-service-status.service';

@Component
export default class RiServiceStatusDetails extends Vue {
  @Inject('riServiceStatusService') private riServiceStatusService: () => RiServiceStatusService;
  public riServiceStatus: IRiServiceStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riServiceStatusId) {
        vm.retrieveRiServiceStatus(to.params.riServiceStatusId);
      }
    });
  }

  public retrieveRiServiceStatus(riServiceStatusId) {
    this.riServiceStatusService()
      .find(riServiceStatusId)
      .then(res => {
        this.riServiceStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
