import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementSubcategory } from '@/shared/model/advertisement-subcategory.model';
import AdvertisementSubcategoryService from './advertisement-subcategory.service';

@Component
export default class AdvertisementSubcategoryDetails extends Vue {
  @Inject('advertisementSubcategoryService') private advertisementSubcategoryService: () => AdvertisementSubcategoryService;
  public advertisementSubcategory: IAdvertisementSubcategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSubcategoryId) {
        vm.retrieveAdvertisementSubcategory(to.params.advertisementSubcategoryId);
      }
    });
  }

  public retrieveAdvertisementSubcategory(advertisementSubcategoryId) {
    this.advertisementSubcategoryService()
      .find(advertisementSubcategoryId)
      .then(res => {
        this.advertisementSubcategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
