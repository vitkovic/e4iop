import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementSupporterStatus } from '@/shared/model/advertisement-supporter-status.model';
import AdvertisementSupporterStatusService from './advertisement-supporter-status.service';

@Component
export default class AdvertisementSupporterStatusDetails extends Vue {
  @Inject('advertisementSupporterStatusService') private advertisementSupporterStatusService!: () => AdvertisementSupporterStatusService;
  public advertisementSupporterStatus: IAdvertisementSupporterStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSupporterStatusId) {
        vm.retrieveAdvertisementSupporterStatus(to.params.advertisementSupporterStatusId);
      }
    });
  }

  public retrieveAdvertisementSupporterStatus(advertisementSupporterStatusId: number) {
    this.advertisementSupporterStatusService()
      .find(advertisementSupporterStatusId)
      .then(res => {
        this.advertisementSupporterStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
