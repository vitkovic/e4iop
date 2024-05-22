import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementSubsubcategoryService from './advertisement-subsubcategory.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementSubsubcategory extends mixins(AlertMixin) {
  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;
  private removeId: number = null;

  public advertisementSubsubcategories: IAdvertisementSubsubcategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementSubsubcategorys();
  }

  public clear(): void {
    this.retrieveAllAdvertisementSubsubcategorys();
  }

  public retrieveAllAdvertisementSubsubcategorys(): void {
    this.isFetching = true;

    this.advertisementSubsubcategoryService()
      .retrieve()
      .then(
        res => {
          this.advertisementSubsubcategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementSubsubcategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementSubsubcategory(): void {
    this.advertisementSubsubcategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementSubsubcategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementSubsubcategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
