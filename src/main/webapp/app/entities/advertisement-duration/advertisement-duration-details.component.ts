import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementDuration } from '@/shared/model/advertisement-duration.model';
import AdvertisementDurationService from './advertisement-duration.service';

@Component
export default class AdvertisementDurationDetails extends Vue {
  @Inject('advertisementDurationService') private advertisementDurationService: () => AdvertisementDurationService;
  public advertisementDuration: IAdvertisementDuration = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementDurationId) {
        vm.retrieveAdvertisementDuration(to.params.advertisementDurationId);
      }
    });
  }

  public retrieveAdvertisementDuration(advertisementDurationId) {
    this.advertisementDurationService()
      .find(advertisementDurationId)
      .then(res => {
        this.advertisementDuration = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
