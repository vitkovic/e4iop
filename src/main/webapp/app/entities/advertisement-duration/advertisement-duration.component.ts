import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementDuration } from '@/shared/model/advertisement-duration.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementDurationService from './advertisement-duration.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementDuration extends mixins(AlertMixin) {
  @Inject('advertisementDurationService') private advertisementDurationService: () => AdvertisementDurationService;
  private removeId: number = null;

  public advertisementDurations: IAdvertisementDuration[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementDurations();
  }

  public clear(): void {
    this.retrieveAllAdvertisementDurations();
  }

  public retrieveAllAdvertisementDurations(): void {
    this.isFetching = true;

    this.advertisementDurationService()
      .retrieve()
      .then(
        res => {
          this.advertisementDurations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementDuration): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementDuration(): void {
    this.advertisementDurationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementDuration.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementDurations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
