import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiCategory } from '@/shared/model/ri-category.model';
import RiCategoryService from './ri-category.service';

@Component
export default class RiCategoryDetails extends Vue {
  @Inject('riCategoryService') private riCategoryService: () => RiCategoryService;
  public riCategory: IRiCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riCategoryId) {
        vm.retrieveRiCategory(to.params.riCategoryId);
      }
    });
  }

  public retrieveRiCategory(riCategoryId) {
    this.riCategoryService()
      .find(riCategoryId)
      .then(res => {
        this.riCategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
