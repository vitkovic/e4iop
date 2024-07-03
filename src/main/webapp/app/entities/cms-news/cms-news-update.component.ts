import { Component, Vue, Inject } from 'vue-property-decorator';
import { required } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';
import AlertService from '@/shared/alert/alert.service';

import { ICmsNews, CmsNews } from '@/shared/model/cms-news.model';
import CmsNewsService from './cms-news.service';

import ImageUpload from '@/shared/components/image-upload/image-upload.vue';

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
    ImageUpload,
  },
})
export default class CmsNewsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsNewsService') private cmsNewsService: () => CmsNewsService;

  public cmsNews: ICmsNews = new CmsNews();

  public isSaving = false;
  public currentLanguage = '';

  public imageFiles: ImageBlob[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsNewsId) {
        vm.retrieveCmsNews(to.params.cmsNewsId);
      }
      vm.initRelationships();
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
    this.cmsNews.documents = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.cmsNews.id) {
      this.saveImages();
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
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsNews = res;
      });
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

  public async saveImages(): Promise<void> {
    try {
      const imageUploadComponent = this.$refs.imageUpload as InstanceType<typeof ImageUpload>;
      this.imageFiles = imageUploadComponent.imageFiles;
    } catch (err) {
      console.error('Nije moguće sačuvati slike', err);
      return;
    }

    if (this.imageFiles.length === 0) {
      return;
    }

    this.isSaving = true;
    const formData = new FormData();

    for (let i = 0; i < this.imageFiles.length; i++) {
      formData.append('files', this.imageFiles[i]);
    }

    formData.append('id', '' + this.cmsNews.id);

    console.log(formData);

    // try {
    //   await this.advertisementService().uploadImages(formData);
    //   this.imageFiles = [];
    // } catch (error) {
    //   if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
    //     const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
    //     this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
    //   }
    //   throw error; // Propagate the error
    // } finally {
    //   this.isSaving = false;
    // }
  }
}
