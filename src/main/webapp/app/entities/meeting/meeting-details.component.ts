import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMeeting } from '@/shared/model/meeting.model';
import MeetingService from './meeting.service';

@Component
export default class MeetingDetails extends Vue {
  @Inject('meetingService') private meetingService: () => MeetingService;
  public meeting: IMeeting = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingId) {
        vm.retrieveMeeting(to.params.meetingId);
      }
    });
  }

  public retrieveMeeting(meetingId) {
    this.meetingService()
      .find(meetingId)
      .then(res => {
        this.meeting = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
