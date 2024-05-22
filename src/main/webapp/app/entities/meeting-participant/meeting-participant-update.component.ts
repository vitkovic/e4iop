import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import MeetingService from '../meeting/meeting.service';
import { IMeeting } from '@/shared/model/meeting.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IMeetingParticipant, MeetingParticipant } from '@/shared/model/meeting-participant.model';
import MeetingParticipantService from './meeting-participant.service';

const validations: any = {
  meetingParticipant: {
    hasAccepted: {},
  },
};

@Component({
  validations,
})
export default class MeetingParticipantUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('meetingParticipantService') private meetingParticipantService: () => MeetingParticipantService;
  public meetingParticipant: IMeetingParticipant = new MeetingParticipant();

  @Inject('meetingService') private meetingService: () => MeetingService;

  public meetings: IMeeting[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingParticipantId) {
        vm.retrieveMeetingParticipant(to.params.meetingParticipantId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.meetingParticipant.id) {
      this.meetingParticipantService()
        .update(this.meetingParticipant)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meetingParticipant.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.meetingParticipantService()
        .create(this.meetingParticipant)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meetingParticipant.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMeetingParticipant(meetingParticipantId): void {
    this.meetingParticipantService()
      .find(meetingParticipantId)
      .then(res => {
        this.meetingParticipant = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.meetingService()
      .retrieve()
      .then(res => {
        this.meetings = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
  }
}
