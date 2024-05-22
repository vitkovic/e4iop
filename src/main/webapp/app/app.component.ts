import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import B2BJhiFooter from '@/core/b2b-jhi-footer/b2b-jhi-footer.vue';
import B2BJhiNavbar from '@/core/b2b-jhi-navbar/b2b-jhi-navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';

@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'jhi-footer': JhiFooter,
    'b2b-jhi-navbar': B2BJhiNavbar,
    'b2b-jhi-footer': B2BJhiFooter,
    'login-form': LoginForm,
  },
})
export default class App extends Vue {
  public isB2BDomain = false;

  public checkB2Bdomain(): boolean {
    const currentUrl: string = window.location.pathname;
    const isB2BDomain = currentUrl.startsWith('/b2b');

    // console.log(currentUrl);
    // console.log(isB2BDomain);

    return isB2BDomain;
  }
}
