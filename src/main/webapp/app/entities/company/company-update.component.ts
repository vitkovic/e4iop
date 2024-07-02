import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import CollaborationService from '../collaboration/collaboration.service';
import { ICollaboration } from '@/shared/model/collaboration.model';

import AdvertisementSupporterService from '../advertisement-supporter/advertisement-supporter.service';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';

import ThreadService from '../thread/thread.service';
import { IThread } from '@/shared/model/thread.model';

import BadgeService from '../badge/badge.service';
import { IBadge } from '@/shared/model/badge.model';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompany, Company } from '@/shared/model/company.model';
import CompanyService from './company.service';

import AccountService from '@/account/account.service';

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
    fields: {},
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
})
export default class CompanyUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('companyService') private companyService: () => CompanyService;
  public company: ICompany = new Company();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];

  @Inject('collaborationService') private collaborationService: () => CollaborationService;

  public collaborations: ICollaboration[] = [];

  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;

  public advertisementSupporters: IAdvertisementSupporter[] = [];

  @Inject('threadService') private threadService: () => ThreadService;

  public threads: IThread[] = [];

  @Inject('badgeService') private badgeService: () => BadgeService;

  public badges: IBadge[] = [];

  @Inject('documentService') private documentService: () => DocumentService;

  @Inject('accountService') private accountService: () => AccountService;

  public documents: IDocument[] = [];
  public isSaving = false;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;

  public imageLogo: ImageBlob | null = null;
  public formImages: ImageBlob[] = [];
  public imageFiles: ImageBlob[] = [];
  public formDocuments: DocumentBlob[] = [];
  public documentFiles: DocumentBlob[] = [];
  public placeholdertext = '';
  public browseText: string = '';
  public showDocumentsSection = false;
  public selectedImageId: number | null = null;
  public selectedDocumentId: number | null = null;
  public companyTitleHasID: boolean = false;

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
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
    this.collaborationService()
      .retrieve()
      .then(res => {
        this.collaborations = res.data;
      });
    this.collaborationService()
      .retrieve()
      .then(res => {
        this.collaborations = res.data;
      });
    this.advertisementSupporterService()
      .retrieve()
      .then(res => {
        this.advertisementSupporters = res.data;
      });
    this.threadService()
      .retrieve()
      .then(res => {
        this.threads = res.data;
      });
    this.badgeService()
      .retrieve()
      .then(res => {
        this.badges = res.data;
      });
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
    // this.portalUserService()
    //   .retrieve()
    //   .then(res => {
    //     this.portalUsers = res.data;
    //   });
    // this.portalUserService()
    //   .retrieve()
    //   .then(res => {
    //     this.portalUsers = res.data;
    //   });
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
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

  public formatNames(files): string {
    if (files.length === 1) {
      return files[0].name;
    } else {
      // const out = '' + this.$t('riportalApp.researchInfrastructure.filesSelected', { param: files.length });
      const out = 'Files selected: ' + files.length;
      return out;
    }
    // return files.length === 1 ? files[0].name : `${files.length} files selected`;
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

  public saveImages(): void {
    if (this.imageFiles.length === 0) {
      const por = this.$t('riportalApp.researchInfrastructure.errors.fileTypeNotMatch');
      this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      return;
    }
    this.isSaving = true;
    const formData = new FormData();

    for (let i = 0; i < this.imageFiles.length; i++) {
      formData.append('files', this.imageFiles[i]);
    }

    formData.append('id', '' + this.company.id);

    this.companyService()
      .uploadImages(formData)
      .then(res => {
        this.isSaving = false;
        this.imageFiles = [];
        // this.company.documents = res;
        this.retrieveCompany(this.company.id);
      })
      .catch(error => {
        this.isSaving = false;
        if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
          const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
          this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        }
      });
  }

  public saveCompanyDocuments(): void {
    if (this.documentFiles.length === 0) {
      const por = this.$t('riportalApp.researchInfrastructure.errors.fileTypeNotMatch');
      this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      return;
    }
    this.isSaving = true;
    const formData = new FormData();

    for (let i = 0; i < this.documentFiles.length; i++) {
      formData.append('files', this.documentFiles[i]);
    }

    formData.append('id', '' + this.company.id);

    this.companyService()
      .uploadCompanyDocuments(formData)
      .then(res => {
        this.isSaving = false;
        this.documentFiles = [];
        // this.company.documents = res;
        this.retrieveCompany(this.company.id);
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

  public removeImage(filename: string): void {
    console.log(filename);
    for (let i = 0; i < this.imageFiles.length; i++) {
      if (this.imageFiles[i].name === filename) {
        this.imageFiles.splice(i, 1);
        return;
      }
    }
  }

  public removeDocument(filename: string): void {
    for (let i = 0; i < this.documentFiles.length; i++) {
      if (this.documentFiles[i].name === filename) {
        this.documentFiles.splice(i, 1);
        return;
      }
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

  // public deleteImage(imageId: number): void {
  //   const companyId = this.company.id;
  //   this.companyService()
  //     .deleteImage(companyId, imageId)
  //     .then(res => {
  //       this.isSaving = false;
  //       // this.documentFiles = [];
  //       // this.company.documents = res;
  //       this.retrieveCompany(this.company.id);
  //     });
  // }

  public deleteImage(): void {
    if (this.selectedImageId !== null) {
      const companyId = this.company.id;
      this.companyService()
        .deleteImage(companyId, this.selectedImageId)
        .then(res => {
          this.isSaving = false;
          this.retrieveCompany(this.company.id);
          // Close the modal after successful deletion
          this.closeDeleteImageDialog();
        });
    }
  }

  // public deleteDocument(documentId: number): void {
  //   const companyId = this.company.id;
  //   this.companyService()
  //     .deleteDocument(companyId, documentId)
  //     .then(res => {
  //       this.isSaving = false;
  //       // this.documentFiles = [];
  //       // this.company.documents = res;
  //       this.retrieveCompany(this.company.id);
  //     });
  // }

  public deleteDocument(documentId: number): void {
    if (this.selectedDocumentId !== null) {
      const companyId = this.company.id;
      this.companyService()
        .deleteDocument(companyId, this.selectedDocumentId)
        .then(res => {
          this.isSaving = false;
          this.retrieveCompany(this.company.id);
          this.closeDeleteDocumentDialog();
        });
    }
  }

  // public appendImageFiles(): void {
  //   for (const formImage of this.formImages) {
  //     if (this.imageFiles.filter(image => image.name === formImage.name).length === 0) {
  //       this.imageFiles.push(formImage);
  //     }
  //   }
  // }

  public appendImageFiles(): void {
    console.log(this.company.documents);
    const newImagesArray: ImageBlob[] = [];
    let numberOfBigImages: number = 0;
    let numberOfLimitImages: number = 0;

    for (const formImage of this.formImages) {
      if (formImage.size > 2 * 1024 * 1024) {
        numberOfBigImages++;
      }
      if (formImage.size <= 2 * 1024 * 1024 && this.imageFiles.filter(image => image.name === formImage.name).length === 0) {
        newImagesArray.push(formImage);
      }
    }

    if (this.company.documents.filter(doc => doc.type.type === 'image').length + this.imageFiles.length + newImagesArray.length > 15) {
      const imagesToAdd = 15 - this.imageFiles.length - this.company.documents.filter(doc => doc.type.type === 'image').length;
      const imagesToAddArray = newImagesArray.slice(0, imagesToAdd);
      numberOfLimitImages = newImagesArray.length - imagesToAddArray.length;
      this.imageFiles.push(...imagesToAddArray);

      if (numberOfLimitImages > 0 && numberOfBigImages > 0) {
        const totalNumberOfImages = numberOfLimitImages + numberOfBigImages;
        const errorText = this.$t('riportalApp.company.error.imgSize&Number', { totalNumberOfImages });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      } else if (numberOfLimitImages > 0) {
        const errorText = this.$t('riportalApp.company.error.imgNumber', { numberOfLimitImages });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      }
    } else {
      for (const formImage of newImagesArray) {
        this.imageFiles.push(formImage);
      }

      if (numberOfBigImages > 0) {
        const errorText = this.$t('riportalApp.company.error.imgSize', { numberOfBigImages });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000,
        });
      }
    }
  }

  // public appendDocumentFiles(): void {
  //   for (const formDocument of this.formDocuments) {
  //     if (this.documentFiles.filter(document => document.name === formDocument.name).length === 0) {
  //       this.documentFiles.push(formDocument);
  //     }
  //   }
  // }

  public appendDocumentFiles(): void {
    const newDocumentsArray: DocumentBlob[] = [];
    let numberOfBigDocuments: number = 0;
    let numberOfLimitDocuments: number = 0;

    for (const formDocument of this.formDocuments) {
      if (formDocument.size > 2 * 1024 * 1024) {
        numberOfBigDocuments++;
      }
      if (formDocument.size <= 2 * 1024 * 1024 && this.documentFiles.filter(document => document.name === formDocument.name).length === 0) {
        newDocumentsArray.push(formDocument);
      }
    }

    if (
      this.company.documents.filter(doc => doc.type.type === 'document').length + this.documentFiles.length + newDocumentsArray.length >
      15
    ) {
      const documentsToAdd = 15 - this.documentFiles.length - this.company.documents.filter(doc => doc.type.type === 'document').length;
      const documentsToAddArray = newDocumentsArray.slice(0, documentsToAdd);
      numberOfLimitDocuments = newDocumentsArray.length - documentsToAddArray.length;
      this.documentFiles.push(...documentsToAddArray);

      if (numberOfLimitDocuments > 0 && numberOfBigDocuments > 0) {
        const totalNumberOfDocuments = numberOfLimitDocuments + numberOfBigDocuments;
        const errorText = this.$t('riportalApp.company.error.documentSize&Number', { totalNumberOfDocuments });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      } else if (numberOfLimitDocuments > 0) {
        const errorText = this.$t('riportalApp.company.error.documentNumber', { numberOfLimitDocuments });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      }
    } else {
      for (const formDocument of newDocumentsArray) {
        this.documentFiles.push(formDocument);
      }

      if (numberOfBigDocuments > 0) {
        const errorText = this.$t('riportalApp.company.error.documentSize', { numberOfBigDocuments });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      }
    }
  }

  public openDeleteImageModal(imageId: number): void {
    this.selectedImageId = imageId;
  }

  public closeDeleteImageDialog(): void {
    this.selectedImageId = null;
    (this.$refs.deleteImageModal as any).hide();
  }

  public openDeleteDocumentModal(documentId: number): void {
    this.selectedDocumentId = documentId;
  }

  public closeDeleteDocumentDialog(): void {
    this.selectedDocumentId = null;
    (this.$refs.deleteDocumentModal as any).hide();
  }

  public openDeleteLogoModal(): void {
    // this.selectedImageId = imageId;
  }

  public closeDeleteLogoDialog(): void {
    // this.selectedDocumentId = null;
    (this.$refs.deleteLogoModal as any).hide();
  }

  get availableNumberOfImagesToAdd(): number {
    return 15 - this.company.documents.filter(doc => doc.type.type === 'image').length;
  }

  get isUploadImageFilesDisabled(): boolean {
    const totalImages: number = this.company.documents.filter(doc => doc.type.type === 'image').length + this.imageFiles.length;
    return totalImages === 15;
  }

  get availableNumberOfDocumentsToAdd(): number {
    return 15 - this.company.documents.filter(doc => doc.type.type === 'document').length;
  }

  get isUploadDocumentFilesDisabled(): boolean {
    const totalDocuments: number = this.company.documents.filter(doc => doc.type.type === 'document').length + this.documentFiles.length;
    return totalDocuments === 15;
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

  public documentFileName(fileName) {
    let regex = /^doc_company_\d+_/;
    return fileName.replace(regex, '');
  }
}
