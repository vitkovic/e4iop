import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IPortalUserStatus, PortalUserStatus } from '@/shared/model/portal-user-status.model';
import PortalUserStatusService from './portal-user-status.service';

const validations: any = {
  portalUserStatus: {
    valueSr: {},
    valueEn: {},
    valueSrCyr: {},
  },
};

@Component({
  validations,
})
export default class PortalUserStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('portalUserStatusService') private portalUserStatusService: () => PortalUserStatusService;
  public portalUserStatus: IPortalUserStatus = new PortalUserStatus();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserStatusId) {
        vm.retrieveportalUserStatus(to.params.portalUserStatusId);
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
    if (this.portalUserStatus.id) {
      this.portalUserStatusService()
        .update(this.portalUserStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUserStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.portalUserStatusService()
        .create(this.portalUserStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUserStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveportalUserStatus(portalUserStatusId): void {
    this.portalUserStatusService()
      .find(portalUserStatusId)
      .then(res => {
        this.portalUserStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
  }
}
