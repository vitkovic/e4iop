import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementSubcategoryService from './advertisement-subcategory.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementSubcategory extends mixins(AlertMixin) {
  @Inject('advertisementSubcategoryService') private advertisementSubcategoryService: () => AdvertisementSubcategoryService;
  private removeId: number = null;

  public advertisementSubcategories: IAdvertisementSubcategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementSubcategorys();
  }

  public clear(): void {
    this.retrieveAllAdvertisementSubcategorys();
  }

  public retrieveAllAdvertisementSubcategorys(): void {
    this.isFetching = true;

    this.advertisementSubcategoryService()
      .retrieve()
      .then(
        res => {
          this.advertisementSubcategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementSubcategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementSubcategory(): void {
    this.advertisementSubcategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementSubcategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementSubcategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
