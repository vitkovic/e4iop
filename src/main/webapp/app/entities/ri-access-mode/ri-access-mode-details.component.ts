import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiAccessMode } from '@/shared/model/ri-access-mode.model';
import RiAccessModeService from './ri-access-mode.service';

@Component
export default class RiAccessModeDetails extends Vue {
  @Inject('riAccessModeService') private riAccessModeService: () => RiAccessModeService;
  public riAccessMode: IRiAccessMode = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riAccessModeId) {
        vm.retrieveRiAccessMode(to.params.riAccessModeId);
      }
    });
  }

  public retrieveRiAccessMode(riAccessModeId) {
    this.riAccessModeService()
      .find(riAccessModeId)
      .then(res => {
        this.riAccessMode = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
