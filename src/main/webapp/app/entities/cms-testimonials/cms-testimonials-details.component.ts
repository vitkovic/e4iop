import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsTestimonials } from '@/shared/model/cms-testimonials.model';
import CmsTestimonialsService from './cms-testimonials.service';

@Component
export default class CmsTestimonialsDetails extends Vue {
  @Inject('cmsTestimonialsService') private cmsTestimonialsService: () => CmsTestimonialsService;
  public cmsTestimonials: ICmsTestimonials = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsTestimonialsId) {
        vm.retrieveCmsTestimonials(to.params.cmsTestimonialsId);
      }
    });
  }

  public retrieveCmsTestimonials(cmsTestimonialsId) {
    this.cmsTestimonialsService()
      .find(cmsTestimonialsId)
      .then(res => {
        this.cmsTestimonials = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
