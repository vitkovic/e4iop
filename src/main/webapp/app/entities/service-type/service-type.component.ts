import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IServiceType } from '@/shared/model/service-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ServiceTypeService from './service-type.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceType extends mixins(AlertMixin) {
  @Inject('serviceTypeService') private serviceTypeService: () => ServiceTypeService;
  private removeId: number = null;

  public serviceTypes: IServiceType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllServiceTypes();
  }

  public clear(): void {
    this.retrieveAllServiceTypes();
  }

  public retrieveAllServiceTypes(): void {
    this.isFetching = true;

    this.serviceTypeService()
      .retrieve()
      .then(
        res => {
          this.serviceTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IServiceType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeServiceType(): void {
    this.serviceTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.serviceType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllServiceTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
