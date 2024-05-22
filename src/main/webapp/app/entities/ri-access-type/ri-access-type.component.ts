import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiAccessType } from '@/shared/model/ri-access-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiAccessTypeService from './ri-access-type.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiAccessType extends mixins(AlertMixin) {
  @Inject('riAccessTypeService') private riAccessTypeService: () => RiAccessTypeService;
  private removeId: number = null;

  public riAccessTypes: IRiAccessType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRiAccessTypes();
  }

  public clear(): void {
    this.retrieveAllRiAccessTypes();
  }

  public retrieveAllRiAccessTypes(): void {
    this.isFetching = true;

    this.riAccessTypeService()
      .retrieve()
      .then(
        res => {
          this.riAccessTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiAccessType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiAccessType(): void {
    this.riAccessTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riAccessType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiAccessTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
