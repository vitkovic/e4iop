import { Component, Vue, Inject } from 'vue-property-decorator';
import { required } from 'vuelidate/lib/validators';
import MeetingParticipantStatusService from './meeting-participant-status.service';
import AlertService from '@/shared/alert/alert.service';
import { IMeetingParticipantStatus, MeetingParticipantStatus } from '@/shared/model/meeting-participant-status.model';

const validations: any = {
  meetingParticipantStatus: {
    statusSr: {
      required,
    },
    statusSrc: {
      required,
    },
    statusEn: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class MeetingParticipantStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('meetingParticipantStatusService') private meetingParticipantStatusService: () => MeetingParticipantStatusService;
  public meetingParticipantStatus: IMeetingParticipantStatus = new MeetingParticipantStatus();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingParticipantStatusId) {
        vm.retrieveMeetingParticipantStatus(to.params.meetingParticipantStatusId);
      }
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
    if (this.meetingParticipantStatus.id) {
      this.meetingParticipantStatusService()
        .update(this.meetingParticipantStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meetingParticipantStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.meetingParticipantStatusService()
        .create(this.meetingParticipantStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meetingParticipantStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMeetingParticipantStatus(meetingParticipantStatusId): void {
    this.meetingParticipantStatusService()
      .find(meetingParticipantStatusId)
      .then(res => {
        this.meetingParticipantStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
