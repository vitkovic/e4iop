import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICollaborationStatus } from '@/shared/model/collaboration-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CollaborationStatusService from './collaboration-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CollaborationStatus extends mixins(AlertMixin) {
  @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;
  private removeId: number = null;

  public collaborationStatuses: ICollaborationStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCollaborationStatuss();
  }

  public clear(): void {
    this.retrieveAllCollaborationStatuss();
  }

  public retrieveAllCollaborationStatuss(): void {
    this.isFetching = true;

    this.collaborationStatusService()
      .retrieve()
      .then(
        res => {
          this.collaborationStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICollaborationStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCollaborationStatus(): void {
    this.collaborationStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.collaborationStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCollaborationStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
