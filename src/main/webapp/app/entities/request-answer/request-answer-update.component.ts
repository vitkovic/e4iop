import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRequestAnswer, RequestAnswer } from '@/shared/model/request-answer.model';
import RequestAnswerService from './request-answer.service';

const validations: any = {
  requestAnswer: {
    answer: {},
    answerEn: {},
    answerCyr: {},
  },
};

@Component({
  validations,
})
export default class RequestAnswerUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('requestAnswerService') private requestAnswerService: () => RequestAnswerService;
  public requestAnswer: IRequestAnswer = new RequestAnswer();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestAnswerId) {
        vm.retrieveRequestAnswer(to.params.requestAnswerId);
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
    if (this.requestAnswer.id) {
      this.requestAnswerService()
        .update(this.requestAnswer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.requestAnswer.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.requestAnswerService()
        .create(this.requestAnswer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.requestAnswer.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRequestAnswer(requestAnswerId): void {
    this.requestAnswerService()
      .find(requestAnswerId)
      .then(res => {
        this.requestAnswer = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
