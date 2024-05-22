import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMeetingType } from '@/shared/model/meeting-type.model';
import MeetingTypeService from './meeting-type.service';

@Component
export default class MeetingTypeDetails extends Vue {
  @Inject('meetingTypeService') private meetingTypeService: () => MeetingTypeService;
  public meetingType: IMeetingType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingTypeId) {
        vm.retrieveMeetingType(to.params.meetingTypeId);
      }
    });
  }

  public retrieveMeetingType(meetingTypeId) {
    this.meetingTypeService()
      .find(meetingTypeId)
      .then(res => {
        this.meetingType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
