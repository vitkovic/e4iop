import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IPortalUserRole, PortalUserRole } from '@/shared/model/portal-user-role.model';
import PortalUserRoleService from './portal-user-role.service';

const validations: any = {
  portalUserRole: {
    role: {},
    abbreviation: {},
    fullName: {},
  },
};

@Component({
  validations,
})
export default class PortalUserRoleUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('portalUserRoleService') private portalUserRoleService: () => PortalUserRoleService;
  public portalUserRole: IPortalUserRole = new PortalUserRole();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserRoleId) {
        vm.retrievePortalUserRole(to.params.portalUserRoleId);
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
    if (this.portalUserRole.id) {
      this.portalUserRoleService()
        .update(this.portalUserRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUserRole.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.portalUserRoleService()
        .create(this.portalUserRole)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUserRole.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePortalUserRole(portalUserRoleId): void {
    this.portalUserRoleService()
      .find(portalUserRoleId)
      .then(res => {
        this.portalUserRole = res;
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
