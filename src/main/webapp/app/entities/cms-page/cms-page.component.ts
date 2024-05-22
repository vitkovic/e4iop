import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICmsPage } from '@/shared/model/cms-page.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CmsPageService from './cms-page.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CmsPage extends mixins(AlertMixin) {
  @Inject('cmsPageService') private cmsPageService: () => CmsPageService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cmsPages: ICmsPage[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCmsPages();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCmsPages();
  }

  public retrieveAllCmsPages(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.cmsPageService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cmsPages = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICmsPage): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCmsPage(): void {
    this.cmsPageService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.cmsPage.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCmsPages();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllCmsPages();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
