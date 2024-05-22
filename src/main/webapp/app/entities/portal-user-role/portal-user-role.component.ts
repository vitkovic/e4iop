import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPortalUserRole } from '@/shared/model/portal-user-role.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PortalUserRoleService from './portal-user-role.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PortalUserRole extends mixins(AlertMixin) {
  @Inject('portalUserRoleService') private portalUserRoleService: () => PortalUserRoleService;
  private removeId: number = null;

  public portalUserRoles: IPortalUserRole[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPortalUserRoles();
  }

  public clear(): void {
    this.retrieveAllPortalUserRoles();
  }

  public retrieveAllPortalUserRoles(): void {
    this.isFetching = true;

    this.portalUserRoleService()
      .retrieve()
      .then(
        res => {
          this.portalUserRoles = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPortalUserRole): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePortalUserRole(): void {
    this.portalUserRoleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.portalUserRole.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPortalUserRoles();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
