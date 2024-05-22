import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRipoActivityStatus } from '@/shared/model/ripo-activity-status.model';
import RipoActivityStatusService from './ripo-activity-status.service';

@Component
export default class RipoActivityStatusDetails extends Vue {
  @Inject('ripoActivityStatusService') private ripoActivityStatusService: () => RipoActivityStatusService;
  public ripoActivityStatus: IRipoActivityStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ripoActivityStatusId) {
        vm.retrieveRipoActivityStatus(to.params.ripoActivityStatusId);
      }
    });
  }

  public retrieveRipoActivityStatus(ripoActivityStatusId) {
    this.ripoActivityStatusService()
      .find(ripoActivityStatusId)
      .then(res => {
        this.ripoActivityStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
