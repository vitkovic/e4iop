import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnswerToOffer } from '@/shared/model/answer-to-offer.model';
import AnswerToOfferService from './answer-to-offer.service';

@Component
export default class AnswerToOfferDetails extends Vue {
  @Inject('answerToOfferService') private answerToOfferService: () => AnswerToOfferService;
  public answerToOffer: IAnswerToOffer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.answerToOfferId) {
        vm.retrieveAnswerToOffer(to.params.answerToOfferId);
      }
    });
  }

  public retrieveAnswerToOffer(answerToOfferId) {
    this.answerToOfferService()
      .find(answerToOfferId)
      .then(res => {
        this.answerToOffer = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
