import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMeetingParticipantStatus } from '@/shared/model/meeting-participant-status.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import MeetingParticipantStatusService from './meeting-participant-status.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MeetingParticipantStatus extends mixins(AlertMixin) {
  @Inject('meetingParticipantStatusService') private meetingParticipantStatusService: () => MeetingParticipantStatusService;
  private removeId: number = null;

  public meetingParticipantStatuses: IMeetingParticipantStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMeetingParticipantStatuses();
  }

  public clear(): void {
    this.retrieveAllMeetingParticipantStatuses();
  }

  public retrieveAllMeetingParticipantStatuses(): void {
    this.isFetching = true;

    this.meetingParticipantStatusService()
      .retrieve()
      .then(
        res => {
          this.meetingParticipantStatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMeetingParticipantStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMeetingParticipantStatus(): void {
    this.meetingParticipantStatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.meetingParticipantStatus.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllMeetingParticipantStatuses();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
