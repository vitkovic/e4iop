import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiService } from '@/shared/model/ri-service.model';
import RiServiceService from './ri-service.service';
import UploadFilesService from '@/utility/upload/upload_service';
@Component
export default class RiServiceDetails extends Vue {
  @Inject('riServiceService') private riServiceService: () => RiServiceService;
  public riService: IRiService = {};
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riServiceId) {
        vm.retrieveRiService(to.params.riServiceId);
      }
    });
  }
  formLink(link): void {
    UploadFilesService.getFile(link);
  }
  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }
  public retrieveRiService(riServiceId) {
    this.riServiceService()
      .find(riServiceId)
      .then(res => {
        this.riService = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
