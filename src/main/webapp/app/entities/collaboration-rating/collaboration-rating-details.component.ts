import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICollaborationRating } from '@/shared/model/collaboration-rating.model';
import CollaborationRatingService from './collaboration-rating.service';

@Component
export default class CollaborationRatingDetails extends Vue {
  @Inject('collaborationRatingService') private collaborationRatingService: () => CollaborationRatingService;
  public collaborationRating: ICollaborationRating = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collaborationRatingId) {
        vm.retrieveCollaborationRating(to.params.collaborationRatingId);
      }
    });
  }

  public retrieveCollaborationRating(collaborationRatingId) {
    this.collaborationRatingService()
      .find(collaborationRatingId)
      .then(res => {
        this.collaborationRating = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
