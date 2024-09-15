import { Component, Vue, Inject } from 'vue-property-decorator';
// import 'viewerjs/dist/viewer.css';
// import VueViewer from 'v-viewer';

import { ICmsPage } from '@/shared/model/cms-page.model';
import { CmsPageTypeOptions } from '@/shared/model/cms-page-type.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import CmsPageService from './cms-page.service';
import DocumentService from '../document/document.service';

@Component
export default class PageAbout extends Vue {
  @Inject('cmsPageService')
  private cmsPageService: () => CmsPageService;

  @Inject('documentService')
  private documentService: () => DocumentService;

  // private images: string[] = [
  //   'https://images.unsplash.com/photo-1577962917302-cd874c4e31d2?q=80&w=1632&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
  //   'https://images.unsplash.com/photo-1511174511562-5f7f18b874f8?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
  //   'https://images.unsplash.com/photo-1522542550221-31fd19575a2d?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
  //   'https://img.freepik.com/free-photo/close-up-businesswoman-pointing-digital-tablet_1098-1875.jpg?t=st=1719953856~exp=1719957456~hmac=2e0c02e7eb58fc01b1713dca0da277e4cb014872e5a2f7c7a5415e16f3641eb2&w=996',
  //   'https://img.freepik.com/free-photo/close-up-businessman-s-hand-using-digital-tablet_23-2148176180.jpg?t=st=1719953857~exp=1719957457~hmac=1b43938faabed2bc716e0a440b3d3f9424a5b66fb14fcfdb53ced5b425e16457&w=996',
  // ];

  // private documents = [
  //   {
  //     src: 'https://www.startech.org.rs/htdocs/Files/02145/StarTech_Brosura_210x210+3mm_26042023.pdf',
  //     title: 'StarTech: Innovation aiming for the starts',
  //   },
  //   {
  //     src: 'https://www.startech.org.rs/htdocs/Files/02144/Startech-brosura-ENG-web.pdf',
  //     title: 'StarTech: Od inovacija do zvezda',
  //   },
  //   {
  //     src: 'https://betterbusinesscontent.com/_ebooks/prince_albert/pdfs/eBook-Starting-a-Business-101.pdf',
  //     title: 'Starting a business 101',
  //   },
  // ];

  public firstImgWidth: number = 0;
  public imagesLoaded: number = 0;
  public totalItems = 0;
  public companyImagesArray: string[] = [];
  public currentLightboxImage: IDocument;
  public currentIndex = 0;
  public showMask = false;
  public previewImage = false;
  public totalImagesCount = 0;

  public cmsPage: ICmsPage | null = null;
  public cmsPageType = CmsPageTypeOptions.ABOUT;
  public cmsPageImages: IDocument[] = [];
  public cmsPageDocuments: IDocument[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.retrieveCmsPage(vm.cmsPageType);
    });
  }

  mounted() {
    this.updateImgWidth();
    window.addEventListener('resize', this.updateImgWidth);
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.updateImgWidth);
  }

  public retrieveCmsPage(type: string): void {
    this.cmsPageService()
      .getSpecificByType(type)
      .then(res => {
        this.cmsPage = res;
        this.cmsPageImages = this.cmsPage.documents.filter(doc => doc.type.type === DocumentTypeOptions.IMAGE);
        this.cmsPageDocuments = this.cmsPage.documents.filter(doc => doc.type.type === DocumentTypeOptions.DOCUMENT);
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
    this.currentLightboxImage = this.cmsPageImages[index];
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
      this.currentIndex = this.cmsPageImages.length - 1;
    }
    this.currentLightboxImage = this.cmsPageImages[this.currentIndex];
  }

  public next(): void {
    this.currentIndex = this.currentIndex + 1;
    if (this.currentIndex > this.cmsPageImages.length - 1) {
      this.currentIndex = 0;
    }
    this.currentLightboxImage = this.cmsPageImages[this.currentIndex];
  }

  // --- LIGHTBOX END ---

  onImageLoad() {
    this.imagesLoaded++;
    const totalImages = this.cmsPageImages.length; // Total number of images
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
