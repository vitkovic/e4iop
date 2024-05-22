import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsPage } from '@/shared/model/cms-page.model';
import CmsPageService from './cms-page.service';

@Component
export default class CmsPageDetails extends Vue {
  @Inject('cmsPageService') private cmsPageService: () => CmsPageService;
  public cmsPage: ICmsPage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsPageId) {
        vm.retrieveCmsPage(to.params.cmsPageId);
      }
    });
  }

  public retrieveCmsPage(cmsPageId) {
    this.cmsPageService()
      .find(cmsPageId)
      .then(res => {
        this.cmsPage = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
