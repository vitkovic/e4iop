import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentKind, DocumentKind } from '@/shared/model/document-kind.model';
import DocumentKindService from './document-kind.service';

const validations: any = {
  documentKind: {
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
export default class DocumentKindUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentKindService') private documentKindService: () => DocumentKindService;
  public documentKind: IDocumentKind = new DocumentKind();

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentKindId) {
        vm.retrieveDocumentKind(to.params.documentKindId);
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
    if (this.documentKind.id) {
      this.documentKindService()
        .update(this.documentKind)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.documentKind.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentKindService()
        .create(this.documentKind)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.documentKind.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentKind(documentKindId): void {
    this.documentKindService()
      .find(documentKindId)
      .then(res => {
        this.documentKind = res;
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
