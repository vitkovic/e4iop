import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiStatus } from '@/shared/model/ri-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiStatusService from './ri-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiStatus extends mixins(AlertMixin) {
  @Inject('riStatusService') private riStatusService: () => RiStatusService;
  private removeId: number = null;

  public riStatuses: IRiStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiStatuss();
  }

  public clear(): void {
    this.retrieveAllRiStatuss();
  }

  public retrieveAllRiStatuss(): void {
    this.isFetching = true;

    this.riStatusService()
      .retrieve()
      .then(
        res => {
          this.riStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiStatus(): void {
    this.riStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
