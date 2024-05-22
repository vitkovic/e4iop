import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRequestAnswer } from '@/shared/model/request-answer.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RequestAnswerService from './request-answer.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RequestAnswer extends mixins(AlertMixin) {
  @Inject('requestAnswerService') private requestAnswerService: () => RequestAnswerService;
  private removeId: number = null;

  public requestAnswers: IRequestAnswer[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRequestAnswers();
  }

  public clear(): void {
    this.retrieveAllRequestAnswers();
  }

  public retrieveAllRequestAnswers(): void {
    this.isFetching = true;

    this.requestAnswerService()
      .retrieve()
      .then(
        res => {
          this.requestAnswers = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRequestAnswer): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRequestAnswer(): void {
    this.requestAnswerService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.requestAnswer.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRequestAnswers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
