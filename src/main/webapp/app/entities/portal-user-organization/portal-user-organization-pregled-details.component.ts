import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';
import PortalUserOrganizationService from './portal-user-organization.service';

@Component
export default class PortalUserOrganizationPregledDetails extends Vue {
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  public portalUserOrganization: IPortalUserOrganization = {};
  public currentLanguage = '';
  private pathInformation = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.portalUserOrganizationId) {
        vm.retrievePortalUserOrganization(to.params.portalUserOrganizationId);
      }
      if (to.params.pathInformation) {
        vm.pathInformation = to.params.pathInformation;
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
      .findPregled(portalUserOrganizationId)
      .then(res => {
        this.portalUserOrganization = res;
      });
  }

  public previousState() {
    if (this.pathInformation) {
      this.$router.push({ name: this.pathInformation.path, params: { search: this.pathInformation.search } });
    } else this.$router.go(-1);
  }
}
