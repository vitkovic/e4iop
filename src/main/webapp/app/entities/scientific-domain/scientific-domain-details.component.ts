import { Component, Vue, Inject } from 'vue-property-decorator';

import { IScientificDomain } from '@/shared/model/scientific-domain.model';
import ScientificDomainService from './scientific-domain.service';

@Component
export default class ScientificDomainDetails extends Vue {
  @Inject('scientificDomainService') private scientificDomainService: () => ScientificDomainService;
  public scientificDomain: IScientificDomain = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.scientificDomainId) {
        vm.retrieveScientificDomain(to.params.scientificDomainId);
      }
    });
  }

  public retrieveScientificDomain(scientificDomainId) {
    this.scientificDomainService()
      .find(scientificDomainId)
      .then(res => {
        this.scientificDomain = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
