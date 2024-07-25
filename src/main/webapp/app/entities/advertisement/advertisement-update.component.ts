import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, requiredIf } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IAdvertisement, Advertisement } from '@/shared/model/advertisement.model';
import { IAdvertisementType, AdvertisementTypeOptions } from '@/shared/model/advertisement-type.model';
import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';
import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';
import { IAdvertisementDuration } from '@/shared/model/advertisement-duration.model';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AdvertisementService from './advertisement.service';
import AdvertisementTypeServdice from '../advertisement-type/advertisement-type.service';
import AdvertisementKindService from '../advertisement-kind/advertisement-kind.service';
import AdvertisementStatusService from '../advertisement-status/advertisement-status.service';
import AdvertisementDurationService from '../advertisement-duration/advertisement-duration.service';
import AdvertisementSubsubcategoryService from '../advertisement-subsubcategory/advertisement-subsubcategory.service';
import AdvertisementSupporterService from '../advertisement-supporter/advertisement-supporter.service';
import CompanyService from '../company/company.service';
import PortalUserService from '../portal-user/portal-user.service';

import AlertService from '@/shared/alert/alert.service';
import AccountService from '@/account/account.service';
import NumericDataUtils from '@/shared/data/numeric-data-utils.service';

import CompanySelect from '@/shared/components/company-select/company-select.vue';

