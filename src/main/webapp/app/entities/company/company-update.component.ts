import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AccountService from '@/account/account.service';
import AlertService from '@/shared/alert/alert.service';

import CompanyService from './company.service';
import PortalUserService from '../portal-user/portal-user.service';

import { ICompany, Company } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import FileUpload from '@/shared/components/file-upload/file-upload.vue';

const validations: any = {
  company: {
    createdAt: {},
    modifiedAt: {},
    rsnisId: {
      // required,
      numeric,
    },
    aprId: {
      // required,
      numeric,
    },
    name: {
      required,
    },
    about: {},
    // fields: {},
    isEinovator: {},
  },
};

interface ImageBlob extends Blob {
  name: string;
  type: 'image/*';
}

interface DocumentBlob extends Blob {
  name: string;
  type: 'application/pdf';
}

@Component({
  validations,
  components: {
    FileUpload,
  },
})
export default class CompanyUpdate extends Vue {
  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('alertService')
  private alertService: () => AlertService;

  @Inject('companyService')
  private companyService: () => CompanyService;

  @Inject('portalUserService')
  private portalUserService: () => PortalUserService;

  public company: ICompany = new Company();
  public companyImagesValue: IDocument[] = [];
  public companyDocumentsValue: IDocument[] = [];
  public portalUsers: IPortalUser[] = [];

  public selectedImageId: number | null = null;
  public selectedDocumentId: number | null = null;
  public imageLogo: ImageBlob | null = null;
  public imageFiles: ImageBlob[] = [];
  public documentFiles: DocumentBlob[] = [];
  public placeholdertext = '';
  public documentTypeOptions = DocumentTypeOptions;

  public isSaving = false;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;
  public companyTitleHasID: boolean = false;
  public showDocumentsSection = false;

  data() {
    return {
      customToolbar: [
        ['bold', 'underline', 'link'],
        [{ list: 'ordered' }, { list: 'bullet' }],
      ],
    };
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.companyTitleHasID = false;
        vm.retrieveCompany(to.params.companyId);
      } else {
        vm.companyTitleHasID = true;
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
    this.company.badges = [];
    this.company.documents = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.company.id) {
      this.company.modifiedAt = new Date();
      this.companyService()
        .update(this.company)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.company.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
          const user = this.$store.getters.account;
          this.companyService().updateCompanyModifiedBy(this.company.id, user.id);
          this.saveFiles();
        });
    } else {
      this.company.rsnisId = 9999;
      this.company.aprId = 9999;

      this.company.createdAt = new Date();
      this.companyService()
        .create(this.company)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.company.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
          const user = this.$store.getters.account;
          this.companyService().updateCompanyCreatedBy(param.id, user.id);
          this.saveFiles();
        });
    }
  }

  public async getModifiedByUser(): Promise<any> {
    const user = this.$store.getters.account;
    console.log(user.id);
    this.company.modifiedBy = await this.portalUserService().findByUserId(user.id);
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.company[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.company[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.company[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.company[field] = null;
    }
  }

  public retrieveCompany(companyId): void {
    this.companyService()
      .find(companyId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.company = res;
        this.showDocumentsSection = this.company.id != null;

        this.companyImagesValue = this.company.documents.filter(doc => doc.type.type === this.documentTypeOptions.IMAGE);
        this.companyDocumentsValue = this.company.documents.filter(doc => doc.type.type === this.documentTypeOptions.DOCUMENT);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    // this.portalUserService()
    //   .retrieve()
    //   .then(res => {
    //     this.portalUsers = res.data;
    //   });
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
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

  get browseButtonText(): string {
    if (this.currentLanguage === 'en') {
      return this.$t('riportalApp.company.browseText');
    } else if (this.currentLanguage === 'sr') {
      return this.$t('riportalApp.company.browseText');
    } else if (this.currentLanguage === 'src') {
      return this.$t('riportalApp.company.browseText');
    }
  }

  public saveCompanyLogo(): void {
    if (this.imageLogo === null) {
      const por = this.$t('riportalApp.researchInfrastructure.errors.fileTypeNotMatch');
      this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      return;
    }
    this.isSaving = true;
    const formData = new FormData();

    formData.append('files', this.imageLogo);

    formData.append('id', '' + this.company.id);

    this.companyService()
      .uploadCompanyLogo(formData)
      .then(res => {
        this.isSaving = false;
        this.imageLogo = null;
        this.retrieveCompany(this.company.id);
        // this.company.documents = res;
      })
      .catch(error => {
        this.isSaving = false;
        if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
          const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
          this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        }
      });
  }

  public removeLogo(): void {
    if (this.imageLogo != null) {
      this.imageLogo = null;
    }
  }

  public deleteLogo(): void {
    const companyId = this.company.id;
    this.companyService()
      .deleteLogo(companyId)
      .then(res => {
        this.isSaving = false;
        // this.documentFiles = [];
        // this.company.documents = res;
        this.retrieveCompany(this.company.id);
        this.closeDeleteLogoDialog();
      });
  }

  public openDeleteLogoModal(): void {
    // this.selectedImageId = imageId;
  }

  public closeDeleteLogoDialog(): void {
    // this.selectedDocumentId = null;
    (this.$refs.deleteLogoModal as any).hide();
  }

  public handleFileChange(event) {
    const files = event.target.files;
    if (!files.length) return;

    const file = files[0];

    const maxSize = 2 * 1024 * 1024;
    if (file.size > maxSize) {
      const errorText = this.$t('riportalApp.company.upload.imgInfo.logoSizeLimit') as string;
      this.$notify({
        text: errorText,
        type: 'error',
        duration: 5000,
      });
      this.imageLogo = null;
    } else {
      this.imageLogo = file;
    }
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

    formData.append('id', '' + this.company.id);

    try {
      await this.companyService().uploadFiles(formData);
    } catch (error) {
      console.error(error);
      throw error; // Propagate the error
    } finally {
      this.isSaving = false;
    }
  }

  public deleteFile(fileId: number): void {
    this.companyService()
      .deleteFile(this.company.id, fileId)
      .then(res => {
        this.isSaving = false;
        this.retrieveCompany(this.company.id);
      });
  }

  get companyImages(): IDocument[] {
    return this.companyImagesValue;
  }

  get companyDocuments(): IDocument[] {
    return this.companyDocumentsValue;
  }
}
