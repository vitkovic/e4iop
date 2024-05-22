import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMeetingType } from '@/shared/model/meeting-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import MeetingTypeService from './meeting-type.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MeetingType extends mixins(AlertMixin) {
  @Inject('meetingTypeService') private meetingTypeService: () => MeetingTypeService;
  private removeId: number = null;

  public meetingTypes: IMeetingType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMeetingTypes();
  }

  public clear(): void {
    this.retrieveAllMeetingTypes();
  }

  public retrieveAllMeetingTypes(): void {
    this.isFetching = true;

    this.meetingTypeService()
      .retrieve()
      .then(
        res => {
          this.meetingTypes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMeetingType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMeetingType(): void {
    this.meetingTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.meetingType.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllMeetingTypes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
