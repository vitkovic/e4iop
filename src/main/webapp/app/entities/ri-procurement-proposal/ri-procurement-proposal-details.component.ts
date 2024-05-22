import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiProcurementProposal } from '@/shared/model/ri-procurement-proposal.model';
import RiProcurementProposalService from './ri-procurement-proposal.service';

@Component
export default class RiProcurementProposalDetails extends Vue {
  @Inject('riProcurementProposalService') private riProcurementProposalService: () => RiProcurementProposalService;
  public riProcurementProposal: IRiProcurementProposal = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riProcurementProposalId) {
        vm.retrieveRiProcurementProposal(to.params.riProcurementProposalId);
      }
    });
  }

  public retrieveRiProcurementProposal(riProcurementProposalId) {
    this.riProcurementProposalService()
      .find(riProcurementProposalId)
      .then(res => {
        this.riProcurementProposal = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
