import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ScientificSubdomainService from './scientific-subdomain.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ScientificSubdomain extends mixins(AlertMixin) {
  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;
  private removeId: number = null;

  public scientificSubdomains: IScientificSubdomain[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllScientificSubdomains();
  }

  public clear(): void {
    this.retrieveAllScientificSubdomains();
  }

  public retrieveAllScientificSubdomains(): void {
    this.isFetching = true;

    this.scientificSubdomainService()
      .retrieve()
      .then(
        res => {
          this.scientificSubdomains = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IScientificSubdomain): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeScientificSubdomain(): void {
    this.scientificSubdomainService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.scientificSubdomain.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllScientificSubdomains();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
