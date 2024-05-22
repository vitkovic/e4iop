import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CollaborationService from '../collaboration/collaboration.service';
import { ICollaboration } from '@/shared/model/collaboration.model';

import AlertService from '@/shared/alert/alert.service';
import { ICollaborationStatus, CollaborationStatus } from '@/shared/model/collaboration-status.model';
import CollaborationStatusService from './collaboration-status.service';

const validations: any = {
  collaborationStatus: {
    status: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class CollaborationStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;
  public collaborationStatus: ICollaborationStatus = new CollaborationStatus();

  @Inject('collaborationService') private collaborationService: () => CollaborationService;

  public collaborations: ICollaboration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collaborationStatusId) {
        vm.retrieveCollaborationStatus(to.params.collaborationStatusId);
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
    if (this.collaborationStatus.id) {
      this.collaborationStatusService()
        .update(this.collaborationStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.collaborationStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.collaborationStatusService()
        .create(this.collaborationStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.collaborationStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCollaborationStatus(collaborationStatusId): void {
    this.collaborationStatusService()
      .find(collaborationStatusId)
      .then(res => {
        this.collaborationStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.collaborationService()
      .retrieve()
      .then(res => {
        this.collaborations = res.data;
      });
  }
}
