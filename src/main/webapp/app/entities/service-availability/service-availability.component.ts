import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IServiceAvailability } from '@/shared/model/service-availability.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ServiceAvailabilityService from './service-availability.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceAvailability extends mixins(AlertMixin) {
  @Inject('serviceAvailabilityService') private serviceAvailabilityService: () => ServiceAvailabilityService;
  private removeId: number = null;

  public serviceAvailabilities: IServiceAvailability[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllServiceAvailabilitys();
  }

  public clear(): void {
    this.retrieveAllServiceAvailabilitys();
  }

  public retrieveAllServiceAvailabilitys(): void {
    this.isFetching = true;

    this.serviceAvailabilityService()
      .retrieve()
      .then(
        res => {
          this.serviceAvailabilities = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IServiceAvailability): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeServiceAvailability(): void {
    this.serviceAvailabilityService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.serviceAvailability.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllServiceAvailabilitys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
