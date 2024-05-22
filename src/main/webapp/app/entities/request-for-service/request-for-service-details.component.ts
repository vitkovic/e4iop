import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequestForService } from '@/shared/model/request-for-service.model';
import RequestForServiceService from './request-for-service.service';

@Component
export default class RequestForServiceDetails extends Vue {
  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;
  public requestForService: IRequestForService = {};
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestForServiceId) {
        vm.retrieveRequestForService(to.params.requestForServiceId);
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

  public retrieveRequestForService(requestForServiceId) {
    this.requestForServiceService()
      .find(requestForServiceId)
      .then(res => {
        this.requestForService = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
