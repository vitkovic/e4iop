import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiSubcategory } from '@/shared/model/ri-subcategory.model';
import RiSubcategoryService from './ri-subcategory.service';

@Component
export default class RiSubcategoryDetails extends Vue {
  @Inject('riSubcategoryService') private riSubcategoryService: () => RiSubcategoryService;
  public riSubcategory: IRiSubcategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riSubcategoryId) {
        vm.retrieveRiSubcategory(to.params.riSubcategoryId);
      }
    });
  }

  public retrieveRiSubcategory(riSubcategoryId) {
    this.riSubcategoryService()
      .find(riSubcategoryId)
      .then(res => {
        this.riSubcategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
