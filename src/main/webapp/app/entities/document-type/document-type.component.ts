import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentType } from '@/shared/model/document-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentTypeService from './document-type.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DocumentType extends mixins(AlertMixin) {
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;
  private removeId: number = null;

  public documentTypes: IDocumentType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentTypes();
  }

  public clear(): void {
    this.retrieveAllDocumentTypes();
  }

  public retrieveAllDocumentTypes(): void {
    this.isFetching = true;

    this.documentTypeService()
      .retrieve()
      .then(
        res => {
          this.documentTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDocumentType(): void {
    this.documentTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.documentType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllDocumentTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
