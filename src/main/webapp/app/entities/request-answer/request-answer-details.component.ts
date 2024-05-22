import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequestAnswer } from '@/shared/model/request-answer.model';
import RequestAnswerService from './request-answer.service';

@Component
export default class RequestAnswerDetails extends Vue {
  @Inject('requestAnswerService') private requestAnswerService: () => RequestAnswerService;
  public requestAnswer: IRequestAnswer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestAnswerId) {
        vm.retrieveRequestAnswer(to.params.requestAnswerId);
      }
    });
  }

  public retrieveRequestAnswer(requestAnswerId) {
    this.requestAnswerService()
      .find(requestAnswerId)
      .then(res => {
        this.requestAnswer = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
