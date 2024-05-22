import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPortalUserRole } from '@/shared/model/portal-user-role.model';
import PortalUserRoleService from './portal-user-role.service';

@Component
export default class PortalUserRoleDetails extends Vue {
  @Inject('portalUserRoleService') private portalUserRoleService: () => PortalUserRoleService;
  public portalUserRole: IPortalUserRole = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserRoleId) {
        vm.retrievePortalUserRole(to.params.portalUserRoleId);
      }
    });
  }

  public retrievePortalUserRole(portalUserRoleId) {
    this.portalUserRoleService()
      .find(portalUserRoleId)
      .then(res => {
        this.portalUserRole = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