import { co } from '@fullcalendar/core/internal-common';

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
      // required,
    },
    kinds: {
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

// enum AdvertisementTypeOptions {
//   OFFER = 'Понуда',
//   DEMAND = 'Потражња',
// }

@Component({
  validations,
  components: {
    CompanySelect,
  },
})
export default class AdvertisementUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('numericDataUtils') public numericDataUtils: () => NumericDataUtils;

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
  @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;
  @Inject('advertisementStatusService') private advertisementStatusService: () => AdvertisementStatusService;
  @Inject('advertisementDurationService') private advertisementDurationService: () => AdvertisementDurationService;
  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;
  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public advertisement: IAdvertisement = new Advertisement();
  public advertisementTypes: IAdvertisementType[] = [];
  public advertisementKinds: IAdvertisementKind[] = [];
  public advertisementStatuses: IAdvertisementStatus[] = [];
  public advertisementDurations: IAdvertisementDuration[] = [];
  public advertisementSubsubcategories: IAdvertisementSubsubcategory[] = [];
  public advertisementSupporters: IAdvertisementSupporter[] = [];
  public companies: ICompany[] = [];
  public portalUsers: IPortalUser[] = [];

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
  // public advertisementTypeOptions = AdvertisementTypeOptions;
  public componentInitialized = false;
  public codetrue;
  public inputCode;

  data() {
    return {
      inputCode: '',
      content: '',
      customToolbar: [
        ['bold', 'underline', 'link'],
        [{ list: 'ordered' }, { list: 'bullet' }],
      ],
    };
  }

  public handleClick(): void {
    this.$refs.code.getCode();
  }
  public handleConfirm(): any {
    var capcthacode = this.$refs.code.getCode();

    if (capcthacode == this.inputCode) {
      this.codetrue = true;
    } else {
      this.codetrue = false;
    }

    return this.codetrue;
  }

  public refreshCaptcha(): any {
    return (Math.floor(Math.random() * 90000) + 10000).toString();
  }

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

  public async initializeComponent(): Promise<void> {
    if (this.$route.params.advertisementId) {
      this.advertisementTitleHasID = false;
      await this.retrieveAdvertisement(this.$route.params.advertisementId);
      await this.retrieveAdvertisementSupporters(this.$route.params.advertisementId);
      await this.setIncludedCompanies();
    } else {
      if (this.$route.query.type) {
        if (this.$route.query.type == AdvertisementTypeOptions.OFFER) {
          this.advertisementTypeService()
            .getAdvertisementTypeByType(AdvertisementTypeOptions.OFFER)
            .then(res => {
              this.advertisement.type = res;
            });
        } else if (this.$route.query.type == AdvertisementTypeOptions.DEMAND) {
          this.advertisementTypeService()
            .getAdvertisementTypeByType(AdvertisementTypeOptions.DEMAND)
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

  public async setIncludedCompanies(): Promise<void> {
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

    if (!this.handleConfirm()) {
      const message = this.$t('riportalApp.advertisement.captchafalse');
      this.$notify({ text: JSON.stringify(message).replace(/["]/g, ''), type: 'error', duration: 10000 });
      this.isSaving = false;
      this.isLoading = false;
      return;
    }

    // Remove any dots that might be in budget input
    this.formatBudgetToRealNumber();

    if (this.advertisement.id) {
      this.advertisement.expirationDatetime = new Date(this.advertisement.activationDatetime);
      const expirationMonth = this.advertisement.expirationDatetime.getMonth();
      this.advertisement.expirationDatetime.setMonth(expirationMonth + this.advertisement.duration.duration);

      try {
        const param = await this.advertisementService().update(this.advertisement);
        this.isSaving = false;
        const message = this.$t('riportalApp.advertisement.updated', { param: param.id });
        this.alertService().showAlert(message, 'info');
        await this.manageAdvertisementSupporters();
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
      if (this.advertisement.activationDatetime == null || this.advertisement.activationDatetime.toDateString() == '') {
        this.advertisement.activationDatetime = new Date();
        this.advertisement.expirationDatetime = new Date();
      } else {
        this.advertisement.expirationDatetime = this.advertisement.activationDatetime;
      }

      const expirationMonth = this.advertisement.expirationDatetime.getMonth();
      this.advertisement.expirationDatetime.setMonth(expirationMonth + this.advertisement.duration.duration);
      // console.log(this.advertisement.status)
      var dtm = new Date();
      if (this.advertisement.activationDatetime > dtm) {
        this.advertisement.status.id = 3552;
      }
      //this.advertisement.status = this.advertisementStatuses.filter(status => status.status === 'Активан')[0];

      try {
        const param = await this.advertisementService().create(this.advertisement);
        this.isSaving = false;
        const message = this.$t('riportalApp.advertisement.created', { param: param.id });
        this.alertService().showAlert(message, 'success');
        this.advertisement = param;
        await this.manageAdvertisementSupporters();
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

  public async retrieveAdvertisement(advertisementId): Promise<void> {
    await this.advertisementService()
      .find(advertisementId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        res.activationDatetime = new Date(res.activationDatetime);
        this.advertisement = res;

        // Make budget easier to read
        this.formatBudgetToDottedStringNumber();
      });
  }

  public async retrieveAdvertisementSupporters(advertisementId): Promise<void> {
    await this.advertisementSupporterService()
      .retrieveAdvertisementSupporters(advertisementId)
      .then(res => {
        this.advertisement.advertisementSupporters = res;
        // this.setIncludedCompanies();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
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

  public async deleteImage(): Promise<void> {
    if (this.selectedImageId !== null) {
      const advertisementId = this.advertisement.id;
      await this.advertisementService()
        .deleteImage(advertisementId, this.selectedImageId)
        .then(res => {
          this.isSaving = false;
          this.closeDeleteImageDialog();
        });
      // If we fetch only advertisement without supporters,
      // supporters will be null, thus failing any updates on save.
      await this.retrieveAdvertisement(this.advertisement.id);
      await this.retrieveAdvertisementSupporters(this.advertisement.id);
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

  public async deleteDocument(): Promise<void> {
    if (this.selectedDocumentId !== null) {
      const advertisementId = this.advertisement.id;
      await this.advertisementService()
        .deleteDocument(advertisementId, this.selectedDocumentId)
        .then(res => {
          this.isSaving = false;
          this.closeDeleteDocumentDialog();
        });
      // If we fetch only advertisement without supporters,
      // supporters will be null, thus failing any updates on save.
      await this.retrieveAdvertisement(this.advertisement.id);
      await this.retrieveAdvertisementSupporters(this.advertisement.id);
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

  public async manageAdvertisementSupporters(): Promise<void> {
    let selectedCompanies = [];
    try {
      const companySelectComponent = this.$refs.companySelect as InstanceType<typeof CompanySelect>;
      selectedCompanies = companySelectComponent.selectedCompanies;
    } catch (err) {
      console.error('Nije moguće pristupiti izabranim kompanijama', err);
      return;
    }

    if (this.advertisement?.advertisementSupporters === null) {
      console.error('Nije moguće pristupiti prethodno izabranim kompanijama');
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

  public budgetInputFormatter(event: Event): void {
    let input = event.target as HTMLInputElement;
    let value = input.value;

    this.advertisement.budget = this.numericDataUtils().dottedInputFormatter(value);
  }

  public formatBudgetToDottedStringNumber(): void {
    this.advertisement.budget = this.numericDataUtils().dottedInputFormatter(this.advertisement.budget);
  }

  public formatBudgetToRealNumber(): void {
    if (this.advertisement.budget) {
      this.advertisement.budget = parseInt(this.advertisement.budget.replace(/\D/g, ''), 10);
    }
  }

  public advertisementCategorizationBranch(subsubcategory: IAdvertisementSubsubcategory): string {
    let branch = '';
    const currentLanguage = this.$store.getters.currentLanguage;

    if (currentLanguage === 'sr') {
      branch =
        subsubcategory.advertisementSubcategory.advertisementCategory.name +
        ' / ' +
        subsubcategory.advertisementSubcategory.name +
        ' / ' +
        subsubcategory.name;

      return branch;
    }

    if (currentLanguage === 'src') {
      branch =
        subsubcategory.advertisementSubcategory.advertisementCategory.nameSrc +
        ' / ' +
        subsubcategory.advertisementSubcategory.nameSrc +
        ' / ' +
        subsubcategory.nameSrc;

      return branch;
    }

    if (currentLanguage === 'en') {
      branch =
        subsubcategory.advertisementSubcategory.advertisementCategory.nameEn +
        ' / ' +
        subsubcategory.advertisementSubcategory.nameEn +
        ' / ' +
        subsubcategory.nameEn;

      return branch;
    }

    return branch;
  }

  public customKindLabel(kind: IAdvertisementKind) {
    const currentLanguage = this.$store.getters.currentLanguage;

    if (currentLanguage === 'sr') {
      return `${kind.kind}`;
    } else if (currentLanguage === 'src') {
      return `${kind.kindSrc}`;
    } else if (currentLanguage === 'en') {
      return `${kind.kindEn}`;
    }
  }
}
