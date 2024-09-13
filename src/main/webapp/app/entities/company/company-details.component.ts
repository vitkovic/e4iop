import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { ICompanyRatingsDTO } from '@/shared/model/dto/company-ratings-dto';
import { IInquiryDTO } from '@/shared/model/dto/inquiry-dto.model';

import AccountService from '@/account/account.service';
import InquiryService from './inquiry.service';
import CompanyService from './company.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import CollaborationService from '../../entities/collaboration/collaboration.service';

import RatingBadge from './company-badge.vue';

// import VueViewer from 'v-viewer';

@Component({
  components: {
    RatingBadge,
  },
})
export default class CompanyDetails extends Vue {
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('inquiryService') private inquiryService: () => InquiryService;
  @Inject('collaborationService') private collaborationService: () => CollaborationService;

  private portalUser: IPortalUser = null;
  private hasAnyAuthorityValue = false;
  private isCompanyOwnerValue = false;

  public companyId: number | null = null;
  public company: ICompany = {};
  public inquiryDTO: IInquiryDTO | null = null;
  public collaborations: ICollaboration[] = [];
  public companyRatingsDTO: ICompanyRatingsDTO | null = null;

  public isModalFormIsValid: boolean = true;
  public inputSubject = {
    value: '',
    isValid: true,
  };
  public textareaContent = {
    value: '',
    isValid: true,
  };

  public firstImgWidth: number = 0;
  public imagesLoaded: number = 0;

  // Pagination parameters
  public isFetching = false;
  public itemsPerPage = 2;
  public queryCount: number | null = null;
  public page = 1;
  // public previousPage = 1;
  public propOrder = 'datetime';
  public reverse = false;
  public totalItems = 0;

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

  public companyImagesArray: string[] = [];
  public currentLightboxImage: string = '';
  public currentIndex = 0;
  public showMask = false;
  public previewImage = false;
  public totalImagesCount = 0;

