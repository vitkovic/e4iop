import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICollaborationRating } from '@/shared/model/collaboration-rating.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CollaborationRatingService from './collaboration-rating.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CollaborationRating extends mixins(AlertMixin) {
  @Inject('collaborationRatingService') private collaborationRatingService: () => CollaborationRatingService;
  private removeId: number = null;

  public collaborationRatings: ICollaborationRating[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCollaborationRatings();
  }

  public clear(): void {
    this.retrieveAllCollaborationRatings();
  }

  public retrieveAllCollaborationRatings(): void {
    this.isFetching = true;

    this.collaborationRatingService()
      .retrieve()
      .then(
        res => {
          this.collaborationRatings = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICollaborationRating): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCollaborationRating(): void {
    this.collaborationRatingService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.collaborationRating.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCollaborationRatings();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
