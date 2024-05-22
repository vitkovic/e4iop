import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPortalUserStatus } from '@/shared/model/portal-user-status.model';
import portalUserStatusService from './portal-user-status.service';

@Component
export default class portalUserStatusDetails extends Vue {
  @Inject('portalUserStatusService') private portalUserStatusService: () => portalUserStatusService;
  public portalUserStatus: IPortalUserStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserStatusId) {
        vm.retrieveportalUserStatus(to.params.portalUserStatusId);
      }
    });
  }

  public retrieveportalUserStatus(portalUserStatusId) {
    this.portalUserStatusService()
      .find(portalUserStatusId)
      .then(res => {
        this.portalUserStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
