import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMeetingParticipantStatus } from '@/shared/model/meeting-participant-status.model';
import MeetingParticipantStatusService from './meeting-participant-status.service';

@Component
export default class MeetingParticipantStatusDetails extends Vue {
  @Inject('meetingParticipantStatusService') private meetingParticipantStatusService: () => MeetingParticipantStatusService;
  public meetingParticipantStatus: IMeetingParticipantStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingParticipantStatusId) {
        vm.retrieveMeetingParticipantStatus(to.params.meetingParticipantStatusId);
      }
    });
  }

  public retrieveMeetingParticipantStatus(meetingParticipantStatusId) {
    this.meetingParticipantStatusService()
      .find(meetingParticipantStatusId)
      .then(res => {
        this.meetingParticipantStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
