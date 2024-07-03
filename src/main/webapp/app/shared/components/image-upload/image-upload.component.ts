import { Component, Vue, Prop, Inject } from 'vue-property-decorator';

interface ImageBlob extends Blob {
  name: string;
  type: 'image/*';
}

@Component
export default class ImageUpload extends Vue {
  @Prop({ default: 'Potražite kompaniju' }) labelText!: string;
  @Prop({ default: () => [] }) includedImages!: ImageBlob[];

  public formImages: ImageBlob[] = [];
  public imageFiles: ImageBlob[] = [];
  public placeholdertext = '';
  public currentLanguage = '';

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
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

    if (this.includedImages.length + this.imageFiles.length + newImagesArray.length > 15) {
      const imagesToAdd = 15 - this.imageFiles.length - this.includedImages.length;
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

  get availableNumberOfImagesToAdd(): number {
    return 15 - this.includedImages.length;
  }

  get isUploadImageFilesDisabled(): boolean {
    const totalImages: number = this.includedImages.length + this.imageFiles.length;
    return totalImages === 15;
  }

  public removeImage(filename: string): void {
    for (let i = 0; i < this.imageFiles.length; i++) {
      if (this.imageFiles[i].name === filename) {
        this.imageFiles.splice(i, 1);
        return;
      }
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

  public openDeleteImageModal(imageId: number): void {
    // this.selectedImageId = imageId;
    // Show the modal
    // (this.$refs.deleteImageModal as any).show();
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
