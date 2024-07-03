import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';

import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

import MeetingParticipantService from '@/entities/meeting-participant/meeting-participant.service';

@Component
export default class CMSB2B extends Vue {
  @Inject('meetingParticipantService')
  private meetingParticipantService: () => MeetingParticipantService;
}
