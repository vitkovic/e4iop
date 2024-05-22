import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import MeetingParticipantService from './meeting-participant.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MeetingParticipant extends mixins(AlertMixin) {
  @Inject('meetingParticipantService') private meetingParticipantService: () => MeetingParticipantService;
  private removeId: number = null;

  public meetingParticipants: IMeetingParticipant[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMeetingParticipants();
  }

  public clear(): void {
    this.retrieveAllMeetingParticipants();
  }

  public retrieveAllMeetingParticipants(): void {
    this.isFetching = true;

    this.meetingParticipantService()
      .retrieve()
      .then(
        res => {
          this.meetingParticipants = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMeetingParticipant): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMeetingParticipant(): void {
    this.meetingParticipantService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.meetingParticipant.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllMeetingParticipants();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
