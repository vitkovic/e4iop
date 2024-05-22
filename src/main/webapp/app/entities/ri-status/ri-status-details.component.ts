import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiStatus } from '@/shared/model/ri-status.model';
import RiStatusService from './ri-status.service';

@Component
export default class RiStatusDetails extends Vue {
  @Inject('riStatusService') private riStatusService: () => RiStatusService;
  public riStatus: IRiStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riStatusId) {
        vm.retrieveRiStatus(to.params.riStatusId);
      }
    });
  }

  public retrieveRiStatus(riStatusId) {
    this.riStatusService()
      .find(riStatusId)
      .then(res => {
        this.riStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
