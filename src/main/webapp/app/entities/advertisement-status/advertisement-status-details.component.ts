import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';
import AdvertisementStatusService from './advertisement-status.service';

@Component
export default class AdvertisementStatusDetails extends Vue {
  @Inject('advertisementStatusService') private advertisementStatusService: () => AdvertisementStatusService;
  public advertisementStatus: IAdvertisementStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementStatusId) {
        vm.retrieveAdvertisementStatus(to.params.advertisementStatusId);
      }
    });
  }

  public retrieveAdvertisementStatus(advertisementStatusId) {
    this.advertisementStatusService()
      .find(advertisementStatusId)
      .then(res => {
        this.advertisementStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
