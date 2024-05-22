import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, minValue, maxValue } from 'vuelidate/lib/validators';
import { email, helpers, maxLength, minLength, required, sameAs } from 'vuelidate/lib/validators';
import ResearcherService from '../researcher/researcher.service';
import { IResearcher } from '@/shared/model/researcher.model';
import { IUser, User } from '@/shared/model/user.model';

import RiResearchOrganizationService from '../ri-research-organization/ri-research-organization.service';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';

import ResearchInfrastructureService from '../research-infrastructure/research-infrastructure.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

import RequestForServiceService from '../request-for-service/request-for-service.service';
import { IRequestForService } from '@/shared/model/request-for-service.model';

import RiCalendarService from '../ri-calendar/ri-calendar.service';
import { IRiCalendar } from '@/shared/model/ri-calendar.model';

import RiProcurementProposalService from '../ri-procurement-proposal/ri-procurement-proposal.service';
import { IRiProcurementProposal } from '@/shared/model/ri-procurement-proposal.model';

import ServiceProposalService from '../service-proposal/service-proposal.service';
import { IServiceProposal } from '@/shared/model/service-proposal.model';

import PortalUserRoleService from '../portal-user-role/portal-user-role.service';
import { IPortalUserRole } from '@/shared/model/portal-user-role.model';

import PortalUserOrganizationService from '../portal-user-organization/portal-user-organization.service';
import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IPortalUser, PortalUser } from '@/shared/model/portal-user.model';
import PortalUserService from './portal-user.service';

const loginPattern = helpers.regex('alpha', /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/);
const validations: any = {
  portalUser: {
    userOrganization: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ActivateUserRoles extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  public portalUser: IPortalUser = new PortalUser();
  public user: IUser = new User();

  @Inject('researcherService') private researcherService: () => ResearcherService;

  public researchers: IResearcher[] = [];

  @Inject('portalUserRoleService') private portalUserRoleService: () => PortalUserRoleService;

  public portalUserRoles: IPortalUserRole[] = [];

  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;

  public portalUserOrganizations: IPortalUserOrganization[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public confirmPassword: any = null;
  public checkboxText = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserId) {
        vm.retrievePortalUser(to.params.portalUserId);
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
    this.portalUser.portalUserRoles = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.portalUser.id) {
      this.portalUserService()
        .updateRoles(this.portalUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUser.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    }
  }

  public retrievePortalUser(portalUserId): void {
    this.portalUserService()
      .findForActivation(portalUserId)
      .then(res => {
        this.portalUser = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    const message = this.$t('riportalApp.portalUser.activateUserRoles.checkboxTitle');
    this.checkboxText = JSON.stringify(message);
    this.portalUserRoleService()
      .retrieve()
      .then(res => {
        this.portalUserRoles = res.data;
      });
    this.portalUserOrganizationService()
      .retrieve()
      .then(res => {
        this.portalUserOrganizations = res.data;
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
