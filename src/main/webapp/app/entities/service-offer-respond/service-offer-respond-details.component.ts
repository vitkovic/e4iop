import { Component, Vue, Inject } from 'vue-property-decorator';

import { IServiceOfferRespond } from '@/shared/model/service-offer-respond.model';
import ServiceOfferRespondService from './service-offer-respond.service';

@Component
export default class ServiceOfferRespondDetails extends Vue {
  @Inject('serviceOfferRespondService') private serviceOfferRespondService: () => ServiceOfferRespondService;
  public serviceOfferRespond: IServiceOfferRespond = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceOfferRespondId) {
        vm.retrieveServiceOfferRespond(to.params.serviceOfferRespondId);
      }
    });
  }

  public retrieveServiceOfferRespond(serviceOfferRespondId) {
    this.serviceOfferRespondService()
      .find(serviceOfferRespondId)
      .then(res => {
        this.serviceOfferRespond = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
