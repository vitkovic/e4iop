import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICmsSlider } from '@/shared/model/cms-slider.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CmsSliderService from './cms-slider.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CmsSlider extends mixins(AlertMixin) {
  @Inject('cmsSliderService') private cmsSliderService: () => CmsSliderService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cmsSliders: ICmsSlider[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCmsSliders();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCmsSliders();
  }

  public retrieveAllCmsSliders(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.cmsSliderService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cmsSliders = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICmsSlider): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCmsSlider(): void {
    this.cmsSliderService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.cmsSlider.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCmsSliders();
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
    this.retrieveAllCmsSliders();
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
