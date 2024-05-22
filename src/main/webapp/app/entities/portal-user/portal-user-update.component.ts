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
import { IPortalUserOrganization, PortalUserOrganization } from '@/shared/model/portal-user-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IPortalUser, PortalUser } from '@/shared/model/portal-user.model';
import PortalUserService from './portal-user.service';
import AccountService from '@/account/account.service';

import { IResearchInstitution, ResearchInstitution } from '@/shared/model/research-institution.model';

const loginPattern = helpers.regex('alpha', /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/);
const validations: any = {
  portalUser: {
    firstName: {},
    familyName: {},
    phone: {},
    position: {},
    user: {
      login: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(50),
        pattern: loginPattern,
      },
      email: {
        required,
        minLength: minLength(5),
        maxLength: maxLength(254),
        email,
      },
      /*
      password: {
        required,
        minLength: minLength(4),
        maxLength: maxLength(254),
      },
      */
      firstName: {
        required,
      },
      lastName: {
        required,
      },
    },
  },
  /*
  confirmPassword: {
    required,
    minLength: minLength(4),
    maxLength: maxLength(50),
    // prettier-ignore
    sameAsPassword: sameAs(function() {
      return this.portalUser.user.password;
    })
  },
  */
};

interface ResearcherSearchFormat {
  // Ovo mozda nije bas najbolje resenje.
  // Posto se sa modela istrazivaca u bazi prelazi na model pogodan za pretrazivanje,
  // da ovo nije uradjeno izgubila bi se veza izmedju dva modela.
  originalResearcher: IResearcher;

  fullName: string;
  fullNameReversed: string;
  firstName: string;
  middleName: string;
  lastName: string;
  birthDate: Date;
}

