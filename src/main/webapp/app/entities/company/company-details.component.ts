import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompany } from '@/shared/model/company.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AccountService from '@/account/account.service';
import InquiryService from './inquiry.service';
import CompanyService from './company.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';

import RatingBadge from './company-badge.vue';

interface InquiryDTO {
  advertisement: IAdvertisement;
  datetime: Date;
  subject: '';
  content: '';
  companySender: ICompany;
  companyReceiver: ICompany;
  portalUserSender: IPortalUser;
}

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

  private portalUser: IPortalUser = null;
  private hasAnyAuthorityValue = false;
  private isCompanyOwnerValue = false;

  public company: ICompany = {};
  public inquiryDTO: InquiryDTO | null = null;

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
  public companies = [
    {
      name: 'QCDS Consulting',
      description: 'Analiza faktora zastoja masina',
      rating: 4,
      role: 'Oglasivac',
      details:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    },
    {
      name: 'B.2.B Company',
      description: 'Ispitivanje kostrukcija',
      rating: 2,
      role: 'Trazilac',
      details:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    },
    {
      name: 'Mehanika D.O.O.',
      description: 'Ispitivanje trzista',
      rating: 3,
      role: 'Oglasivac',
      details:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    },
  ];

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

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.retrieveCompany(to.params.companyId);
      }
    });
  }

  created() {
    if (this.$route.params.companyId) {
      this.retrieveCompany(this.$route.params.companyId);
    }
  }

  // ----------------------

  mounted() {
    this.updateImgWidth();
    window.addEventListener('resize', this.updateImgWidth);
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.updateImgWidth);
  }

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
    const totalImages = carousel.querySelectorAll('img').length;
    const firstImg = carousel.querySelectorAll('img')[0] as HTMLElement;

    if (firstImg) {
      this.firstImgWidth = firstImg.clientWidth;
      this.updateImageWidths(totalImages);
      // console.log(this.firstImgWidth);
    }
  }

  updateImageWidths(totalImages: number) {
    const carousel = this.$refs.carousel as HTMLElement;
    const imgs = carousel.querySelectorAll('img');

    const width = window.innerWidth;
    let divisionFactor: number;

    if (width <= 576) {
      divisionFactor = 1;
    } else if (width <= 768) {
      divisionFactor = 2;
    } else if (width <= 1200) {
      divisionFactor = 3;
    } else {
      divisionFactor = Math.min(totalImages, 5);
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

  // ----------------------------

  // mounted() {
  //   this.updateRowWidth();
  // }

  // updateRowWidth() {
  //   this.rowWidth = (this.$refs.imageRow as HTMLElement).scrollWidth;
  // }

  // slideLeft() {
  //   if (this.slideAmount > 0) {
  //     this.slideAmount--;
  //     this.rowOffset = -this.slideAmount * this.rowWidth;
  //   }
  // }

  // slideRight() {
  //   if (this.slideAmount < this.images.length - 5) {
  //     this.slideAmount++;
  //     this.rowOffset = -this.slideAmount * this.rowWidth;
  //   }
  // }

  // get imagesToShow() {
  //   return this.images.slice(this.slideAmount, this.slideAmount + 5);
  // }

  // ---------------------------------

  public retrieveCompany(companyId) {
    this.companyService()
      .find(companyId)
      .then(res => {
        this.company = res;
        console.log('Current company:', this.company);
      });
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
    console.log(this.inquiryDTO);

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
          const message = 'Vaša poruka je poslata kompaniji ' + this.company.name;
          this.$notify({
            text: message,
          });
        });
    }

    this.closeAdInquiry();
  }
}
