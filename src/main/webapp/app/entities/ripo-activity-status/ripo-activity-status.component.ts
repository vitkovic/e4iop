import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRipoActivityStatus } from '@/shared/model/ripo-activity-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RipoActivityStatusService from './ripo-activity-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RipoActivityStatus extends mixins(AlertMixin) {
  @Inject('ripoActivityStatusService') private ripoActivityStatusService: () => RipoActivityStatusService;
  private removeId: number = null;

  public ripoActivityStatuses: IRipoActivityStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRipoActivityStatuss();
  }

  public clear(): void {
    this.retrieveAllRipoActivityStatuss();
  }

  public retrieveAllRipoActivityStatuss(): void {
    this.isFetching = true;

    this.ripoActivityStatusService()
      .retrieve()
      .then(
        res => {
          this.ripoActivityStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRipoActivityStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRipoActivityStatus(): void {
    this.ripoActivityStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.ripoActivityStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRipoActivityStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
