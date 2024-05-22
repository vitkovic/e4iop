import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementType } from '@/shared/model/advertisement-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementTypeService from './advertisement-type.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementType extends mixins(AlertMixin) {
  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
  private removeId: number = null;

  public advertisementTypes: IAdvertisementType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementTypes();
  }

  public clear(): void {
    this.retrieveAllAdvertisementTypes();
  }

  public retrieveAllAdvertisementTypes(): void {
    this.isFetching = true;

    this.advertisementTypeService()
      .retrieve()
      .then(
        res => {
          this.advertisementTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementType(): void {
    this.advertisementTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
