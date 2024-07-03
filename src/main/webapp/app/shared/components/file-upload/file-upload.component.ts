import { Component, Vue, Prop, Inject } from 'vue-property-decorator';
import { BModal } from 'bootstrap-vue';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

interface ImageBlob extends Blob {
  name: string;
  type: 'image/*';
}

interface DocumentBlob extends Blob {
  name: string;
  type: 'application/pdf';
}

const DELETE_FILE_EVENT = 'delete-file';
const MAXIMUM_FILES_NUMBER = 15;
const MAXIMUM_FILE_SIZE = 2;

@Component
export default class FileUpload extends Vue {
  @Prop({ default: '' }) parentRef!: string;
  @Prop({ default: '' }) fileType!: string;
  @Prop({ default: () => [] }) includedFiles!: IDocument[];

  @Inject('documentService') private documentService: () => DocumentService;

  public deleteFileModalRefValue = '';
  public acceptTypeValue = '';
  // public includedFilesFromParentValue: IDocument[] = [];
  public textLabel = '';
  public textFileLimit = '';
  public textFileNumber = '';
  public textFileSize = '';
  public textCurrentFiles = '';
  public textNewFile = '';
  public textDeleteFileTitle = '';
  public textDeleteFileQuestion = '';
  public textErrorFileSizeAndNumber = '';
  public textErrorFileNumber = '';
  public textErrorFileSize = '';

  public documents: IDocument[] = [];
  public formFiles: ImageBlob[] | DocumentBlob[] = [];
  public files: ImageBlob[] | DocumentBlob[] = [];
  public newFilesArray: ImageBlob[] | DocumentBlob[] = [];
  public placeholdertext = '';
  public currentLanguage = '';
  public fileId: number | null = null;
  public documentTypeOptions = DocumentTypeOptions;

