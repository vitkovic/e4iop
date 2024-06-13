import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';

import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

import MeetingParticipantService from '@/entities/meeting-participant/meeting-participant.service';

@Component
export default class Activate extends Vue {
  @Inject('meetingParticipantService')
  private meetingParticipantService: () => MeetingParticipantService;

  public meetingParticipant: IMeetingParticipant | null = null;
  public meetingId: number | null = null;
  public companyId: number | null = null;
  public message = '';

  success = false;
  error = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.query.meetingId && to.query.companyId) {
        vm.meetingId = to.query.meetingId;
        vm.companyId = to.query.companyId;

        vm.acceptMeeting();
      }
    });
  }

  public acceptMeeting(): void {
    this.meetingParticipantService()
      .acceptMeetingForCompany(this.meetingId, this.companyId)
      .then(res => {
        this.success = true;

        this.meetingParticipant = res;
        this.message = 'Uspešno ste prihvatili poziv za sastanak ';
      })
      .catch(error => {
        this.error = true;

        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            this.message = 'Sistemska greška, nemate poziv za ovaj sastanak';
          } else if (status === 400) {
            this.message = 'Poziv za ovaj sastanak je već prihvaćen';
          } else {
            this.message = error.response.data;
          }
        } else {
          console.error('Greska!', error.message);
        }
      });
  }

  public init(key: string): void {}
}
