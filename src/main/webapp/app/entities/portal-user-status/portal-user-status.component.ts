import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPortalUserStatus } from '@/shared/model/portal-user-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import portalUserStatusService from './portal-user-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class portalUserStatus extends mixins(AlertMixin) {
  @Inject('portalUserStatusService') private portalUserStatusService: () => portalUserStatusService;
  private removeId: number = null;

  public portalUserStatuss: IPortalUserStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllportalUserStatuss();
  }

  public clear(): void {
    this.retrieveAllportalUserStatuss();
  }

  public retrieveAllportalUserStatuss(): void {
    this.isFetching = true;

    this.portalUserStatusService()
      .retrieve()
      .then(
        res => {
          this.portalUserStatuss = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPortalUserStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeportalUserStatus(): void {
    this.portalUserStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.portalUserStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllportalUserStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