  created(): void {
    this.setDeleteFileModalRef();
    this.setTextLanguage();
    this.setAcceptTypes();

    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
        this.setTextLanguage();
      }
    );
  }

  public setDeleteFileModalRef() {
    this.deleteFileModalRefValue = 'deleteFileModal' + this.parentRef;
  }

  get deleteFileModalRef(): string {
    return this.deleteFileModalRefValue;
  }

  public setAcceptTypes() {
    if (this.fileType === DocumentTypeOptions.IMAGE) {
      this.acceptTypeValue = '.jpg, .jpeg, .png, .svg';
    } else if (this.fileType === DocumentTypeOptions.DOCUMENT) {
      this.acceptTypeValue = 'application/pdf';
    }
  }

  get acceptType(): string {
    return this.acceptTypeValue;
  }

  get includedFilesFromParent(): IDocument[] {
    return this.includedFiles;
  }

  public setTextLanguage() {
    if (this.fileType === DocumentTypeOptions.IMAGE) {
      this.textLabel = this.$t('component.fileUpload.imgUpload');
      this.textFileLimit = this.$t('component.fileUpload.imgInfo.imgLimit');
      this.textFileNumber = this.$t('component.fileUpload.imgInfo.imgNumber');
      this.textFileSize = this.$t('component.fileUpload.imgInfo.imgSize');
      this.textCurrentFiles = this.$t('component.fileUpload.currentImg');
      this.textNewFile = this.$t('component.fileUpload.newImg');
      this.textDeleteFileTitle = this.$t('component.fileUpload.deleteImage.title');
      this.textDeleteFileQuestion = this.$t('component.fileUpload.deleteImage.question');

      this.textErrorFileSizeAndNumber = 'component.error.imgSize&Number';
      this.textErrorFileNumber = 'component.error.imgNumber';
      this.textErrorFileSize = 'component.error.imgSize';
    } else if (this.fileType === DocumentTypeOptions.DOCUMENT) {
      this.textLabel = this.$t('component.fileUpload.documentUpload');
      this.textFileLimit = this.$t('component.fileUpload.documentInfo.documentLimit');
      this.textFileNumber = this.$t('component.fileUpload.documentInfo.documentNumber');
      this.textFileSize = this.$t('component.fileUpload.documentInfo.documentSize');
      this.textCurrentFiles = this.$t('component.fileUpload.currentDoc');
      this.textNewFile = this.$t('component.fileUpload.newDoc');
      this.textDeleteFileTitle = this.$t('component.fileUpload.deleteDocument.title');
      this.textDeleteFileQuestion = this.$t('component.fileUpload.deleteDocument.question');

      this.textErrorFileSizeAndNumber = 'component.error.documentSize&Number';
      this.textErrorFileNumber = 'component.error.documentNumber';
      this.textErrorFileSize = 'component.error.documentSize';
    }
  }

  public appendFiles(): void {
    this.newFilesArray = [];
    let numberOfBigFiles: number = 0;
    let numberOfLimitFiles: number = 0;

    for (const formFile of this.formFiles) {
      if (formFile.size > 2 * 1024 * 1024) {
        numberOfBigFiles++;
      }
      if (formFile.size <= 2 * 1024 * 1024 && this.files.filter(file => file.name === formFile.name).length === 0) {
        this.newFilesArray.push(formFile);
      }
    }

    if (this.includedFiles.length + this.files.length + this.newFilesArray.length > MAXIMUM_FILES_NUMBER) {
      const filesToAdd = MAXIMUM_FILES_NUMBER - this.files.length - this.includedFiles.length;
      const filesToAddArray = this.newFilesArray.slice(0, filesToAdd);
      numberOfLimitFiles = this.newFilesArray.length - filesToAddArray.length;
      this.files.push(...filesToAddArray);

      if (numberOfLimitFiles > 0 && numberOfBigFiles > 0) {
        const totalNumberOfFiles = numberOfLimitFiles + numberOfBigFiles;
        const errorText = this.$t(this.textErrorFileSizeAndNumber, { totalNumberOfFiles: totalNumberOfFiles });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      } else if (numberOfLimitFiles > 0) {
        const errorText = this.$t(this.textErrorFileNumber, { numberOfLimitImages: numberOfLimitFiles });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000, // Duration of the notification
        });
      }
    } else {
      for (const formFile of this.newFilesArray) {
        this.files.push(formFile);
      }

      if (numberOfBigFiles > 0) {
        const errorText = this.$t(this.textErrorFileSize, { numberOfBigImages: numberOfBigFiles });
        this.$notify({
          text: errorText,
          type: 'error',
          duration: 8000,
        });
      }
    }
  }

  get availableNumberOfFilesToAdd(): number {
    return MAXIMUM_FILES_NUMBER - this.includedFilesFromParent.length;
  }

  get isUploadFilesDisabled(): boolean {
    const totalImages: number = this.includedFiles.length + this.files.length;
    return totalImages === MAXIMUM_FILES_NUMBER;
  }

  public removeFile(filename: string): void {
    for (let i = 0; i < this.files.length; i++) {
      if (this.files[i].name === filename) {
        this.files.splice(i, 1);
        return;
      }
    }
  }

  public retrieveFile(filename: string): string {
    if (this.fileType === DocumentTypeOptions.IMAGE) {
      return this.documentService().retrieveImage(filename);
    } else if (this.fileType === DocumentTypeOptions.DOCUMENT) {
      return this.documentService().retrieveDocument(filename);
    } else {
      return '';
    }
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

  public prepareDeleteFileModal(fileId: number): void {
    this.fileId = fileId;
    (this.$refs[this.deleteFileModalRef] as any).show();
  }

  public deleteFile(): void {
    try {
      this.$emit(DELETE_FILE_EVENT, this.fileId);
    } catch (error) {
      console.error(error);
    }

    this.closeDeleteFileModal();
  }

  public closeDeleteFileModal(): void {
    (this.$refs[this.deleteFileModalRef] as any).hide();
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
}
