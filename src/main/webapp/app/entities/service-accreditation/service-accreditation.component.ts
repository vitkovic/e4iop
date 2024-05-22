import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IServiceAccreditation } from '@/shared/model/service-accreditation.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ServiceAccreditationService from './service-accreditation.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceAccreditation extends mixins(AlertMixin) {
  @Inject('serviceAccreditationService') private serviceAccreditationService: () => ServiceAccreditationService;
  private removeId: number = null;

  public serviceAccreditations: IServiceAccreditation[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllServiceAccreditations();
  }

  public clear(): void {
    this.retrieveAllServiceAccreditations();
  }

  public retrieveAllServiceAccreditations(): void {
    this.isFetching = true;

    this.serviceAccreditationService()
      .retrieve()
      .then(
        res => {
          this.serviceAccreditations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IServiceAccreditation): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeServiceAccreditation(): void {
    this.serviceAccreditationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.serviceAccreditation.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllServiceAccreditations();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
