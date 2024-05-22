import { Component, Vue, Inject } from 'vue-property-decorator';

import { IThread } from '@/shared/model/thread.model';
import ThreadService from './thread.service';

@Component
export default class ThreadDetails extends Vue {
  @Inject('threadService') private threadService: () => ThreadService;
  public thread: IThread = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.threadId) {
        vm.retrieveThread(to.params.threadId);
      }
    });
  }

  public retrieveThread(threadId) {
    this.threadService()
      .find(threadId)
      .then(res => {
        this.thread = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
