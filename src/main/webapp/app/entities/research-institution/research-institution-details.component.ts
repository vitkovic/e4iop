import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResearchInstitution } from '@/shared/model/research-institution.model';
import ResearchInstitutionService from './research-institution.service';

@Component
export default class ResearchInstitutionDetails extends Vue {
  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;
  public researchInstitution: IResearchInstitution = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researchInstitutionId) {
        vm.retrieveResearchInstitution(to.params.researchInstitutionId);
      }
    });
  }

  public retrieveResearchInstitution(researchInstitutionId) {
    this.researchInstitutionService()
      .find(researchInstitutionId)
      .then(res => {
        this.researchInstitution = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
