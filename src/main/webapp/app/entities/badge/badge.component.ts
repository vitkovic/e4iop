import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBadge } from '@/shared/model/badge.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import BadgeService from './badge.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Badge extends mixins(AlertMixin) {
  @Inject('badgeService') private badgeService: () => BadgeService;
  private removeId: number = null;

  public badges: IBadge[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBadges();
  }

  public clear(): void {
    this.retrieveAllBadges();
  }

  public retrieveAllBadges(): void {
    this.isFetching = true;

    this.badgeService()
      .retrieve()
      .then(
        res => {
          this.badges = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBadge): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBadge(): void {
    this.badgeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.badge.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllBadges();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
