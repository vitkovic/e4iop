import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementType } from '@/shared/model/advertisement-type.model';
import AdvertisementTypeService from './advertisement-type.service';

@Component
export default class AdvertisementTypeDetails extends Vue {
  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
  public advertisementType: IAdvertisementType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementTypeId) {
        vm.retrieveAdvertisementType(to.params.advertisementTypeId);
      }
    });
  }

  public retrieveAdvertisementType(advertisementTypeId) {
    this.advertisementTypeService()
      .find(advertisementTypeId)
      .then(res => {
        this.advertisementType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
