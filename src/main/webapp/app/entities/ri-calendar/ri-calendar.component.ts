import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiCalendarService from './ri-calendar.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiCalendar extends mixins(AlertMixin) {
  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public riCalendars: IRiCalendar[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiCalendars();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRiCalendars();
  }

  public retrieveAllRiCalendars(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.riCalendarService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.riCalendars = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiCalendar): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiCalendar(): void {
    this.riCalendarService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riCalendar.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiCalendars();
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
    this.retrieveAllRiCalendars();
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
