import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiServiceStatus } from '@/shared/model/ri-service-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiServiceStatusService from './ri-service-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiServiceStatus extends mixins(AlertMixin) {
  @Inject('riServiceStatusService') private riServiceStatusService: () => RiServiceStatusService;
  private removeId: number = null;

  public riServiceStatuses: IRiServiceStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiServiceStatuss();
  }

  public clear(): void {
    this.retrieveAllRiServiceStatuss();
  }

  public retrieveAllRiServiceStatuss(): void {
    this.isFetching = true;

    this.riServiceStatusService()
      .retrieve()
      .then(
        res => {
          this.riServiceStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiServiceStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiServiceStatus(): void {
    this.riServiceStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riServiceStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiServiceStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
