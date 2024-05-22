import { Component, Vue, Inject } from 'vue-property-decorator';

import { IServiceProposal } from '@/shared/model/service-proposal.model';
import ServiceProposalService from './service-proposal.service';

@Component
export default class ServiceProposalDetails extends Vue {
  @Inject('serviceProposalService') private serviceProposalService: () => ServiceProposalService;
  public serviceProposal: IServiceProposal = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceProposalId) {
        vm.retrieveServiceProposal(to.params.serviceProposalId);
      }
    });
  }

  public retrieveServiceProposal(serviceProposalId) {
    this.serviceProposalService()
      .find(serviceProposalId)
      .then(res => {
        this.serviceProposal = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
