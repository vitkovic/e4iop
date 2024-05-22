import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMeeting } from '@/shared/model/meeting.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import MeetingService from './meeting.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Meeting extends mixins(AlertMixin) {
  @Inject('meetingService') private meetingService: () => MeetingService;
  private removeId: number = null;

  public meetings: IMeeting[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMeetings();
  }

  public clear(): void {
    this.retrieveAllMeetings();
  }

  public retrieveAllMeetings(): void {
    this.isFetching = true;

    this.meetingService()
      .retrieve()
      .then(
        res => {
          this.meetings = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMeeting): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMeeting(): void {
    this.meetingService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.meeting.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllMeetings();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
