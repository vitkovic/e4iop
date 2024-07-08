import { Component, Vue, Inject } from 'vue-property-decorator';
import { required } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';
import AlertService from '@/shared/alert/alert.service';

import { ICmsNews, CmsNews } from '@/shared/model/cms-news.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import CmsNewsService from './cms-news.service';

import FileUpload from '@/shared/components/file-upload/file-upload.vue';

interface ImageBlob extends Blob {
  name: string;
  type: 'image/*';
}

const validations: any = {
  cmsNews: {
    title: {
      required,
    },
    content: {
      required,
    },
    sequenceNumber: {
      required,
    },
    date: {},
  },
};

@Component({
  validations,
  components: {
    FileUpload,
  },
})
export default class CmsNewsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsNewsService') private cmsNewsService: () => CmsNewsService;

  public cmsNews: ICmsNews = new CmsNews();
  public cmsNewsImagesValue: IDocument[] = [];
  public cmsNewsDocumentsValue: IDocument[] = [];
  public documentTypeOptions = DocumentTypeOptions;
  public cmsNewsTitleHasID: boolean = false;

  public isSaving = false;
  public currentLanguage = '';

  public imageFiles: ImageBlob[] = [];
  public documentFiles: ImageBlob[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsNewsId) {
        vm.retrieveCmsNews(to.params.cmsNewsId);
        vm.cmsNewsTitleHasID = true;
      } else {
        vm.cmsNewsTitleHasID = false;
      }
    });
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

  public save(): void {
    this.isSaving = true;
    if (this.cmsNews.id) {
      this.saveFiles();
      this.cmsNewsService()
        .update(this.cmsNews)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsNews.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsNews.date = new Date();

      this.cmsNewsService()
        .create(this.cmsNews)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsNews.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.cmsNews[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsNews[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsNews[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsNews[field] = null;
    }
  }

  public retrieveCmsNews(cmsNewsId): void {
    this.cmsNewsService()
      .find(cmsNewsId)
      .then(res => {
        this.cmsNews = res;
        this.cmsNewsImagesValue = this.cmsNews.documents.filter(doc => doc.type.type === this.documentTypeOptions.IMAGE);
        this.cmsNewsDocumentsValue = this.cmsNews.documents.filter(doc => doc.type.type === this.documentTypeOptions.DOCUMENT);
      });
  }

  get cmsNewsImages(): IDocument[] {
    return this.cmsNewsImagesValue;
  }

  get cmsNewsDocuments(): IDocument[] {
    return this.cmsNewsDocumentsValue;
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  public async saveFiles(): Promise<void> {
    try {
      const imageUploadComponent = this.$refs.imageUpload as InstanceType<typeof FileUpload>;
      const documentUploadComponent = this.$refs.documentUpload as InstanceType<typeof FileUpload>;

      this.imageFiles = imageUploadComponent.files;
      this.documentFiles = documentUploadComponent.files;
    } catch (err) {
      console.error('Nije moguće sačuvati slike i dokumenta', err);
      return;
    }

    if (this.imageFiles.length === 0 && this.documentFiles.length === 0) {
      return;
    }

    this.isSaving = true;
    const formData = new FormData();

    for (let i = 0; i < this.imageFiles.length; i++) {
      formData.append('imageFiles', this.imageFiles[i]);
    }

    for (let i = 0; i < this.documentFiles.length; i++) {
      formData.append('documentFiles', this.documentFiles[i]);
    }

    formData.append('id', '' + this.cmsNews.id);

    try {
      await this.cmsNewsService().uploadFiles(formData);
      // this.imageFiles = [];
    } catch (error) {
      console.error(error);
      // if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
      //   const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
      //   this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      // }
      throw error; // Propagate the error
    } finally {
      this.isSaving = false;
    }
  }

  public deleteFile(fileId: number): void {
    this.cmsNewsService()
      .deleteFile(this.cmsNews.id, fileId)
      .then(res => {
        this.isSaving = false;
        this.retrieveCmsNews(this.cmsNews.id);
      });
  }
}
