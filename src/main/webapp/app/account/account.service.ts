import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';
import TranslationService from '@/locale/translation.service';

const apiInviteB2BUser = 'api/account/invite';

export default class AccountService {
  constructor(private store: Store<any>, private cookie: any, private translationService: TranslationService, private router: VueRouter) {
    this.init();
  }

  public init(): void {
    this.retrieveProfiles();
  }

  public retrieveProfiles(): void {
    axios.get('management/info').then(res => {
      if (res.data && res.data.activeProfiles) {
        this.store.commit('setRibbonOnProfiles', res.data['display-ribbon-on-profiles']);
        this.store.commit('setActiveProfiles', res.data['activeProfiles']);
      }
    });
  }

  public retrieveAccount(): Promise<boolean> {
    console.log(
      'JS U retrieveAccount ULAZ*****************************************************************************************************'
    );
    return new Promise(resolve => {
      axios
        .get('api/account')
        .then(response => {
          this.store.commit('authenticate');
          const account = response.data;
          console.log(
            'JS  U retrieveAccount U METODI *****************************************************************************************************'
          );
          console.log(account);
          if (account) {
            this.store.commit('authenticated', account);

            console.log(
              'JS  U retrieveAccount U METODI - AUTHENTICATED *****************************************************************************************************'
            );

            if (this.store.getters.currentLanguage !== account.langKey) {
              this.store.commit('currentLanguage', account.langKey);
            }
            if (sessionStorage.getItem('requested-url')) {
              this.router.replace(sessionStorage.getItem('requested-url'));
              sessionStorage.removeItem('requested-url');
            }
          } else {
            this.store.commit('logout');
            this.router.push('/');
            sessionStorage.removeItem('requested-url');
          }
          this.translationService.refreshTranslation(this.store.getters.currentLanguage);
          resolve(true);
        })
        .catch(() => {
          this.store.commit('logout');
          resolve(false);
        });
    });
  }

  public async hasAnyAuthorityAndCheckAuth(authorities: any): Promise<boolean> {
    if (typeof authorities === 'string') {
      authorities = [authorities];
    }

    if (!this.authenticated || !this.userAuthorities) {
      const token = this.cookie.get('JSESSIONID') || this.cookie.get('XSRF-TOKEN');
      //  const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
      if (!this.store.getters.account && !this.store.getters.logon && token) {
        // THE FOLLOWING CHANGE ADDRESSES THE DEFAULT JHIPSTER CODE!!!

        // The return statement bellow can return "true", thus granting any authority
        // that is being checked, even if the user doesn't have that authority.
        // This is especially the problem if we check for ROLE_ADMIN authority.

        // To avoid this, we first wait for the the "retrieveAccount" method.
        // If the account is not retrieved, "false" is returned for authority check.
        // If the account is retrieved we simply continue with authority check.
        // To be able to await for "retrieveAccount", this method is signed with "async";

        // return this.retrieveAccount();
        const accountRetrieved = await this.retrieveAccount();
        if (!accountRetrieved) {
          return false;
        }
      } else {
        return new Promise(resolve => {
          resolve(false);
        });
      }
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.userAuthorities.includes(authorities[i])) {
        return new Promise(resolve => {
          resolve(true);
        });
      }
    }

    return new Promise(resolve => {
      resolve(false);
    });
  }

  public get authenticated(): boolean {
    return this.store.getters.authenticated;
  }

  public get userAuthorities(): any {
    return this.store.getters.account.authorities;
  }

  public inviteB2BUser(entity: FormData): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(apiInviteB2BUser, entity, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
