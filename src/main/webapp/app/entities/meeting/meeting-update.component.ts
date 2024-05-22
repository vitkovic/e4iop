import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import MeetingParticipantService from '../meeting-participant/meeting-participant.service';
import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import MeetingTypeService from '../meeting-type/meeting-type.service';
import { IMeetingType } from '@/shared/model/meeting-type.model';

import AlertService from '@/shared/alert/alert.service';
import { IMeeting, Meeting } from '@/shared/model/meeting.model';
import MeetingService from './meeting.service';

const validations: any = {
  meeting: {
    datetime: {
      required,
    },
    isAcepted: {},
    title: {
      required,
    },
    description: {},
    comment: {},
    notes: {},
  },
};

@Component({
  validations,
})
export default class MeetingUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('meetingService') private meetingService: () => MeetingService;
  public meeting: IMeeting = new Meeting();

  @Inject('meetingParticipantService') private meetingParticipantService: () => MeetingParticipantService;

  public meetingParticipants: IMeetingParticipant[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];

  @Inject('meetingTypeService') private meetingTypeService: () => MeetingTypeService;

  public meetingTypes: IMeetingType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.meetingId) {
        vm.retrieveMeeting(to.params.meetingId);
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
    if (this.meeting.id) {
      this.meetingService()
        .update(this.meeting)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meeting.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.meetingService()
        .create(this.meeting)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.meeting.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.meeting[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.meeting[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.meeting[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.meeting[field] = null;
    }
  }

  public retrieveMeeting(meetingId): void {
    this.meetingService()
      .find(meetingId)
      .then(res => {
        res.datetime = new Date(res.datetime);
        this.meeting = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.meetingParticipantService()
      .retrieve()
      .then(res => {
        this.meetingParticipants = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
    this.meetingTypeService()
      .retrieve()
      .then(res => {
        this.meetingTypes = res.data;
      });
  }
}