  public modalComment: string = '';
  public modalRating: number = 0;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      // if (to.params.companyId) {
      //   vm.retrieveCompany(to.params.companyId);
      // }
    });
  }

  // ----------------------

  created() {
    let companyId = Number(this.$route.params.companyId);

    if (!isNaN(companyId)) {
      this.companyId = companyId;
      this.retrieveCompany(this.companyId);
      this.retrieveCollaborations();
      this.getCompanyRatings(this.companyId);
    }

    this.collaborations.forEach((_, index) => {
      this.$set(this.showFullComment, index, false);
    });

    // this.totalImagesCount = this.imagesGall.length;
  }

  mounted() {
    this.updateImgWidth();
    window.addEventListener('resize', this.updateImgWidth);
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.updateImgWidth);
  }

  // --- LIGHTBOX ---

  public async companyImages(): Promise<void> {
    this.companyImagesArray = []; // Clear any existing images
    for (let document of this.company.documents) {
      if (document.type.type === 'image') {
        const imageUrl = await this.companyService().retrieveImage(document.filename);
        this.companyImagesArray.push(imageUrl);
      }
    }
  }

  public onPreviewImage(index: number): void {
    this.showMask = true;
    this.previewImage = true;
    this.currentIndex = index;
    this.currentLightboxImage = this.companyImagesArray[index];
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
      this.currentIndex = this.companyImagesArray.length - 1;
    }
    this.currentLightboxImage = this.companyImagesArray[this.currentIndex];
  }

  public next(): void {
    this.currentIndex = this.currentIndex + 1;
    if (this.currentIndex > this.companyImagesArray.length - 1) {
      this.currentIndex = 0;
    }
    this.currentLightboxImage = this.companyImagesArray[this.currentIndex];
  }

  // --- LIGHTBOX END ---

  // --- SHARE ---

  shareOnFacebook(event: Event) {
    event.preventDefault();
    const currentUrl = window.location.href;

    const encodedUrl = encodeURIComponent(currentUrl);

    const shareUrl = 'https://www.facebook.com/sharer/sharer.php?u=' + encodedUrl;

    window.open(shareUrl, '_blank', 'width=600,height=400');
  }

  shareOnLinkedIn(event: Event) {
    event.preventDefault();

    const currentUrl = window.location.href;
    const documentTitle = document.title;

    const encodedUrl = encodeURIComponent(currentUrl);
    const encodedTitle = encodeURIComponent(documentTitle);

    const shareUrl = `https://www.linkedin.com/shareArticle?mini=true&url=${encodedUrl}&title=${encodedTitle}`;

    window.open(shareUrl, '_blank', 'width=600,height=400');
  }

  shareOnNewTwitter(event: Event) {
    event.preventDefault();

    const currentUrl = window.location.href;
    const documentTitle = document.title;

    const encodedUrl = encodeURIComponent(currentUrl);
    const encodedTitle = encodeURIComponent(documentTitle);

    const shareUrl = `https://twitter.com/intent/tweet?url=${encodedUrl}&text=${encodedTitle}`;

    window.open(shareUrl, 'twitter-share-dialog', 'width=800,height=600');
  }

  // copyToClipboard() {

  //   if (!navigator.clipboard) {
  //     console.error('Clipboard API not available');
  //     alert('Clipboard API is not supported in your browser or environment');
  //     return;
  //   }

  //   const url = window.location.href;

  //   navigator.clipboard
  //     .writeText(url)
  //     .then(() => {
  //       const errorText = this.$t('riportalApp.company.copyLink');
  //       this.$notify({
  //         text: errorText,
  //         type: 'info',
  //         duration: 1000,
  //       });
  //     })
  //     .catch(error => {
  //       console.error('Failed to copy URL to clipboard: ', error);
  //       alert('Failed to copy URL to clipboard');
  //     });
  // }

  copyToClipboard() {
    const url = window.location.href;

    // Create a temporary textarea element to hold the URL
    const textArea = document.createElement('textarea');
    textArea.value = url;
    document.body.appendChild(textArea);

    // Select the text in the textarea
    textArea.select();

    try {
      // Execute the copy command
      const successful = document.execCommand('copy');
      if (successful) {
        const successText = this.$t('riportalApp.company.copyLink');
        this.$notify({
          text: successText,
          type: 'info',
          duration: 1000,
        });
      } else {
        throw new Error('Copy command failed');
      }
    } catch (error) {
      console.error('Failed to copy URL to clipboard: ', error);
      alert('Failed to copy URL to clipboard');
    }

    // Remove the temporary textarea element
    document.body.removeChild(textArea);
  }

  // ---  SHARE END ---

  onImageLoad() {
    this.imagesLoaded++;
    const totalImages = this.imageNumber(); // Total number of images
    if (this.imagesLoaded === totalImages) {
      this.updateImgWidth();
    }
  }

  public imageNumber() {
    let number = 0;
    for (let company of this.company.documents) {
      if (company.type.type === 'image') {
        number++;
      }
    }
    return number;
  }

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

  public retrieveCompany(companyId) {
    this.companyService()
      .find(companyId)
      .then(res => {
        this.company = res;
        return this.companyImages(); // Call the method to fetch images
      })
      .then(() => {})
      .catch(error => {
        console.error('Error fetching company details or images:', error);
      });
  }

  public getCompanyRatings(companyId: number): void {
    this.collaborationService()
      .getCompanyRatings(companyId)
      .then(res => {
        this.companyRatingsDTO = res;
      });
  }

  public retrieveCollaborations(): void {
    if (this.companyId == null) {
      return;
    }

    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.collaborationService()
      .getPageOfRatedCollaborationsForCompany(this.companyId, paginationQuery)
      .then(
        res => {
          this.collaborations = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public previousPage(): void {
    if (this.page > 1) {
      this.page -= 1;
    } else {
      this.page = Math.ceil(this.totalItems / this.itemsPerPage);
    }

    this.retrieveCollaborations();
  }

  public nextPage(): void {
    if (this.page < this.totalItems / this.itemsPerPage) {
      this.page += 1;
    } else {
      this.page = 1;
    }

    this.retrieveCollaborations();
  }

  public previousState() {
    this.$router.go(-1);
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

  public isCompanyOwner(): boolean {
    const user = this.$store.getters.account;
    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          if (this.portalUser.company?.id === this.company.id) {
            this.isCompanyOwnerValue = true;
          }
        });
    }
    return this.isCompanyOwnerValue;
  }

  // Method to open the viewer
  public openViewer(index: number): void {
    // const viewer = this.$el.querySelector('.images').$viewer;
    // viewer.view(index); // Open the viewer on the clicked image
  }

  public prepareAdInquiry(): void {
    this.inquiryDTO = {
      advertisement: null,
      datetime: new Date(),
      subject: '',
      content: '',
      companySender: this.portalUser.company,
      companyReceiver: this.company,
      portalUserSender: this.portalUser,
    };

    if (<any>this.$refs.adInquiry) {
      (<any>this.$refs.adInquiry).show();
    }
  }

  public closeAdInquiry(): void {
    (<any>this.$refs.adInquiry).hide();
  }

  public validateModalForm() {
    this.isModalFormIsValid = true;
    if (this.inputSubject.value === '') {
      this.inputSubject.isValid = false;
      this.isModalFormIsValid = false;
    }

    if (this.textareaContent.value === '') {
      this.textareaContent.isValid = false;
      this.isModalFormIsValid = false;
    }
  }

  public clearValidity(input) {
    this[input].isValid = true;
  }

  public sendInquiry(): void {
    this.validateModalForm();

    if (!this.isModalFormIsValid) {
      return;
    }

    this.inquiryDTO.subject = this.inputSubject.value;
    this.inquiryDTO.content = this.textareaContent.value;

    if (this.inquiryDTO) {
      this.inquiryService()
        .create(this.inquiryDTO)
        .then(res => {
          const message = this.$t('riportalApp.company.notify.message') + this.company.name;
          this.$notify({
            text: message,
          });
        });
    }

    this.closeAdInquiry();
  }

  public getCompanyInitials(company: ICompany): string {
    return company.name
      .split(' ')
      .map(word => word[0])
      .join('')
      .toUpperCase();
  }

  public getCommentText(collaboration: any): string {
    return collaboration.companyOffer.id === this.company.id ? collaboration.commentRequest : collaboration.commentOffer;
  }

  public shortenedComment(comment: string): string {
    if (comment) {
      return comment.length <= 90 ? comment : comment.slice(0, 90) + '...';
    } else {
      return '';
    }
  }

  // public toggleComment(index: number): void {
  //   this.$set(this.showFullComment, index, !this.showFullComment[index]);
  // }

  public showModal(collaboration: any): void {
    this.modalComment = this.getCommentText(collaboration);
    this.modalRating =
      collaboration.companyOffer.id == this.company.id ? collaboration.ratingRequest.number : collaboration.ratingOffer.number;
    (this.$refs.testimonial as any).show();
  }

  public resetModalData(): void {
    this.modalComment = '';
    this.modalRating = 0;
  }

  public documentFileName(fileName) {
    let regex = /^doc_page_\d+_/;
    return fileName.replace(regex, '');
  }
}
