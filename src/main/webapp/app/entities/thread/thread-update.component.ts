import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import MessageService from '../message/message.service';
import { IMessage } from '@/shared/model/message.model';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import AlertService from '@/shared/alert/alert.service';
import { IThread, Thread } from '@/shared/model/thread.model';
import ThreadService from './thread.service';

const validations: any = {
  thread: {
    subject: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ThreadUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('threadService') private threadService: () => ThreadService;
  public thread: IThread = new Thread();

  @Inject('messageService') private messageService: () => MessageService;

  public messages: IMessage[] = [];

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.threadId) {
        vm.retrieveThread(to.params.threadId);
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
    this.thread.advertisements = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.thread.id) {
      this.threadService()
        .update(this.thread)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.thread.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.threadService()
        .create(this.thread)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.thread.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveThread(threadId): void {
    this.threadService()
      .find(threadId)
      .then(res => {
        this.thread = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.messageService()
      .retrieve()
      .then(res => {
        this.messages = res.data;
      });
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
