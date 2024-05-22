import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPuoLegalStatus } from '@/shared/model/puo-legal-status.model';
import PuoLegalStatusService from './puo-legal-status.service';

@Component
export default class PuoLegalStatusDetails extends Vue {
  @Inject('puoLegalStatusService') private puoLegalStatusService: () => PuoLegalStatusService;
  public puoLegalStatus: IPuoLegalStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.puoLegalStatusId) {
        vm.retrievePuoLegalStatus(to.params.puoLegalStatusId);
      }
    });
  }

  public retrievePuoLegalStatus(puoLegalStatusId) {
    this.puoLegalStatusService()
      .find(puoLegalStatusId)
      .then(res => {
        this.puoLegalStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
