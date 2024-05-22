import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICmsPageType } from '@/shared/model/cms-page-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CmsPageTypeService from './cms-page-type.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CmsPageType extends mixins(AlertMixin) {
  @Inject('cmsPageTypeService') private cmsPageTypeService: () => CmsPageTypeService;
  private removeId: number = null;

  public cmsPageTypes: ICmsPageType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCmsPageTypes();
  }

  public clear(): void {
    this.retrieveAllCmsPageTypes();
  }

  public retrieveAllCmsPageTypes(): void {
    this.isFetching = true;

    this.cmsPageTypeService()
      .retrieve()
      .then(
        res => {
          this.cmsPageTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICmsPageType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCmsPageType(): void {
    this.cmsPageTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.cmsPageType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCmsPageTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
