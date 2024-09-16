import { Component, Vue, Inject } from 'vue-property-decorator';
// import 'viewerjs/dist/viewer.css';
// import VueViewer from 'v-viewer';

import { ICmsNews } from '@/shared/model/cms-news.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import CmsNewsService from '../cms-news/cms-news.service';
import DocumentService from '../document/document.service';

@Component
export default class PageNews extends Vue {
  @Inject('cmsNewsService')
  private cmsNewsService: () => CmsNewsService;

  @Inject('documentService')
  private documentService: () => DocumentService;

  public firstImgWidth: number = 0;
  public imagesLoaded: number = 0;
  public totalItems = 0;
  public companyImagesArray: string[] = [];
  public currentLightboxImage: IDocument;
  public currentIndex = 0;
  public showMask = false;
  public previewImage = false;
  public totalImagesCount = 0;

  public cmsNews: ICmsNews | null = null;
  public cmsNewsImages: IDocument[] = [];
  public cmsNewsDocuments: IDocument[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsNewsId) {
        vm.retrieveCmsNews(to.params.cmsNewsId);
      }
    });
  }

  mounted() {
    this.updateImgWidth();
    window.addEventListener('resize', this.updateImgWidth);
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.updateImgWidth);
  }

  public retrieveCmsNews(id): void {
    this.cmsNewsService()
      .find(id)
      .then(res => {
        this.cmsNews = res;
        this.cmsNewsImages = this.cmsNews.documents.filter(doc => doc.type.type === DocumentTypeOptions.IMAGE);
        this.cmsNewsDocuments = this.cmsNews.documents.filter(doc => doc.type.type === DocumentTypeOptions.DOCUMENT);
      })
      .catch(err => {
        console.error(err);
      });
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

  get currentImage(): IDocument {
    return this.currentLightboxImage;
  }

  // --- LIGHTBOX ---

  // public async companyImages(): Promise<void> {
  //   this.companyImagesArray = []; // Clear any existing images
  //   for (let document of this.company.documents) {
  //     if (document.type.type === 'image') {
  //       const imageUrl = await this.companyService().retrieveImage(document.filename);
  //       this.companyImagesArray.push(imageUrl);
  //     }
  //   }
  // }

  public onPreviewImage(index: number): void {
    this.showMask = true;
    this.previewImage = true;
    this.currentIndex = index;
    this.currentLightboxImage = this.cmsNewsImages[index];
    this.$nextTick(() => {
      const lightboxElement = this.$refs.lightbox as HTMLElement;
      if (lightboxElement) {
        lightboxElement.focus();
      }
    });
  }

  public onClosePreviewImage() {
    this.showMask = false;
    this.previewImage = false;
  }

  public prev(): void {
    this.currentIndex = this.currentIndex - 1;
    if (this.currentIndex < 0) {
      this.currentIndex = this.cmsNewsImages.length - 1;
    }
    this.currentLightboxImage = this.cmsNewsImages[this.currentIndex];
  }

  public next(): void {
    this.currentIndex = this.currentIndex + 1;
    if (this.currentIndex > this.cmsNewsImages.length - 1) {
      this.currentIndex = 0;
    }
    this.currentLightboxImage = this.cmsNewsImages[this.currentIndex];
  }

  // --- LIGHTBOX END ---

  onImageLoad() {
    this.imagesLoaded++;
    const totalImages = this.cmsNewsImages.length; // Total number of images
    if (this.imagesLoaded === totalImages) {
      this.updateImgWidth();
    }
  }

  // public imageNumber() {
  //   let number = 0;
  //   for (let img of this.images) {
  //     if (company.type.type === 'image') {
  //       number++;
  //     }
  //   }
  //   return number;
  // }

  updateImgWidth() {
    const carousel = this.$refs.carousel as HTMLElement;

    if (!carousel) {
      return;
    }

    const totalImages = carousel.querySelectorAll('img').length;
    const firstImg = carousel.querySelectorAll('img')[0] as HTMLElement;

    if (firstImg) {
      this.updateImageWidths(totalImages);
      this.firstImgWidth = firstImg.clientWidth;
    }
  }

  updateImageWidths(totalImages: number) {
    const carousel = this.$refs.carousel as HTMLElement;
    const imgs = carousel.querySelectorAll('img');

    const width = window.innerWidth;
    let divisionFactor: number;

    if (width <= 576) {
      divisionFactor = 1;
    } else if (width <= 1200) {
      divisionFactor = 2;
    } else {
      divisionFactor = Math.min(totalImages, 3);
    }

    imgs.forEach((img: HTMLElement) => {
      img.style.width = `calc(100% / ${divisionFactor})`;
    });
  }

  scrollPrev() {
    const carousel = this.$refs.carousel as HTMLElement;
    carousel.scrollLeft += -this.firstImgWidth;
  }

  scrollNext() {
    const carousel = this.$refs.carousel as HTMLElement;
    carousel.scrollLeft += this.firstImgWidth;
  }

  private viewerOptions: any = {
    movable: false,
    toolbar: {
      zoomIn: false,
      zoomOut: false,
      oneToOne: false,
      reset: false,
      prev: true,
      play: false,
      next: true,
      rotateLeft: false,
      rotateRight: false,
      flipHorizontal: false,
      flipVertical: false,
    },
    title: false,
  };

  // Method to open the viewer
  public openViewer(index: number): void {
    // const viewer = this.$el.querySelector('.images').$viewer;
    // viewer.view(index); // Open the viewer on the clicked image
  }

  public documentFileName(fileName) {
    let regex = /^doc_\w+_\d+_/;
    return fileName.replace(regex, '');
  }
}
