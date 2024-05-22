import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementCategory } from '@/shared/model/advertisement-category.model';
import AdvertisementCategoryService from './advertisement-category.service';

@Component
export default class AdvertisementCategoryDetails extends Vue {
  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;
  public advertisementCategory: IAdvertisementCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementCategoryId) {
        vm.retrieveAdvertisementCategory(to.params.advertisementCategoryId);
      }
    });
  }

  public retrieveAdvertisementCategory(advertisementCategoryId) {
    this.advertisementCategoryService()
      .find(advertisementCategoryId)
      .then(res => {
        this.advertisementCategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
