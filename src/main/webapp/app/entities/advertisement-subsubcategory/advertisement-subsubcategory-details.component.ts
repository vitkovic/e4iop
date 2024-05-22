import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
import AdvertisementSubsubcategoryService from './advertisement-subsubcategory.service';

@Component
export default class AdvertisementSubsubcategoryDetails extends Vue {
  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;
  public advertisementSubsubcategory: IAdvertisementSubsubcategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSubsubcategoryId) {
        vm.retrieveAdvertisementSubsubcategory(to.params.advertisementSubsubcategoryId);
      }
    });
  }

  public retrieveAdvertisementSubsubcategory(advertisementSubsubcategoryId) {
    this.advertisementSubsubcategoryService()
      .find(advertisementSubsubcategoryId)
      .then(res => {
        this.advertisementSubsubcategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
