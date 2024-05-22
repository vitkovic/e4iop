import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiService } from '@/shared/model/ri-service.model';
import RiServiceService from './ri-service.service';
import UploadFilesService from '@/utility/upload/upload_service';
@Component
export default class RiServicePregledDetails extends Vue {
  @Inject('riServiceService') private riServiceService: () => RiServiceService;
  public riService: IRiService = {};
  public currentLanguage = '';
  private pathInformation = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riServiceId) {
        vm.retrieveRiService(to.params.riServiceId);
      }
      if (to.params.pathInformation) {
        vm.pathInformation = to.params.pathInformation;
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
      .findPregled(riServiceId)
      .then(res => {
        this.riService = res;
      });
  }

  public previousState() {
    if (this.pathInformation) {
      this.$router.push({ name: this.pathInformation.path, params: { search: this.pathInformation.search } });
    } else this.$router.go(-1);
  }
}
