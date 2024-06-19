import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiFinanceSource } from '@/shared/model/ri-finance-source.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiFinanceSourceService from './ri-finance-source.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiFinanceSource extends mixins(AlertMixin) {
  @Inject('riFinanceSourceService')
  private riFinanceSourceService!: () => RiFinanceSourceService;

  private removeId: number | null = null;

  public riFinanceSources: IRiFinanceSource[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiFinanceSources();
  }

  public clear(): void {
    this.retrieveAllRiFinanceSources();
  }

  public retrieveAllRiFinanceSources(): void {
    this.isFetching = true;

    this.riFinanceSourceService()
      .retrieve()
      .then(
        res => {
          this.riFinanceSources = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiFinanceSource): void {
    this.removeId = instance.id!;
    if (this.$refs.removeEntity) {
      (this.$refs.removeEntity as any).show();
    }
  }

  public removeRiFinanceSource(): void {
    if (!this.removeId) {
      return;
    }
    this.riFinanceSourceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riFinanceSource.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiFinanceSources();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    if (this.$refs.removeEntity) {
      (this.$refs.removeEntity as any).hide();
    }
  }
}
