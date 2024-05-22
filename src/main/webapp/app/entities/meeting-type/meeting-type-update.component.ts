import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import MeetingService from '../meeting/meeting.service';
import { IMeeting } from '@/shared/model/meeting.model';

import AlertService from '@/shared/alert/alert.service';
import { IMeetingType, MeetingType } from '@/shared/model/meeting-type.model';
import MeetingTypeService from './meeting-type.service';

const validations: any = {
  meetingType: {
    type: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class MeetingTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('meetingTypeService') private meetingTypeService: () => MeetingTypeService;
  public meetingType: IMeetingType = new MeetingType();

  @Inject('meetingService') private meetingService: () => MeetingService;

  public meetings: IMeeting[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingTypeId) {
        vm.retrieveMeetingType(to.params.meetingTypeId);
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
    if (this.meetingType.id) {
      this.meetingTypeService()
        .update(this.meetingType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meetingType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.meetingTypeService()
        .create(this.meetingType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meetingType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMeetingType(meetingTypeId): void {
    this.meetingTypeService()
      .find(meetingTypeId)
      .then(res => {
        this.meetingType = res;
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
  }
}
