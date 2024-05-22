import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';
import AdvertisementKindService from './advertisement-kind.service';

@Component
export default class AdvertisementKindDetails extends Vue {
  @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;
  public advertisementKind: IAdvertisementKind = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementKindId) {
        vm.retrieveAdvertisementKind(to.params.advertisementKindId);
      }
    });
  }

  public retrieveAdvertisementKind(advertisementKindId) {
    this.advertisementKindService()
      .find(advertisementKindId)
      .then(res => {
        this.advertisementKind = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
