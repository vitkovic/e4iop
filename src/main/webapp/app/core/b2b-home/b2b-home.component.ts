import Component from 'vue-class-component';
// import Home from './home.vue'
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';

@Component
export default class B2BHome extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

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
