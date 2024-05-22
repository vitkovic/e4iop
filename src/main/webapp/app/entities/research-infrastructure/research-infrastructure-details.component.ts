import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import ResearchInfrastructureService from './research-infrastructure.service';

import UploadFilesService from '@/utility/upload/upload_service';

@Component
export default class ResearchInfrastructureDetails extends Vue {
  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;
  public researchInfrastructure: IResearchInfrastructure = {};
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researchInfrastructureId) {
        vm.retrieveResearchInfrastructure(to.params.researchInfrastructureId);
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

  public loadImage(filename): string {
    return 'api/research-infrastructures/image/' + filename;
  }

  public retrieveResearchInfrastructure(researchInfrastructureId) {
    this.researchInfrastructureService()
      .find(researchInfrastructureId)
      .then(res => {
        this.researchInfrastructure = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public getDescriptionByLanguage(researchInfrastructure: IResearchInfrastructure): string {
    switch (this.currentLanguage) {
      case 'sr':
        return researchInfrastructure.descriptionSr;
      case 'en':
        return researchInfrastructure.descriptionEn;
      case 'src':
        return researchInfrastructure.descriptionSrCyr;
      default:
        return researchInfrastructure.descriptionSr;
    }
  }

  public shortenedDescription(): string {
    const description = this.getDescriptionByLanguage(this.researchInfrastructure);

    if (description.length <= 90 || this.researchInfrastructure.showFullDescription) {
      return description;
    } else {
      return description.slice(0, 90) + '...';
    }
  }

  // Method to toggle the showFullDescription flag
  public toggleDescription(): void {
    this.researchInfrastructure = {
      ...this.researchInfrastructure,
      showFullDescription: !this.researchInfrastructure.showFullDescription,
    };
  }
}
