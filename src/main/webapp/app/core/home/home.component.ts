import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import ResearchInfrastructureService from '@/entities/research-infrastructure/research-infrastructure.service';
import RiServiceService from '@/entities/ri-service/ri-service.service';
import PortalUserService from '@/entities/portal-user/portal-user.service';
import PortalUserOrganizationService from '@/entities/portal-user-organization/portal-user-organization.service';

@Component
export default class Home extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  @Inject('riServiceService') private riServiceService: () => RiServiceService;
  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public header = 'Infrastrukture';
  public riServicesCount = 0;
  public researchInfrastructureCount = 0;
  public portalOrganizationsCount = 0;
  public portalUsersCount = 0;

  public currentLanguage = '';
  public services: any[] = [];

  public search = {
    searchTerms: '',
    currentLanguage: '',
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.search) {
        vm.search = to.params.search;
        vm.searchRiService();
      }
      vm.preparePage();
    });
  }

  public created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public searchRiService(): void {
    this.search.currentLanguage = this.currentLanguage;
    this.riServiceService()
      .searchAll(this.search)
      .then(
        res => {
          this.services = res.data;
        },
        err => {}
      );
  }

  public preparePage(): void {
    this.riServiceService()
      .countAll()
      .then(res => {
        this.riServicesCount = res;
      });

    this.researchInfrastructureService()
      .countAll()
      .then(res => {
        this.researchInfrastructureCount = res;
      });

    this.portalUserOrganizationService()
      .countAll()
      .then(res => {
        this.portalOrganizationsCount = res;
      });

    this.portalUserService()
      .countAll()
      .then(res => {
        this.portalUsersCount = res;
      });
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
