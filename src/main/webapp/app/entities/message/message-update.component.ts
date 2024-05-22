import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import ThreadService from '../thread/thread.service';
import { IThread } from '@/shared/model/thread.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IMessage, Message } from '@/shared/model/message.model';
import MessageService from './message.service';

const validations: any = {
  message: {
    datetime: {},
    content: {
      required,
    },
    isRead: {},
    isDeletedSender: {},
    isDeletedReceiver: {},
  },
};

@Component({
  validations,
})
export default class MessageUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('messageService') private messageService: () => MessageService;
  public message: IMessage = new Message();

  @Inject('threadService') private threadService: () => ThreadService;

  public threads: IThread[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageId) {
        vm.retrieveMessage(to.params.messageId);
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
    if (this.message.id) {
      this.messageService()
        .update(this.message)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.message.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.message.datetime = new Date();
      this.messageService()
        .create(this.message)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.message.created', { param: param.id });
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
      this.message[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.message[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.message[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.message[field] = null;
    }
  }

  public retrieveMessage(messageId): void {
    this.messageService()
      .find(messageId)
      .then(res => {
        res.datetime = new Date(res.datetime);
        this.message = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.threadService()
      .retrieve()
      .then(res => {
        this.threads = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
  }
}
