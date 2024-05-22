import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICollaborationStatus } from '@/shared/model/collaboration-status.model';
import CollaborationStatusService from './collaboration-status.service';

@Component
export default class CollaborationStatusDetails extends Vue {
  @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;
  public collaborationStatus: ICollaborationStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collaborationStatusId) {
        vm.retrieveCollaborationStatus(to.params.collaborationStatusId);
      }
    });
  }

  public retrieveCollaborationStatus(collaborationStatusId) {
    this.collaborationStatusService()
      .find(collaborationStatusId)
      .then(res => {
        this.collaborationStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
