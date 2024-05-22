import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IResearchInstitution } from '@/shared/model/research-institution.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ResearchInstitutionService from './research-institution.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ResearchInstitution extends mixins(AlertMixin) {
  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;
  private removeId: number = null;

  public researchInstitutions: IResearchInstitution[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllResearchInstitutions();
  }

  public clear(): void {
    this.retrieveAllResearchInstitutions();
  }

  public retrieveAllResearchInstitutions(): void {
    this.isFetching = true;

    this.researchInstitutionService()
      .retrieve()
      .then(
        res => {
          this.researchInstitutions = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IResearchInstitution): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeResearchInstitution(): void {
    this.researchInstitutionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.researchInstitution.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllResearchInstitutions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
