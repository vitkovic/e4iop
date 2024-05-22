import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IAnswerToOffer, AnswerToOffer } from '@/shared/model/answer-to-offer.model';
import AnswerToOfferService from './answer-to-offer.service';

const validations: any = {
  answerToOffer: {
    answer: {},
    answerEn: {},
    answerCyr: {},
  },
};

@Component({
  validations,
})
export default class AnswerToOfferUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('answerToOfferService') private answerToOfferService: () => AnswerToOfferService;
  public answerToOffer: IAnswerToOffer = new AnswerToOffer();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.answerToOfferId) {
        vm.retrieveAnswerToOffer(to.params.answerToOfferId);
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

  public save(): void {
    this.isSaving = true;
    if (this.answerToOffer.id) {
      this.answerToOfferService()
        .update(this.answerToOffer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.answerToOffer.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.answerToOfferService()
        .create(this.answerToOffer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.answerToOffer.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAnswerToOffer(answerToOfferId): void {
    this.answerToOfferService()
      .find(answerToOfferId)
      .then(res => {
        this.answerToOffer = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
