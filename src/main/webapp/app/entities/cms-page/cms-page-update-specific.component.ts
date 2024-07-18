import { Component, Vue, Inject, Watch } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { ICmsPage, CmsPage } from '@/shared/model/cms-page.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';
import { ICmsPageType, CmsPageTypeOptions } from '@/shared/model/cms-page-type.model';

import DocumentService from '../document/document.service';
import CmsPageTypeService from '../cms-page-type/cms-page-type.service';
import AlertService from '@/shared/alert/alert.service';
import CmsPageService from './cms-page.service';

import FileUpload from '@/shared/components/file-upload/file-upload.vue';

interface ImageBlob extends Blob {
  name: string;
  type: 'image/*';
}

interface DocumentBlob extends Blob {
  name: string;
  type: 'application/pdf';
}

const validations: any = {
  cmsPage: {
    title: {
      required,
    },
    content: {
      required,
    },
    type: {
      required,
    },
  },
};

@Component({
  validations,
  components: {
    FileUpload,
  },
})
export default class CmsPageUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsPageService') private cmsPageService: () => CmsPageService;
  public cmsPage: ICmsPage = new CmsPage();

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];

  @Inject('cmsPageTypeService') private cmsPageTypeService: () => CmsPageTypeService;

  public cmsPageTypes: ICmsPageType[] = [];

  public cmsPageImagesValue: IDocument[] = [];
  public cmsPageDocumentsValue: IDocument[] = [];
  public documentTypeOptions = DocumentTypeOptions;
  public cmsPageTypeOptions = CmsPageTypeOptions;
  public imageFiles: ImageBlob[] = [];
  public documentFiles: DocumentBlob[] = [];
  public pageType = '';
  public textHeading = '';

  public isSaving = false;
  public currentLanguage = '';

  data() {
    return {
      customToolbar: [
        ['bold', 'underline', 'link'],
        [{ list: 'ordered' }, { list: 'bullet' }],
      ],
    };
  }

  @Watch('pageType')
  onPageTypeChange() {
    this.setTextHeading();
  }

  @Watch('$i18n.locale')
  onLocaleChange() {
    this.setTextHeading();
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pageType) {
        vm.pageType = to.params.pageType;
        vm.retrieveCmsPage(to.params.pageType);
        vm.setTextHeading();
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
    this.cmsPage.documents = [];
  }

  public setTextHeading() {
    console.log(this.$i18n.messages);
    // TO DO - dodati jezike u json!!!
    if (this.pageType === this.cmsPageTypeOptions.ABOUT) {
      this.textHeading = this.$t('riportalApp.cmsPage.textHeading.about') as string;
    } else if (this.pageType === this.cmsPageTypeOptions.PRIVACY_POLICY) {
      this.textHeading = this.$t('riportalApp.cmsPage.textHeading.privacyPolicy') as string;
    } else if (this.pageType === this.cmsPageTypeOptions.TERMS_OF_USE) {
      this.textHeading = this.$t('riportalApp.cmsPage.textHeading.termsOfUse') as string;
    } else if (this.pageType === this.cmsPageTypeOptions.COOKIES) {
      this.textHeading = this.$t('riportalApp.cmsPage.textHeading.cookiePolicy') as string;
    }
  }

  public save(): void {
    this.isSaving = true;
    if (this.cmsPage.id) {
      this.saveFiles();
      this.cmsPageService()
        .update(this.cmsPage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsPage.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsPageService()
        .create(this.cmsPage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsPage.created', { param: param.id });
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
      this.cmsPage[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsPage[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsPage[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsPage[field] = null;
    }
  }

  public retrieveCmsPage(type: string): void {
    this.cmsPageService()
      .getSpecificByType(type)
      .then(res => {
        this.cmsPage = res;
        this.cmsPageImagesValue = this.cmsPage.documents.filter(doc => doc.type.type === this.documentTypeOptions.IMAGE);
        this.cmsPageDocumentsValue = this.cmsPage.documents.filter(doc => doc.type.type === this.documentTypeOptions.DOCUMENT);
      })
      .catch(err => {
        console.error(err);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
    this.cmsPageTypeService()
      .retrieve()
      .then(res => {
        this.cmsPageTypes = res.data;
      });
  }

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

    formData.append('id', '' + this.cmsPage.id);

    try {
      await this.cmsPageService().uploadFiles(formData);
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
    this.cmsPageService()
      .deleteFile(this.cmsPage.id, fileId)
      .then(res => {
        this.isSaving = false;
        this.retrieveCmsPage(this.cmsPage.type.type);
      });
  }

  get cmsPageImages(): IDocument[] {
    return this.cmsPageImagesValue;
  }

  get cmsPageDocuments(): IDocument[] {
    return this.cmsPageDocumentsValue;
  }
}
