import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementKindService from './advertisement-kind.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementKind extends mixins(AlertMixin) {
  @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;
  private removeId: number = null;

  public advertisementKinds: IAdvertisementKind[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementKinds();
  }

  public clear(): void {
    this.retrieveAllAdvertisementKinds();
  }

  public retrieveAllAdvertisementKinds(): void {
    this.isFetching = true;

    this.advertisementKindService()
      .retrieve()
      .then(
        res => {
          this.advertisementKinds = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementKind): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementKind(): void {
    this.advertisementKindService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementKind.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementKinds();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
