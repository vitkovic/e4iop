import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPuoLegalStatus } from '@/shared/model/puo-legal-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PuoLegalStatusService from './puo-legal-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PuoLegalStatus extends mixins(AlertMixin) {
  @Inject('puoLegalStatusService') private puoLegalStatusService: () => PuoLegalStatusService;
  private removeId: number = null;

  public puoLegalStatuses: IPuoLegalStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPuoLegalStatuss();
  }

  public clear(): void {
    this.retrieveAllPuoLegalStatuss();
  }

  public retrieveAllPuoLegalStatuss(): void {
    this.isFetching = true;

    this.puoLegalStatusService()
      .retrieve()
      .then(
        res => {
          this.puoLegalStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPuoLegalStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePuoLegalStatus(): void {
    this.puoLegalStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.puoLegalStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPuoLegalStatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
