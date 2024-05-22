import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IServiceOfferRespond } from '@/shared/model/service-offer-respond.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ServiceOfferRespondService from './service-offer-respond.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceOfferRespond extends mixins(AlertMixin) {
  @Inject('serviceOfferRespondService') private serviceOfferRespondService: () => ServiceOfferRespondService;
  private removeId: number = null;

  public serviceOfferResponds: IServiceOfferRespond[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllServiceOfferResponds();
  }

  public clear(): void {
    this.retrieveAllServiceOfferResponds();
  }

  public retrieveAllServiceOfferResponds(): void {
    this.isFetching = true;

    this.serviceOfferRespondService()
      .retrieve()
      .then(
        res => {
          this.serviceOfferResponds = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IServiceOfferRespond): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeServiceOfferRespond(): void {
    this.serviceOfferRespondService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.serviceOfferRespond.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllServiceOfferResponds();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
