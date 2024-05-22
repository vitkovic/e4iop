import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import AdvertisementSupporterService from './advertisement-supporter.service';

@Component
export default class AdvertisementSupporterDetails extends Vue {
  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;
  public advertisementSupporter: IAdvertisementSupporter = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSupporterId) {
        vm.retrieveAdvertisementSupporter(to.params.advertisementSupporterId);
      }
    });
  }

  public retrieveAdvertisementSupporter(advertisementSupporterId) {
    this.advertisementSupporterService()
      .find(advertisementSupporterId)
      .then(res => {
        this.advertisementSupporter = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
