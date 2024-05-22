import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementSupporterService from './advertisement-supporter.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementSupporter extends mixins(AlertMixin) {
  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;
  private removeId: number = null;

  public advertisementSupporters: IAdvertisementSupporter[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementSupporters();
  }

  public clear(): void {
    this.retrieveAllAdvertisementSupporters();
  }

  public retrieveAllAdvertisementSupporters(): void {
    this.isFetching = true;

    this.advertisementSupporterService()
      .retrieve()
      .then(
        res => {
          this.advertisementSupporters = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementSupporter): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementSupporter(): void {
    this.advertisementSupporterService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementSupporter.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementSupporters();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
