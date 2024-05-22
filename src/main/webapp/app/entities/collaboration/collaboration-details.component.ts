import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICollaboration } from '@/shared/model/collaboration.model';
import CollaborationService from './collaboration.service';

@Component
export default class CollaborationDetails extends Vue {
  @Inject('collaborationService') private collaborationService: () => CollaborationService;
  public collaboration: ICollaboration = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collaborationId) {
        vm.retrieveCollaboration(to.params.collaborationId);
      }
    });
  }

  public retrieveCollaboration(collaborationId) {
    this.collaborationService()
      .find(collaborationId)
      .then(res => {
        this.collaboration = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
