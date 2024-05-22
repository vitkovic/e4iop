import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsQuestion } from '@/shared/model/cms-question.model';
import CmsQuestionService from './cms-question.service';

@Component
export default class CmsQuestionDetails extends Vue {
  @Inject('cmsQuestionService') private cmsQuestionService: () => CmsQuestionService;
  public cmsQuestion: ICmsQuestion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsQuestionId) {
        vm.retrieveCmsQuestion(to.params.cmsQuestionId);
      }
    });
  }

  public retrieveCmsQuestion(cmsQuestionId) {
    this.cmsQuestionService()
      .find(cmsQuestionId)
      .then(res => {
        this.cmsQuestion = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
