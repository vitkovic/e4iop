import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';

import PortalUserService from '@/entities/portal-user/portal-user.service';

//b2b
import { IPortalUser } from '@/shared/model/portal-user.model';

@Component
export default class JhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValue = false;
  private hasAnyRoleValue = false;

  private isPrviNavVisible = true;
  public portalUser: IPortalUser = null;
  public companyId: number;
  public companyLink = '';

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);

    this.portalUserService()
      .getRoles()
      .then(value => {
        return;
      });
  }

  // moj kod

  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  }

  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  }

  private handleScroll() {
    this.isPrviNavVisible = window.scrollY < window.innerHeight * 0.3;
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
/**
  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    this.$router.push('/');
  }
**/
public logout(): void {
    this.loginService()
      .logout()
      .then(response => {
        this.$store.commit('logout');
        this.$router.push('/');
        const data = response.data;
        let logoutUrl = data.logoutUrl;
        // if Keycloak, uri has protocol/openid-connect/token
        if (logoutUrl.indexOf('/protocol') > -1) {
          logoutUrl = logoutUrl + '?redirect_uri=' + window.location.origin;
        } else {
          // Okta
          logoutUrl = logoutUrl + '?id_token_hint=' + data.idToken + '&post_logout_redirect_uri=' + window.location.origin;
        }
        window.location.href = logoutUrl;
      });
  }

  public openLogin(): void {
    //this.loginService().openLogin((<any>this).$root); old for jwt auth.
    this.loginService().login();
  }

  public get authenticated(): boolean {
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

  public checkRoles(rolesFromServer: any, roles: any): boolean {
    for (let i = 0; i < rolesFromServer.length; i++) {
      for (let j = 0; j < roles.length; j++) {
        if (rolesFromServer[i] == roles[j]) {
          return true;
        }
      }
    }
    return false;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get swaggerEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('swagger') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }

  //ivin kod
  public get settingsAccount(): any {
    return this.$store.getters.account;
  }

  public getUserInitials(): string {
    const account = this.settingsAccount;
    if (account && account.firstName && account.lastName) {
      const initials = `${account.firstName.charAt(0)}${account.lastName.charAt(0)}`.toUpperCase();
      return initials;
    }
    return ''; // Ukoliko nisu dostupni podaci o imenu i prezimenu korisnika
  }

  public getCompanyLink(): string {
    const user = this.$store.getters.account;
    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          if (this.portalUser.company) {
            this.companyLink = '/company/' + this.portalUser.company.id + '/view';
          }
          // console.log(this.companyLink);
        });
    }

    return this.companyLink;
  }
}
