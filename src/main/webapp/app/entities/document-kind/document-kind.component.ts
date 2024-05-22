import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentKind } from '@/shared/model/document-kind.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentKindService from './document-kind.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DocumentKind extends mixins(AlertMixin) {
  @Inject('documentKindService') private documentKindService: () => DocumentKindService;
  private removeId: number = null;

  public documentKinds: IDocumentKind[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentKinds();
  }

  public clear(): void {
    this.retrieveAllDocumentKinds();
  }

  public retrieveAllDocumentKinds(): void {
    this.isFetching = true;

    this.documentKindService()
      .retrieve()
      .then(
        res => {
          this.documentKinds = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentKind): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDocumentKind(): void {
    this.documentKindService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.documentKind.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllDocumentKinds();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
