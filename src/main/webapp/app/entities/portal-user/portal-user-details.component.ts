import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPortalUser } from '@/shared/model/portal-user.model';
import PortalUserService from './portal-user.service';

@Component
export default class PortalUserDetails extends Vue {
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  public portalUser: IPortalUser = {};
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserId) {
        vm.retrievePortalUser(to.params.portalUserId);
      }
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

  public retrievePortalUser(portalUserId) {
    this.portalUserService()
      .find(portalUserId)
      .then(res => {
        this.portalUser = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
