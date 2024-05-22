import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementStatusService from './advertisement-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementStatus extends mixins(AlertMixin) {
  @Inject('advertisementStatusService') private advertisementStatusService: () => AdvertisementStatusService;
  private removeId: number = null;

  public advertisementStatuses: IAdvertisementStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementStatuss();
  }

  public clear(): void {
    this.retrieveAllAdvertisementStatuss();
  }

  public retrieveAllAdvertisementStatuss(): void {
    this.isFetching = true;

    this.advertisementStatusService()
      .retrieve()
      .then(
        res => {
          this.advertisementStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementStatus(): void {
    this.advertisementStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
