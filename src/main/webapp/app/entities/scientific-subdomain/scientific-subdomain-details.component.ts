import { Component, Vue, Inject } from 'vue-property-decorator';

import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';
import ScientificSubdomainService from './scientific-subdomain.service';

@Component
export default class ScientificSubdomainDetails extends Vue {
  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;
  public scientificSubdomain: IScientificSubdomain = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.scientificSubdomainId) {
        vm.retrieveScientificSubdomain(to.params.scientificSubdomainId);
      }
    });
  }

  public retrieveScientificSubdomain(scientificSubdomainId) {
    this.scientificSubdomainService()
      .find(scientificSubdomainId)
      .then(res => {
        this.scientificSubdomain = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
