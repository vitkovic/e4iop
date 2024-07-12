import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, requiredIf } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AdvertisementSupporterService from '../advertisement-supporter/advertisement-supporter.service';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';

import CollaborationService from '../collaboration/collaboration.service';
import { ICollaboration } from '@/shared/model/collaboration.model';

import MeetingService from '../meeting/meeting.service';
import { IMeeting } from '@/shared/model/meeting.model';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import AdvertisementStatusService from '../advertisement-status/advertisement-status.service';
import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';

import AdvertisementDurationService from '../advertisement-duration/advertisement-duration.service';
import { IAdvertisementDuration } from '@/shared/model/advertisement-duration.model';

import AdvertisementTypeService from '../advertisement-type/advertisement-type.service';
import { IAdvertisementType } from '@/shared/model/advertisement-type.model';

import AdvertisementKindService from '../advertisement-kind/advertisement-kind.service';
import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';

import AdvertisementSubsubcategoryService from '../advertisement-subsubcategory/advertisement-subsubcategory.service';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';

import ThreadService from '../thread/thread.service';
import { IThread } from '@/shared/model/thread.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisement, Advertisement } from '@/shared/model/advertisement.model';
import AdvertisementService from './advertisement.service';

import AccountService from '@/account/account.service';

import CompanySelect from '@/shared/components/company-select/company-select.vue';

