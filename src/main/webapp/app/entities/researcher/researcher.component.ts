import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IResearcher } from '@/shared/model/researcher.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ResearcherService from './researcher.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Researcher extends mixins(AlertMixin) {
  @Inject('researcherService') private researcherService: () => ResearcherService;
  private removeId: number = null;

  public researchers: IResearcher[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllResearchers();
  }

  public clear(): void {
    this.retrieveAllResearchers();
  }

  public retrieveAllResearchers(): void {
    this.isFetching = true;

    this.researcherService()
      .retrieve()
      .then(
        res => {
          this.researchers = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IResearcher): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeResearcher(): void {
    this.researcherService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.researcher.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllResearchers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
