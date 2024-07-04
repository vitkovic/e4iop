import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsSlider } from '@/shared/model/cms-slider.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import CmsSliderService from './cms-slider.service';
import DocumentService from '../document/document.service';

@Component
export default class CmsSliderDetails extends Vue {
  @Inject('cmsSliderService') private cmsSliderService: () => CmsSliderService;
  @Inject('documentService') private documentService: () => DocumentService;

  public cmsSlider: ICmsSlider = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsSliderId) {
        vm.retrieveCmsSlider(to.params.cmsSliderId);
      }
    });
  }

  public retrieveCmsSlider(cmsSliderId) {
    this.cmsSliderService()
      .find(cmsSliderId)
      .then(res => {
        this.cmsSlider = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public retrieveFile(file: IDocument): string {
    if (file.type.type === DocumentTypeOptions.IMAGE) {
      return this.documentService().retrieveImage(file.filename);
    } else if (file.type.type === DocumentTypeOptions.DOCUMENT) {
      return this.documentService().retrieveDocument(file.filename);
    } else {
      return '';
    }
  }
}
