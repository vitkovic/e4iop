import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiAccessType } from '@/shared/model/ri-access-type.model';
import RiAccessTypeService from './ri-access-type.service';

@Component
export default class RiAccessTypeDetails extends Vue {
  @Inject('riAccessTypeService') private riAccessTypeService: () => RiAccessTypeService;
  public riAccessType: IRiAccessType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riAccessTypeId) {
        vm.retrieveRiAccessType(to.params.riAccessTypeId);
      }
    });
  }

  public retrieveRiAccessType(riAccessTypeId) {
    this.riAccessTypeService()
      .find(riAccessTypeId)
      .then(res => {
        this.riAccessType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
