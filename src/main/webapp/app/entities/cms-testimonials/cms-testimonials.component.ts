import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICmsTestimonials } from '@/shared/model/cms-testimonials.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CmsTestimonialsService from './cms-testimonials.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CmsTestimonials extends mixins(AlertMixin) {
  @Inject('cmsTestimonialsService') private cmsTestimonialsService: () => CmsTestimonialsService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cmsTestimonials: ICmsTestimonials[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCmsTestimonialss();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCmsTestimonialss();
  }

  public retrieveAllCmsTestimonialss(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.cmsTestimonialsService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cmsTestimonials = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICmsTestimonials): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCmsTestimonials(): void {
    this.cmsTestimonialsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.cmsTestimonials.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCmsTestimonialss();
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
    this.retrieveAllCmsTestimonialss();
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
