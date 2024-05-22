import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumentKind } from '@/shared/model/document-kind.model';
import DocumentKindService from './document-kind.service';

@Component
export default class DocumentKindDetails extends Vue {
  @Inject('documentKindService') private documentKindService: () => DocumentKindService;
  public documentKind: IDocumentKind = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentKindId) {
        vm.retrieveDocumentKind(to.params.documentKindId);
      }
    });
  }

  public retrieveDocumentKind(documentKindId) {
    this.documentKindService()
      .find(documentKindId)
      .then(res => {
        this.documentKind = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
