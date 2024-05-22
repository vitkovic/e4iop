import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiCategory } from '@/shared/model/ri-category.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiCategoryService from './ri-category.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiCategory extends mixins(AlertMixin) {
  @Inject('riCategoryService') private riCategoryService: () => RiCategoryService;
  private removeId: number = null;

  public riCategories: IRiCategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiCategorys();
  }

  public clear(): void {
    this.retrieveAllRiCategorys();
  }

  public retrieveAllRiCategorys(): void {
    this.isFetching = true;

    this.riCategoryService()
      .retrieve()
      .then(
        res => {
          this.riCategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiCategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiCategory(): void {
    this.riCategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riCategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiCategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