const validations: any = {
  advertisement: {
    createdAt: {},
    modifiedAt: {},
    company: {
      required: requiredIf(function () {
        return this.hasAnyAuthority('ROLE_ADMIN');
      }),
    },
    activationDatetime: {
      // required,
    },
    title: {
      required,
    },
    description: {
      required,
    },
    type: {
      required,
    },
    kind: {
      required,
    },
    subsubcategory: {
      required,
    },
    duration: {
      required,
    },
    budget: {},
    conditions: {},
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

enum AdvertisementTypeOptions {
  OFFER = 'Понуда',
  DEMAND = 'Потражња',
}

@Component({
  validations,
  components: {
    CompanySelect,
  },
})
export default class AdvertisementUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  public advertisement: IAdvertisement = new Advertisement();

  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;

  public advertisementSupporters: IAdvertisementSupporter[] = [];

  @Inject('collaborationService') private collaborationService: () => CollaborationService;

  public collaborations: ICollaboration[] = [];

  @Inject('meetingService') private meetingService: () => MeetingService;

  public meetings: IMeeting[] = [];

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];

  @Inject('advertisementStatusService') private advertisementStatusService: () => AdvertisementStatusService;

  public advertisementStatuses: IAdvertisementStatus[] = [];

  @Inject('advertisementDurationService') private advertisementDurationService: () => AdvertisementDurationService;

  public advertisementDurations: IAdvertisementDuration[] = [];

  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;

  public advertisementTypes: IAdvertisementType[] = [];

  @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;

  public advertisementKinds: IAdvertisementKind[] = [];

  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;

  public advertisementSubsubcategories: IAdvertisementSubsubcategory[] = [];

  @Inject('threadService') private threadService: () => ThreadService;

  @Inject('accountService') private accountService: () => AccountService;

  public threads: IThread[] = [];
  public company: ICompany | null = null;
  public excludedCompaniesValue: ICompany[] = [];
  public includedCompaniesValue: ICompany[] = [];
  public portalUser: IPortalUser | null = null;
  public isSaving = false;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;

  public formImages: ImageBlob[] = [];
  public imageFiles: ImageBlob[] = [];
  public formDocuments: DocumentBlob[] = [];
  public documentFiles: DocumentBlob[] = [];
  public placeholdertext = '';
  public browseText: string = '';
  public showDocumentsSection = false;
  public selectedImageId: number | null = null;
  public selectedDocumentId: number | null = null;
  public showImageSizeError: { number: number; state: boolean } = { number: 0, state: false };
  public showImageLimitError: { number: number; state: boolean } = { number: 0, state: false };
  public advertisementTitleHasID: boolean = false;
  public isLoading: boolean = false;
  public advertisementTypeOptions = AdvertisementTypeOptions;
  public componentInitialized = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.setExcludedCompanies();

      if (vm.componentInitialized) {
        return;
      }

      vm.componentInitialized = true;
      vm.initializeComponent();
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
    this.advertisement.documents = [];
  }

  mounted(): void {
    if (this.componentInitialized) {
      return;
    }

    this.componentInitialized = true;
    this.initializeComponent();
  }

  public initializeComponent(): void {
    if (this.$route.params.advertisementId) {
      this.advertisementTitleHasID = false;
      this.retrieveAdvertisement(this.$route.params.advertisementId);
      this.retrieveAdvertisementSupporters(this.$route.params.advertisementId);
    } else {
      if (this.$route.query.type) {
        if (this.$route.query.type == 'offer') {
          this.advertisementTypeService()
            .getAdvertisementTypeByType(this.advertisementTypeOptions.OFFER)
            .then(res => {
              this.advertisement.type = res;
            });
        } else if (this.$route.query.type == 'demand') {
          this.advertisementTypeService()
            .getAdvertisementTypeByType(this.advertisementTypeOptions.DEMAND)
            .then(res => {
              this.advertisement.type = res;
            });
        }
      }

      this.advertisementTitleHasID = true;
    }
    this.initRelationships();
  }

  public setExcludedCompanies(): void {
    const user = this.$store.getters.account;

    if (!user) {
      this.excludedCompaniesValue = [];
      return;
    }

    this.portalUserService()
      .findByUserId(user.id)
      .then(res => {
        this.portalUser = res;
        if (this.portalUser?.company) {
          // this.company = this.portalUser.company;
          this.excludedCompaniesValue = [this.portalUser.company];
        } else {
          this.excludedCompaniesValue = [];
        }
      });
  }

  get excludedCompanies(): ICompany[] {
    return this.excludedCompaniesValue;
  }

  public setIncludedCompanies(): void {
    if (this.advertisement?.advertisementSupporters) {
      this.includedCompaniesValue = this.advertisement.advertisementSupporters.map(supporter => supporter.company);
    } else {
      this.includedCompaniesValue = [];
    }
  }

  get includedCompanies(): ICompany[] {
    return this.includedCompaniesValue;
  }

  public async save(): Promise<void> {
    this.isSaving = true;
    this.isLoading = true;
    if (this.advertisement.id) {
      this.advertisement.expirationDatetime = new Date(this.advertisement.activationDatetime);
      const expirationMonth = this.advertisement.expirationDatetime.getMonth();
      this.advertisement.expirationDatetime.setMonth(expirationMonth + this.advertisement.duration.duration);

      try {
        const param = await this.advertisementService().update(this.advertisement);
        this.isSaving = false;
        const message = this.$t('riportalApp.advertisement.updated', { param: param.id });
        this.alertService().showAlert(message, 'info');
        this.manageAdvertisementSupporters();
        await this.saveFiles(); // Wait for saveFiles to complete
        // this.isLoading = false;
        // this.$router.go(-1);     // Navigate after saveFiles() is done
        setTimeout(() => {
          this.isLoading = false;
          this.$router.go(-1); // Navigate after saveFiles() is done and 5 seconds delay
        }, 500);
      } catch (error) {
        console.error('Failed to update advertisement:', error);
        this.isSaving = false;
      }

      // this.advertisementService()
      //   .update(this.advertisement)
      //   .then(param => {
      //     this.isSaving = false;
      //     this.$router.go(-1);
      //     const message = this.$t('riportalApp.advertisement.updated', { param: param.id });
      //     this.alertService().showAlert(message, 'info');

      //     this.saveFiles();
      //   });
    } else {
      this.advertisement.activationDatetime = new Date();
      this.advertisement.expirationDatetime = new Date();
      const expirationMonth = this.advertisement.expirationDatetime.getMonth();
      this.advertisement.expirationDatetime.setMonth(expirationMonth + this.advertisement.duration.duration);

      this.advertisement.status = this.advertisementStatuses.filter(status => status.status === 'Активан')[0];

      try {
        const param = await this.advertisementService().create(this.advertisement);
        this.isSaving = false;
        const message = this.$t('riportalApp.advertisement.created', { param: param.id });
        this.alertService().showAlert(message, 'success');
        this.advertisement = param;
        this.manageAdvertisementSupporters();
        await this.saveFiles(); // Wait for saveFiles to complete
        // this.isLoading = false;
        // this.$router.go(-1);     // Navigate after saveFiles() is done
        setTimeout(() => {
          this.isLoading = false;
          this.$router.go(-1); // Navigate after saveFiles() is done and 5 seconds delay
        }, 500);
      } catch (error) {
        console.error('Failed to create advertisement:', error);
        this.isSaving = false;
      }

      // this.advertisementService()
      //   .create(this.advertisement)
      //   .then(param => {
      //     this.isSaving = false;
      //     this.$router.go(-1);
      //     const message = this.$t('riportalApp.advertisement.created', { param: param.id });
      //     this.alertService().showAlert(message, 'success');

      //     this.advertisement = param;
      //     this.saveFiles();
      //   });
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
      this.advertisement[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.advertisement[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.advertisement[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.advertisement[field] = null;
    }
  }

  public retrieveAdvertisement(advertisementId): void {
    this.advertisementService()
      .find(advertisementId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        res.activationDatetime = new Date(res.activationDatetime);
        this.advertisement = res;
      });
  }

  public retrieveAdvertisementSupporters(advertisementId): void {
    this.advertisementSupporterService()
      .retrieveAdvertisementSupporters(advertisementId)
      .then(res => {
        this.advertisement.advertisementSupporters = res;
        this.setIncludedCompanies();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    // this.advertisementSupporterService()
    //   .retrieve()
    //   .then(res => {
    //     this.advertisementSupporters = res.data;
    //   });
    // this.collaborationService()
    //   .retrieve()
    //   .then(res => {
    //     this.collaborations = res.data;
    //   });
    // this.meetingService()
    //   .retrieve()
    //   .then(res => {
    //     this.meetings = res.data;
    //   });
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
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.advertisementStatusService()
      .retrieve()
      .then(res => {
        this.advertisementStatuses = res.data;
      });
    this.advertisementDurationService()
      .retrieve()
      .then(res => {
        this.advertisementDurations = res.data;
      });
    this.advertisementTypeService()
      .retrieve()
      .then(res => {
        this.advertisementTypes = res.data;
      });
    this.advertisementKindService()
      .retrieve()
      .then(res => {
        this.advertisementKinds = res.data;
      });
    this.advertisementSubsubcategoryService()
      .getAllOrdered()
      .then(res => {
        this.advertisementSubsubcategories = res.data;
      });
    // this.threadService()
    //   .retrieve()
    //   .then(res => {
    //     this.threads = res.data;
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

  public async saveFiles(): Promise<void> {
    if (this.imageFiles.length === 0 && this.documentFiles.length === 0) {
      return;
    }

    if (this.imageFiles.length > 0 && this.documentFiles.length === 0) {
      await this.saveImages();
      return;
    }

    if (this.imageFiles.length === 0 && this.documentFiles.length > 0) {
      await this.saveDocuments();
      return;
    }

    if (this.imageFiles.length > 0 && this.documentFiles.length > 0) {
      await this.saveImagesAndDocuments();
      return;
    }
  }

  public async saveImagesAndDocuments(): Promise<void> {
    if (this.imageFiles.length === 0 || this.documentFiles.length === 0) {
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

    formData.append('id', '' + this.advertisement.id);

    try {
      await this.advertisementService().uploadFiles(formData);
      this.imageFiles = [];
    } catch (error) {
      if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
        const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
        this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      }
      throw error; // Propagate the error
    } finally {
      this.isSaving = false;
    }
  }

  public async saveImages(): Promise<void> {
    if (this.imageFiles.length === 0) {
      return;
    }

    this.isSaving = true;
    const formData = new FormData();

    for (let i = 0; i < this.imageFiles.length; i++) {
      formData.append('files', this.imageFiles[i]);
    }

    formData.append('id', '' + this.advertisement.id);

    try {
      await this.advertisementService().uploadImages(formData);
      this.imageFiles = [];
    } catch (error) {
      if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
        const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
        this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      }
      throw error; // Propagate the error
    } finally {
      this.isSaving = false;
    }
  }

  public async saveDocuments(): Promise<void> {
    if (this.documentFiles.length === 0) {
      return;
    }

    this.isSaving = true;
    const formData = new FormData();

    for (let i = 0; i < this.documentFiles.length; i++) {
      formData.append('files', this.documentFiles[i]);
    }

    formData.append('id', '' + this.advertisement.id);

    try {
      await this.advertisementService().uploadDocuments(formData);
      this.documentFiles = [];
    } catch (error) {
      if (error.response.status === 400 && error.response.data.type === 'INFRASTRUCTURE_IMAGES_LIMIT') {
        const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
        this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      }
      throw error; // Propagate the error
    } finally {
      this.isSaving = false;
    }
  }

  public appendImageFiles(): void {
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

    if (
      this.advertisement.documents.filter(doc => doc.type.type === 'image').length + this.imageFiles.length + newImagesArray.length >
      15
    ) {
      const imagesToAdd = 15 - this.imageFiles.length - this.advertisement.documents.filter(doc => doc.type.type === 'image').length;
      const imagesToAddArray = newImagesArray.slice(0, imagesToAdd);
      numberOfLimitImages = newImagesArray.length - imagesToAddArray.length;
      this.imageFiles.push(...imagesToAddArray);

      if (numberOfLimitImages > 0 && numberOfBigImages > 0) {
        const totalNumberOfImages = numberOfLimitImages + numberOfBigImages;
        const errorText = this.$t('riportalApp.advertisement.error.imgSize&Number', { totalNumberOfImages });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      } else if (numberOfLimitImages > 0) {
        const errorText = this.$t('riportalApp.advertisement.error.imgNumber', { numberOfLimitImages });
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
        const errorText = this.$t('riportalApp.advertisement.error.imgSize', { numberOfBigImages });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000,
        });
      }
    }
  }

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
      this.advertisement.documents.filter(doc => doc.type.type === 'document').length +
        this.documentFiles.length +
        newDocumentsArray.length >
      15
    ) {
      const documentsToAdd =
        15 - this.documentFiles.length - this.advertisement.documents.filter(doc => doc.type.type === 'document').length;
      const documentsToAddArray = newDocumentsArray.slice(0, documentsToAdd);
      numberOfLimitDocuments = newDocumentsArray.length - documentsToAddArray.length;
      this.documentFiles.push(...documentsToAddArray);

      if (numberOfLimitDocuments > 0 && numberOfBigDocuments > 0) {
        const totalNumberOfDocuments = numberOfLimitDocuments + numberOfBigDocuments;
        const errorText = this.$t('riportalApp.advertisement.error.documentSize&Number', { totalNumberOfDocuments });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      } else if (numberOfLimitDocuments > 0) {
        const errorText = this.$t('riportalApp.advertisement.error.documentNumber', { numberOfLimitDocuments });
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
        const errorText = this.$t('riportalApp.advertisement.error.documentSize', { numberOfBigDocuments });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      }
    }
  }

  public removeImage(filename: string): void {
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

  public deleteImage(): void {
    if (this.selectedImageId !== null) {
      const advertisementId = this.advertisement.id;
      this.advertisementService()
        .deleteImage(advertisementId, this.selectedImageId)
        .then(res => {
          this.isSaving = false;
          this.retrieveAdvertisement(this.advertisement.id);
          // Close the modal after successful deletion
          this.closeDeleteImageDialog();
        });
    }
  }

  public openDeleteImageModal(imageId: number): void {
    this.selectedImageId = imageId;
    // Show the modal
    // (this.$refs.deleteImageModal as any).show();
  }

  public openDeleteDocumentModal(documentId: number): void {
    this.selectedDocumentId = documentId;
  }

  public deleteDocument(documentId: number): void {
    if (this.selectedDocumentId !== null) {
      const advertisementId = this.advertisement.id;
      this.advertisementService()
        .deleteDocument(advertisementId, this.selectedDocumentId)
        .then(res => {
          this.isSaving = false;
          this.retrieveAdvertisement(this.advertisement.id);
          this.closeDeleteDocumentDialog();
        });
    }
  }

  public closeDeleteImageDialog(): void {
    this.selectedImageId = null;
    (this.$refs.deleteImageModal as any).hide();
  }

  public closeDeleteDocumentDialog(): void {
    this.selectedDocumentId = null;
    (this.$refs.deleteDocumentModal as any).hide();
  }

  get availableNumberOfImagesToAdd(): number {
    return 15 - this.advertisement.documents.filter(doc => doc.type.type === 'image').length;
  }

  get isUploadImageFilesDisabled(): boolean {
    const totalImages: number = this.advertisement.documents.filter(doc => doc.type.type === 'image').length + this.imageFiles.length;
    return totalImages === 15;
  }

  get availableNumberOfDocumentsToAdd(): number {
    return 15 - this.advertisement.documents.filter(doc => doc.type.type === 'document').length;
  }

  get isUploadDocumentFilesDisabled(): boolean {
    const totalDocuments: number =
      this.advertisement.documents.filter(doc => doc.type.type === 'document').length + this.documentFiles.length;
    return totalDocuments === 15;
  }

  get browseButtonText(): string {
    if (this.currentLanguage === 'en') {
      return this.$t('riportalApp.advertisement.browseText');
    } else if (this.currentLanguage === 'sr') {
      return this.$t('riportalApp.advertisement.browseText');
    } else if (this.currentLanguage === 'src') {
      return this.$t('riportalApp.advertisement.browseText');
    }
  }

  public manageAdvertisementSupporters(): void {
    let selectedCompanies = [];
    try {
      const companySelectComponent = this.$refs.companySelect as InstanceType<typeof CompanySelect>;
      selectedCompanies = companySelectComponent.selectedCompanies;
    } catch (err) {
      console.error('Nije moguće pristupiti izabranim kompanijama', err);
      return;
    }

    const currentSupporters = this.advertisement.advertisementSupporters.map(supporter => supporter.company);

    const companyIdsToAdd = this.differenceByProperty(selectedCompanies, currentSupporters, 'id').map(company => company.id);

    const companyIdsToRemove = this.differenceByProperty(currentSupporters, selectedCompanies, 'id').map(company => company.id);

    let formData = new FormData();
    formData.append('advertisementId', '' + this.advertisement.id);
    formData.append('companyIdsToAdd', '' + companyIdsToAdd);
    formData.append('companyIdsToRemove', '' + companyIdsToRemove);

    this.advertisementSupporterService()
      .manageAdvertisementSupporters(formData)
      .then(res => {})
      .catch(err => {
        console.error(err);
      });
  }

  /**
   * For two arrays, A and B, find set like difference, A\B.
   */
  public differenceByProperty<T, K extends keyof T>(array1: T[], array2: T[], property: K): T[] {
    const valuesInArray2 = array2.map(item => item[property]);
    return array1.filter(element => !valuesInArray2.includes(element[property]));
  }

  public documentFileName(fileName) {
    let regex = /^doc_ad_\d+_/;
    return fileName.replace(regex, '');
  }
}
