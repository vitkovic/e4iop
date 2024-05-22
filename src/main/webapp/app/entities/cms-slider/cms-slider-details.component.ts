import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsSlider } from '@/shared/model/cms-slider.model';
import CmsSliderService from './cms-slider.service';

@Component
export default class CmsSliderDetails extends Vue {
  @Inject('cmsSliderService') private cmsSliderService: () => CmsSliderService;
  public cmsSlider: ICmsSlider = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsSliderId) {
        vm.retrieveCmsSlider(to.params.cmsSliderId);
      }
    });
  }

  public retrieveCmsSlider(cmsSliderId) {
    this.cmsSliderService()
      .find(cmsSliderId)
      .then(res => {
        this.cmsSlider = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
