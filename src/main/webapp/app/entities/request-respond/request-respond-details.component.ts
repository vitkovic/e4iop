import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequestRespond } from '@/shared/model/request-respond.model';
import RequestRespondService from './request-respond.service';

@Component
export default class RequestRespondDetails extends Vue {
  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;
  public requestRespond: IRequestRespond = {};
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestRespondId) {
        vm.retrieveRequestRespond(to.params.requestRespondId);
      } else if (to.params.requestForServiceId) {
        vm.retrieveRequestRespondForRequestService(to.params.requestForServiceId);
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

  public retrieveRequestRespond(requestRespondId) {
    this.requestRespondService()
      .find(requestRespondId)
      .then(res => {
        this.requestRespond = res;
      });
  }

  public retrieveRequestRespondForRequestService(requestForServiceId): void {
    this.requestRespondService()
      .findForRequestService(requestForServiceId)
      .then(res => {
        if (res.id) {
          res.date = new Date(res.date);
          res.startTime = new Date(res.startTime);
          res.endTime = new Date(res.endTime);
          this.requestRespond = res;
        } else {
          return;
        }
      });
  }

  public sendToOffer(): void {
    this.$router.push({
      name: 'ServiceOfferRespondRespondCreateChoice',
      params: { requestRespondId: JSON.stringify(this.requestRespond.id).replace(/["]/g, '') },
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
