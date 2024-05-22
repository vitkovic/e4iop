import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';
import MeetingParticipantService from './meeting-participant.service';

@Component
export default class MeetingParticipantDetails extends Vue {
  @Inject('meetingParticipantService') private meetingParticipantService: () => MeetingParticipantService;
  public meetingParticipant: IMeetingParticipant = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingParticipantId) {
        vm.retrieveMeetingParticipant(to.params.meetingParticipantId);
      }
    });
  }

  public retrieveMeetingParticipant(meetingParticipantId) {
    this.meetingParticipantService()
      .find(meetingParticipantId)
      .then(res => {
        this.meetingParticipant = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
