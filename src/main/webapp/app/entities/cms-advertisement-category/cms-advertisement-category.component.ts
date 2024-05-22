import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CmsAdvertisementCategoryService from './cms-advertisement-category.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CmsAdvertisementCategory extends mixins(AlertMixin) {
  @Inject('cmsAdvertisementCategoryService') private cmsAdvertisementCategoryService: () => CmsAdvertisementCategoryService;
  private removeId: number = null;

  public cmsAdvertisementCategories: ICmsAdvertisementCategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCmsAdvertisementCategorys();
  }

  public clear(): void {
    this.retrieveAllCmsAdvertisementCategorys();
  }

  public retrieveAllCmsAdvertisementCategorys(): void {
    this.isFetching = true;

    this.cmsAdvertisementCategoryService()
      .retrieve()
      .then(
        res => {
          this.cmsAdvertisementCategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICmsAdvertisementCategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCmsAdvertisementCategory(): void {
    this.cmsAdvertisementCategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.cmsAdvertisementCategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCmsAdvertisementCategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
