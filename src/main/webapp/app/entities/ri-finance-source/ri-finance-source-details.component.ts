import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiFinanceSource } from '@/shared/model/ri-finance-source.model';
import RiFinanceSourceService from './ri-finance-source.service';

@Component
export default class RiFinanceSourceDetails extends Vue {
  @Inject('riFinanceSourceService') private riFinanceSourceService: () => RiFinanceSourceService;
  public riFinanceSource: IRiFinanceSource = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riFinanceSourceId) {
        vm.retrieveRiFinanceSource(to.params.riFinanceSourceId);
      }
    });
  }

  public retrieveRiFinanceSource(riFinanceSourceId) {
    this.riFinanceSourceService()
      .find(riFinanceSourceId)
      .then(res => {
        this.riFinanceSource = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
