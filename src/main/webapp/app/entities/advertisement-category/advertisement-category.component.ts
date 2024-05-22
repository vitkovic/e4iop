import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisementCategory } from '@/shared/model/advertisement-category.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementCategoryService from './advertisement-category.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdvertisementCategory extends mixins(AlertMixin) {
  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;
  private removeId: number = null;

  public advertisementCategories: IAdvertisementCategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdvertisementCategorys();
  }

  public clear(): void {
    this.retrieveAllAdvertisementCategorys();
  }

  public retrieveAllAdvertisementCategorys(): void {
    this.isFetching = true;

    this.advertisementCategoryService()
      .retrieve()
      .then(
        res => {
          this.advertisementCategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdvertisementCategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisementCategory(): void {
    this.advertisementCategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisementCategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisementCategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
