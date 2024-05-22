import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsCarousel } from '@/shared/model/cms-carousel.model';
import CmsCarouselService from './cms-carousel.service';

@Component
export default class CmsCarouselDetails extends Vue {
  @Inject('cmsCarouselService') private cmsCarouselService: () => CmsCarouselService;
  public cmsCarousel: ICmsCarousel = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsCarouselId) {
        vm.retrieveCmsCarousel(to.params.cmsCarouselId);
      }
    });
  }

  public retrieveCmsCarousel(cmsCarouselId) {
    this.cmsCarouselService()
      .find(cmsCarouselId)
      .then(res => {
        this.cmsCarousel = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
