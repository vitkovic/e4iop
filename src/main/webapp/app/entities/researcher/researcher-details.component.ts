import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResearcher } from '@/shared/model/researcher.model';
import ResearcherService from './researcher.service';

@Component
export default class ResearcherDetails extends Vue {
  @Inject('researcherService') private researcherService: () => ResearcherService;
  public researcher: IResearcher = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researcherId) {
        vm.retrieveResearcher(to.params.researcherId);
      }
    });
  }

  public retrieveResearcher(researcherId) {
    this.researcherService()
      .find(researcherId)
      .then(res => {
        this.researcher = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
