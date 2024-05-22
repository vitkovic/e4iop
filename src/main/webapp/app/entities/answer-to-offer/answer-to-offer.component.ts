import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAnswerToOffer } from '@/shared/model/answer-to-offer.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AnswerToOfferService from './answer-to-offer.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AnswerToOffer extends mixins(AlertMixin) {
  @Inject('answerToOfferService') private answerToOfferService: () => AnswerToOfferService;
  private removeId: number = null;

  public answerToOffers: IAnswerToOffer[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAnswerToOffers();
  }

  public clear(): void {
    this.retrieveAllAnswerToOffers();
  }

  public retrieveAllAnswerToOffers(): void {
    this.isFetching = true;

    this.answerToOfferService()
      .retrieve()
      .then(
        res => {
          this.answerToOffers = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAnswerToOffer): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAnswerToOffer(): void {
    this.answerToOfferService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.answerToOffer.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAnswerToOffers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
