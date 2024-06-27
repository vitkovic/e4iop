import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import AdvertisementCategoryService from '../../entities/advertisement-category/advertisement-category.service';
import AdvertisementCategory from '../../entities/advertisement/advertisement.service';
import { IPortalUser } from '@/shared/model/portal-user.model';
import AdvertisementService from '../../entities/advertisement/advertisement.service';

@Component
export default class B2BJhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;

  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  
  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;

  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValue = false;
  private isPrviNavVisible = true;
  private mainSearchCategory = null;
  public portalUser: IPortalUser = null;
  public companyId: number;
  public companyLink = '';
  private advCategList = null;
  private txtsearch;

  public isActive: boolean = false;

 beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initRelationships();
    });
  }
  
  created() {
	
	
    this.translationService().refreshTranslation(this.currentLanguage);
   
  }
  
  data() {
      return {
        txtsearch: '',
      }
    }
  
  
  mounted() {
	this.initRelationships();
    // Call the function to check screen size and update isActive
    this.checkScreenSize();
    // Add event listener to track window resize
    window.addEventListener('resize', this.checkScreenSize);
  }

  beforeDestroy() {
    // Remove event listener when component is destroyed
    window.removeEventListener('resize', this.checkScreenSize);
  }

  public checkScreenSize() {
    this.isActive = window.innerWidth < 786;
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  
  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    this.$router.push('/');
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    this.getCompanyLink();
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get swaggerEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('swagger') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }

  public getCompanyLink(): string {
    const user = this.$store.getters.account;
    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          if (this.portalUser?.company) {
            this.companyLink = '/company/' + this.portalUser.company.id + '/view';
          }
        });
    }

    return this.companyLink;
  }
  
  private advList;
  public searchAdv(): void {
	//  console.log(this.searchinput);
	 this.advertisementService()
      .retrieveSearch("test")
      .then(res => {
        console.log(res.data);
        this.advList = res.data;
      });  
  }

  private searchinput;
  public initRelationships(): void {
    this.advertisementCategoryService()
      .retrieve()
      .then(res => {
        console.log(res.data);
        this.advCategList = res.data;
        this.$refs.mainSearchCategory = this.advCategList;
        
      });

   
  }
  
 
  
}
