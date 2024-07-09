import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementSupporterStatus } from '@/shared/model/advertisement-supporter-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementSupporterStatusService from './advertisement-supporter-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementSupporterStatusComponent extends mixins(AlertMixin) {
  @Inject('advertisementSupporterStatusService') private advertisementSupporterStatusService: () => AdvertisementSupporterStatusService;
  private removeId: number | null = null;

  public advertisementSupporterStatuses: IAdvertisementSupporterStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementSupporterStatuses();
  }

  public clear(): void {
    this.retrieveAllAdvertisementSupporterStatuses();
  }

  public retrieveAllAdvertisementSupporterStatuses(): void {
    this.isFetching = true;

    this.advertisementSupporterStatusService()
      .retrieve()
      .then(
        res => {
          this.advertisementSupporterStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementSupporterStatus): void {
    this.removeId = instance.id!;
    if (this.$refs.removeEntity) {
      (this.$refs.removeEntity as any).show();
    }
  }

  public removeAdvertisementSupporterStatus(): void {
    if (this.removeId !== null) {
      this.advertisementSupporterStatusService()
        .delete(this.removeId)
        .then(() => {
          const message = this.$t('riportalApp.advertisementSupporterStatus.deleted', { param: this.removeId });
          this.alertService().showAlert(message, 'danger');
          this.getAlertFromStore();
          this.removeId = null;
          this.retrieveAllAdvertisementSupporterStatuses();
          this.closeDialog();
        });
    }
  }

  public closeDialog(): void {
    if (this.$refs.removeEntity) {
      (this.$refs.removeEntity as any).hide();
    }
  }
}
