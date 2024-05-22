import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';
import PortalUserOrganizationService from './portal-user-organization.service';

@Component
export default class PortalUserOrganizationDetails extends Vue {
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  public portalUserOrganization: IPortalUserOrganization = {};

  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserOrganizationId) {
        vm.retrievePortalUserOrganization(to.params.portalUserOrganizationId);
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

  public retrievePortalUserOrganization(portalUserOrganizationId) {
    this.portalUserOrganizationService()
      .find(portalUserOrganizationId)
      .then(res => {
        this.portalUserOrganization = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
