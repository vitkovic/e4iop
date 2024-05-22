import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';
import RiResearchOrganizationService from './ri-research-organization.service';

@Component
export default class RiResearchOrganizationDetails extends Vue {
  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;
  public riResearchOrganization: IRiResearchOrganization = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riResearchOrganizationId) {
        vm.retrieveRiResearchOrganization(to.params.riResearchOrganizationId);
      }
    });
  }

  public retrieveRiResearchOrganization(riResearchOrganizationId) {
    this.riResearchOrganizationService()
      .find(riResearchOrganizationId)
      .then(res => {
        this.riResearchOrganization = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
