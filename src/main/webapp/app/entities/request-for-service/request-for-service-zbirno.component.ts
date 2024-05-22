import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequestForService } from '@/shared/model/request-for-service.model';
import { IRequestRespond } from '@/shared/model/request-respond.model';
import { IServiceOfferRespond } from '@/shared/model/service-offer-respond.model';
import RequestForServiceService from './request-for-service.service';

@Component
export default class RequestForServiceZbirno extends Vue {
  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;
  public requestForService: IRequestForService = {};
  public currentLanguage = '';
  public requestRespond: IRequestRespond = {};
  public serviceOfferRespond: IServiceOfferRespond = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestForServiceId) {
        vm.retrieveRequestForServiceZbirno(to.params.requestForServiceId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public retrieveRequestForServiceZbirno(requestForServiceId) {
    debugger;
    this.requestForServiceService()
      .retrieveZbir(requestForServiceId)
      .then(res => {
        //this.requestForServiceZbirno = res;
        this.requestForService = res.requestForService;
        this.requestRespond = res.requestRespond;
        this.serviceOfferRespond = res.serviceOfferRespond;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
