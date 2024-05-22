import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IServiceSubtype } from '@/shared/model/service-subtype.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ServiceSubtypeService from './service-subtype.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceSubtype extends mixins(AlertMixin) {
  @Inject('serviceSubtypeService') private serviceSubtypeService: () => ServiceSubtypeService;
  private removeId: number = null;

  public serviceSubtypes: IServiceSubtype[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllServiceSubtypes();
  }

  public clear(): void {
    this.retrieveAllServiceSubtypes();
  }

  public retrieveAllServiceSubtypes(): void {
    this.isFetching = true;

    this.serviceSubtypeService()
      .retrieve()
      .then(
        res => {
          this.serviceSubtypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IServiceSubtype): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeServiceSubtype(): void {
    this.serviceSubtypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.serviceSubtype.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllServiceSubtypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