@Component({
  validations,
})
export default class PortalUserUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('accountService') private accountService: () => AccountService;

  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  public portalUser: IPortalUser = new PortalUser();
  public user: IUser = new User();

  @Inject('researcherService') private researcherService: () => ResearcherService;

  public researchers: IResearcher[] = [];

  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;

  public riResearchOrganizations: IRiResearchOrganization[] = [];

  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  public researchInfrastructures: IResearchInfrastructure[] = [];

  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;

  public requestForServices: IRequestForService[] = [];

  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;

  public riCalendars: IRiCalendar[] = [];

  @Inject('riProcurementProposalService') private riProcurementProposalService: () => RiProcurementProposalService;

  public riProcurementProposals: IRiProcurementProposal[] = [];

  @Inject('serviceProposalService') private serviceProposalService: () => ServiceProposalService;

  public serviceProposals: IServiceProposal[] = [];

  @Inject('portalUserRoleService') private portalUserRoleService: () => PortalUserRoleService;

  public portalUserRoles: IPortalUserRole[] = [];

  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;

  public researchInstitution: IResearchInstitution = new ResearchInstitution();
  public researchersWithFullNames: ResearcherSearchFormat[] = [];
  public search_data_researchers: ResearcherSearchFormat[] = [];
  private patternResearchers = false;
  public researchersFieldDisabled = false;

  public portalUserOrganizations: IPortalUserOrganization[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public confirmPassword: any = null;
  private hasAnyAuthorityValue = false;

  private search_data = [];
  private patternRI = false;
  public txtfield = null;
  public portalUserOrganization: IPortalUserOrganization = new PortalUserOrganization();

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserId) {
        vm.retrievePortalUser(to.params.portalUserId);
      } else {
        vm.portalUser.user = vm.user;
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
        .update(this.portalUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUser.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.portalUserService()
        .create(this.portalUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.portalUser.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePortalUser(portalUserId): void {
    this.portalUserService()
      .find(portalUserId)
      .then(res => {
        this.portalUser = res;
        // dodato zbog validacije, jer dugme sacuvaj nije aktivno zbog ovih polja
        this.portalUser.user.password = 'bbbb';
        this.confirmPassword = 'bbbb';
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    // this.researcherService()
    //   .retrieve()
    //   .then(res => {
    //     this.researchers = res.data;
    //   });
    this.riResearchOrganizationService()
      .retrieve()
      .then(res => {
        this.riResearchOrganizations = res.data;
      });
    this.riResearchOrganizationService()
      .retrieve()
      .then(res => {
        this.riResearchOrganizations = res.data;
      });
    this.researchInfrastructureService()
      .retrieve()
      .then(res => {
        this.researchInfrastructures = res.data;
      });
    this.researchInfrastructureService()
      .retrieve()
      .then(res => {
        this.researchInfrastructures = res.data;
      });
    this.requestForServiceService()
      .retrieve()
      .then(res => {
        this.requestForServices = res.data;
      });
    this.riCalendarService()
      .retrieve()
      .then(res => {
        this.riCalendars = res.data;
      });
    this.riProcurementProposalService()
      .retrieve()
      .then(res => {
        this.riProcurementProposals = res.data;
      });
    this.serviceProposalService()
      .retrieve()
      .then(res => {
        this.serviceProposals = res.data;
      });

    if (this.hasAnyRole(['RPRIPO'])) {
      this.portalUserRoleService()
        .find(1204)
        .then(res => {
          this.portalUserRoles[0] = res;
        });
    } else {
      this.portalUserRoleService()
        .retrieve()
        .then(res => {
          this.portalUserRoles = res.data;
        });
    }
    this.portalUserOrganizationService()
      .retrieve()
      .then(res => {
        this.portalUserOrganizations = res.data;
        this.search_data = this.portalUserOrganizations;
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

  public hasAnyRole(roles: any): boolean {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public getDataAutoForPUO(): void {
    this.patternRI = true;
    this.txtfield = this.$refs.portalUserOrganisationField as HTMLInputElement;

    if (this.txtfield.value != '') {
      this.portalUserOrganizationService()
        .retrieveAutocomplete(this.txtfield.value, this.currentLanguage)
        .then(res => {
          this.portalUserOrganizations = res.data;
          this.search_data = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      this.portalUserOrganizationService()
        .retrieveWithoutPagination()
        .then(res => {
          this.portalUserOrganizations = res.data;
          this.search_data = res.data;
        });
    }
  }

  getPUOs(data): void {
    this.txtfield = this.$refs.portalUserOrganisationField as HTMLInputElement;

    if (this.currentLanguage == 'sr') {
      this.txtfield.value = data.legalNameSr;
    } else if (this.currentLanguage == 'src') {
      this.txtfield.value = data.legalNameSrCyr;
    } else if (this.currentLanguage == 'en') {
      this.txtfield.value = data.legalNameEn;
    }

    this.portalUserOrganization = data;
    this.portalUser.userOrganization = this.portalUserOrganization;

    this.txtfield = this.$refs.researchersField as HTMLInputElement;
    if (this.portalUserOrganization.nioInstitution) {
      this.researchersFieldDisabled = false;
      this.findReseacrhersForResearchInstitution(this.portalUserOrganization.nioInstitution.id);
      this.txtfield.value = '';
    } else {
      this.researchersFieldDisabled = true;
      this.txtfield.value = this.$t('riportalApp.portalUser.researcherChoicePossibility');
    }
  }

  public toggleSearchList(event, toggleControl: string) {
    if (event.type === 'focusout') {
      this[toggleControl] = false;
    } else {
      this[toggleControl] = !this[toggleControl];
    }
  }

  public findReseacrhersForResearchInstitution(id) {
    this.researcherService()
      .retrieveOrdered(this.currentLanguage, id)
      .then(res => {
        this.researchers = res.data;
        this.createResearchersWithFullNames();
      })
      .catch(e => {
        console.log(e);
      });
  }

  public getResearchers(data): void {
    this.txtfield = this.$refs.researchersField as HTMLInputElement;
    this.txtfield.value = data.firstName + ' ';
    if (data.middleName) {
      this.txtfield.value += data.middleName + ' ';
    }
    this.txtfield.value += data.lastName;
    this.txtfield.value += ' - ' + data.birthDate;

    this.portalUser.user.firstName = data.firstName;
    this.portalUser.user.lastName = data.lastName;
    this.portalUser.researcher = data.originalResearcher;
  }

  public getDataAutoForResearchers(): void {
    this.patternResearchers = true;
    this.txtfield = this.$refs.researchersField as HTMLInputElement;

    if (this.txtfield.value != '') {
      // This search is brute force, could be smarter.
      this.search_data_researchers = [];
      for (var researcher of this.researchersWithFullNames) {
        if (
          researcher.fullName.includes(this.txtfield.value.toUpperCase()) ||
          researcher.fullNameReversed.includes(this.txtfield.value.toUpperCase())
        ) {
          this.search_data_researchers.push(researcher);
        }
      }
    } else {
      this.search_data_researchers = this.researchersWithFullNames;
    }
  }

  public createResearchersWithFullNames() {
    var fullName: string;
    var fullNameReversed: string;

    this.researchersWithFullNames = [];
    for (var researcher of this.researchers) {
      fullName = researcher.firstName + ' ';
      fullNameReversed = researcher.lastName + ' ';
      if (researcher.middleName) {
        fullName += researcher.middleName + ' ';
        fullNameReversed += researcher.middleName + ' ';
      }
      fullName += researcher.lastName;
      fullNameReversed += researcher.firstName;

      fullName = fullName.toUpperCase();
      fullNameReversed = fullNameReversed.toUpperCase();

      this.researchersWithFullNames.push({
        originalResearcher: researcher,
        fullName: fullName,
        fullNameReversed: fullNameReversed,
        firstName: researcher.firstName,
        middleName: researcher.middleName,
        lastName: researcher.lastName,
        birthDate: researcher.birthDate,
      });
    }

    this.search_data_researchers = this.researchersWithFullNames;
  }
}
