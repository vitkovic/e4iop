import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiAccessMode } from '@/shared/model/ri-access-mode.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiAccessModeService from './ri-access-mode.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiAccessMode extends mixins(AlertMixin) {
  @Inject('riAccessModeService') private riAccessModeService: () => RiAccessModeService;
  private removeId: number = null;

  public riAccessModes: IRiAccessMode[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiAccessModes();
  }

  public clear(): void {
    this.retrieveAllRiAccessModes();
  }

  public retrieveAllRiAccessModes(): void {
    this.isFetching = true;

    this.riAccessModeService()
      .retrieve()
      .then(
        res => {
          this.riAccessModes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiAccessMode): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiAccessMode(): void {
    this.riAccessModeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riAccessMode.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiAccessModes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
