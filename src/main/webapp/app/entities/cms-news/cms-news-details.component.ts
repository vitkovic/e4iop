import { Component, Vue, Inject } from 'vue-property-decorator';
import { ICmsNews } from '@/shared/model/cms-news.model';
import CmsNewsService from './cms-news.service';

@Component
export default class CmsNewsDetails extends Vue {
  @Inject('cmsNewsService') private cmsNewsService: () => CmsNewsService;
  public cmsNews: ICmsNews = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsNewsId) {
        vm.retrieveCmsNews(to.params.cmsNewsId);
      }
    });
  }

  public retrieveCmsNews(cmsNewsId) {
    this.cmsNewsService()
      .find(cmsNewsId)
      .then(res => {
        this.cmsNews = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
