import { Component, Vue, Inject } from 'vue-property-decorator';

import { url, required } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { ICmsSlider, CmsSlider } from '@/shared/model/cms-slider.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import AlertService from '@/shared/alert/alert.service';
import CmsSliderService from './cms-slider.service';
import DocumentService from '../document/document.service';

interface ImageBlob extends Blob {
  name: string;
  type: 'image/*';
}

const validations: any = {
  cmsSlider: {
    title: {
      required,
    },
    link: {
      required,
      url,
    },
    newTab: {
      required,
    },
    ordinalNumber: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CmsSliderUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsSliderService') private cmsSliderService: () => CmsSliderService;
  @Inject('documentService') private documentService: () => DocumentService;

  public cmsSlider: ICmsSlider = new CmsSlider();
  public sliderImage: ImageBlob | null = null;
  public isSaving = false;
  public currentLanguage = '';
  public cmsSliderTitleHasID: boolean = false;

  public placeholdertext = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      // Inside the callback, `vm` refers to the Vue component instance
      if (to.params.cmsSliderId) {
        vm.retrieveCmsSlider(to.params.cmsSliderId);
        vm.cmsSliderTitleHasID = true; // Set to true for edit mode
      } else {
        vm.cmsSliderTitleHasID = false; // Set to false for new mode
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
    if (this.cmsSlider.id) {
      this.cmsSliderService()
        .update(this.cmsSlider)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsSlider.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
          this.saveSliderImage();
        });
    } else {
      this.cmsSlider.createdAt = new Date();
      this.cmsSliderService()
        .create(this.cmsSlider)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsSlider.created', { param: param.id });
          this.alertService().showAlert(message, 'success');

          this.cmsSlider = param;
          this.saveSliderImage();
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
      this.cmsSlider[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsSlider[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsSlider[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsSlider[field] = null;
    }
  }

  public retrieveCmsSlider(cmsSliderId): void {
    this.cmsSliderService()
      .find(cmsSliderId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsSlider = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public saveSliderImage(): void {
    if (this.sliderImage === null) {
      return;
    }

    this.isSaving = true;
    const formData = new FormData();

    formData.append('files', this.sliderImage);
    formData.append('id', '' + this.cmsSlider.id);

    this.cmsSliderService()
      .uploadSliderImage(formData)
      .then(res => {
        this.isSaving = false;
        this.sliderImage = null;
        this.retrieveCmsSlider(this.cmsSlider.id);
      })
      .catch(error => {
        this.isSaving = false;
        console.error(error);
      });
  }

  public openDeleteSliderImageModal(): void {}

  public deleteSliderImage(): void {
    const cmsSliderId = this.cmsSlider.id;
    this.cmsSliderService()
      .deleteSliderImage(cmsSliderId)
      .then(res => {
        this.isSaving = false;
        // this.documentFiles = [];
        // this.company.documents = res;
        this.retrieveCmsSlider(this.cmsSlider.id);
      });

    this.closeDeleteSliderImageModal();
  }

  public closeDeleteSliderImageModal(): void {
    (this.$refs.deleteSliderImageModal as any).hide();
  }

  public retrieveFile(file: IDocument): string {
    if (file.type.type === DocumentTypeOptions.IMAGE) {
      return this.documentService().retrieveImage(file.filename);
    } else if (file.type.type === DocumentTypeOptions.DOCUMENT) {
      return this.documentService().retrieveDocument(file.filename);
    } else {
      return '';
    }
  }

  public removeSliderImage(): void {
    if (this.sliderImage != null) {
      this.sliderImage = null;
    }
  }

  get browseButtonText(): string {
    if (this.currentLanguage === 'en') {
      return this.$t('riportalApp.company.browseText');
    } else if (this.currentLanguage === 'sr') {
      return this.$t('riportalApp.company.browseText');
    } else if (this.currentLanguage === 'src') {
      return this.$t('riportalApp.company.browseText');
    }
  }

  public formatNames(files) {
    const filesLength = files.length;
    if (files.length === 1) {
      return files[0].name;
    } else {
      // const out = '' + this.$t('riportalApp.researchInfrastructure.filesSelected', { param: files.length });
      const out = this.$t('riportalApp.advertisement.upload.filesSelectedPlaceholder', { filesLength });
      return out;
    }
    // return files.length === 1 ? files[0].name : `${files.length} files selected`;
  }

  public handleFileChange(event): void {
    const files = event.target.files;
    if (!files.length) return;

    const file = files[0];
    const maxSize = 2 * 1024 * 1024;

    if (file.size > maxSize) {
      const errorText = this.$t('riportalApp.cmsSlider.imageUpload.errorSize') as string;
      this.$notify({
        text: errorText,
        type: 'error',
        duration: 5000,
      });
      return;
    }

    this.checkImageDimensions(file).then(valid => {
      if (!valid) {
        const errorText = this.$t('riportalApp.cmsSlider.imageUpload.errorDimension') as string;
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 5000,
        });
        return;
      }

      this.sliderImage = file;
    });
  }

  private checkImageDimensions(file: File): Promise<boolean> {
    return new Promise(resolve => {
      const img = new Image();
      img.src = URL.createObjectURL(file);
      img.onload = () => {
        const isValid = img.width >= 0 && img.height >= 0;
        URL.revokeObjectURL(img.src);
        resolve(isValid);
      };
    });
  }
}
