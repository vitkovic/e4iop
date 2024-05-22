import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IScientificDomain } from '@/shared/model/scientific-domain.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ScientificDomainService from './scientific-domain.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ScientificDomain extends mixins(AlertMixin) {
  @Inject('scientificDomainService') private scientificDomainService: () => ScientificDomainService;
  private removeId: number = null;

  public scientificDomains: IScientificDomain[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllScientificDomains();
  }

  public clear(): void {
    this.retrieveAllScientificDomains();
  }

  public retrieveAllScientificDomains(): void {
    this.isFetching = true;

    this.scientificDomainService()
      .retrieve()
      .then(
        res => {
          this.scientificDomains = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IScientificDomain): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeScientificDomain(): void {
    this.scientificDomainService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.scientificDomain.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllScientificDomains();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
