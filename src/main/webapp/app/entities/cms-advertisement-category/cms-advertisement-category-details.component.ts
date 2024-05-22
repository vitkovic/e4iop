import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';
import CmsAdvertisementCategoryService from './cms-advertisement-category.service';

@Component
export default class CmsAdvertisementCategoryDetails extends Vue {
  @Inject('cmsAdvertisementCategoryService') private cmsAdvertisementCategoryService: () => CmsAdvertisementCategoryService;
  public cmsAdvertisementCategory: ICmsAdvertisementCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsAdvertisementCategoryId) {
        vm.retrieveCmsAdvertisementCategory(to.params.cmsAdvertisementCategoryId);
      }
    });
  }

  public retrieveCmsAdvertisementCategory(cmsAdvertisementCategoryId) {
    this.cmsAdvertisementCategoryService()
      .find(cmsAdvertisementCategoryId)
      .then(res => {
        this.cmsAdvertisementCategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
