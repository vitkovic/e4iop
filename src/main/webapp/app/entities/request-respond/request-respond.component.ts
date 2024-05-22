import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRequestRespond } from '@/shared/model/request-respond.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RequestRespondService from './request-respond.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RequestRespond extends mixins(AlertMixin) {
  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;
  private removeId: number = null;

  public requestResponds: IRequestRespond[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRequestResponds();
  }

  public clear(): void {
    this.retrieveAllRequestResponds();
  }

  public retrieveAllRequestResponds(): void {
    this.isFetching = true;

    this.requestRespondService()
      .retrieve()
      .then(
        res => {
          this.requestResponds = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRequestRespond): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRequestRespond(): void {
    this.requestRespondService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.requestRespond.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRequestResponds();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
