import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiSubcategory } from '@/shared/model/ri-subcategory.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiSubcategoryService from './ri-subcategory.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiSubcategory extends mixins(AlertMixin) {
  @Inject('riSubcategoryService') private riSubcategoryService: () => RiSubcategoryService;
  private removeId: number = null;

  public riSubcategories: IRiSubcategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiSubcategorys();
  }

  public clear(): void {
    this.retrieveAllRiSubcategorys();
  }

  public retrieveAllRiSubcategorys(): void {
    this.isFetching = true;

    this.riSubcategoryService()
      .retrieve()
      .then(
        res => {
          this.riSubcategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiSubcategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiSubcategory(): void {
    this.riSubcategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riSubcategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiSubcategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
