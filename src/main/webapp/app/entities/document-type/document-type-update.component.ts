import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentType, DocumentType } from '@/shared/model/document-type.model';
import DocumentTypeService from './document-type.service';

const validations: any = {
  documentType: {
    type: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class DocumentTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;
  public documentType: IDocumentType = new DocumentType();

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeId) {
        vm.retrieveDocumentType(to.params.documentTypeId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentType.id) {
      this.documentTypeService()
        .update(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.documentType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentTypeService()
        .create(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.documentType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentType(documentTypeId): void {
    this.documentTypeService()
      .find(documentTypeId)
      .then(res => {
        this.documentType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
  }
}
