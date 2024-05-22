import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsPageType } from '@/shared/model/cms-page-type.model';
import CmsPageTypeService from './cms-page-type.service';

@Component
export default class CmsPageTypeDetails extends Vue {
  @Inject('cmsPageTypeService') private cmsPageTypeService: () => CmsPageTypeService;
  public cmsPageType: ICmsPageType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsPageTypeId) {
        vm.retrieveCmsPageType(to.params.cmsPageTypeId);
      }
    });
  }

  public retrieveCmsPageType(cmsPageTypeId) {
    this.cmsPageTypeService()
      .find(cmsPageTypeId)
      .then(res => {
        this.cmsPageType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
