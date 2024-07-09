import { Component, Vue, Inject } from 'vue-property-decorator';
import { required, email } from 'vuelidate/lib/validators';

import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { ICompany } from '@/shared/model/company.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IMeeting } from '@/shared/model/meeting.model';
import { ICompanyRatingsDTO } from '@/shared/model/dto/company-ratings-dto';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import { AdvertisementSupporterStatusOptions } from '@/shared/model/advertisement-supporter-status.model';

import InquiryService from './inquiry.service';
import AccountService from '@/account/account.service';
import AdvertisementService from './advertisement.service';
import CollaborationService from '../../entities/collaboration/collaboration.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import MeetingService from '../../entities/meeting/meeting.service';
import CompanyService from '../../entities/company/company.service';
import AdvertisementSupporterService from '../advertisement-supporter/advertisement-supporter.service';

interface InquiryDTO {
  advertisement: IAdvertisement;
  datetime: Date;
  subject: '';
  content: '';
  companySender: ICompany;
  companyReceiver: ICompany;
  portalUserSender: IPortalUser;
}

interface MeetingEvent {
  id: number;
  date: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  title: string;
  description: string;
  location: string;
  advertisement: IAdvertisement;
}

const DEFAULT_MEETING_EVENT: MeetingEvent = {
  id: 0,
  date: '',
  startDate: '',
  endDate: '',
  startTime: '08:00:00',
  endTime: '09:00:00',
  title: '',
  description: '',
  location: '',
  advertisement: null,
};

const validations: any = {
  nonB2BMeetingParticipantEmail: { required, email },
};

@Component({
  validations,
})
export default class AdvertisementDetails extends Vue {
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('collaborationService') private collaborationService: () => CollaborationService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('inquiryService') private inquiryService: () => InquiryService;
  @Inject('meetingService') private meetingService: () => MeetingService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;

  public portalUser: IPortalUser = null;
  private hasAnyAuthorityValue = false;
  private isCompanyOwnerValue = false;

  public advertisement: IAdvertisement = {};
  public inquiryDTO: InquiryDTO | null = null;
  public newMeeting: IMeeting | null = null;
  public meetingEvent = { ...DEFAULT_MEETING_EVENT };
  public companiesSearch: ICompany[] = [];
  public companiesMeetingParticipants: ICompany[] = [];
  public nonB2BParticipantsEmails: string[] = [];
  public nonB2BMeetingParticipantEmail: string | null = null;
  public isEmailValid = true;
  public companyRatingsDTO: ICompanyRatingsDTO | null = null;
  public collaborations: ICollaboration[] = [];
  public advertisementSupporterStatusOptions = AdvertisementSupporterStatusOptions;

  public companySearchText = '';
  public showCompaniesSearch = false;
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
  public advertisementImagesArray: string[] = [];
  public currentLightboxImage: string = '';
  public currentIndex = 0;
  public showMask = false;
  public previewImage = false;
  public totalImagesCount = 0;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementId) {
        vm.retrieveAdvertisement(to.params.advertisementId);
      }
    });
  }

  created() {}

  mounted() {
    this.updateImgWidth();
    window.addEventListener('resize', this.updateImgWidth);
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.updateImgWidth);
  }

  // GALLERY LOGIC

  public async companyImages(): Promise<void> {
    this.advertisementImagesArray = []; // Clear any existing images
    for (let document of this.advertisement.documents) {
      if (document.type.type === 'image') {
        const imageUrl = await this.advertisementService().retrieveImage(document.filename);
        this.advertisementImagesArray.push(imageUrl);
      }
    }
  }

  // --- LIGHTBOX ---

  public onPreviewImage(index: number): void {
    this.showMask = true;
    this.previewImage = true;
    this.currentIndex = index;
    this.currentLightboxImage = this.advertisementImagesArray[index];
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
      this.currentIndex = this.advertisementImagesArray.length - 1;
    }
    this.currentLightboxImage = this.advertisementImagesArray[this.currentIndex];
  }

  public next(): void {
    this.currentIndex = this.currentIndex + 1;
    if (this.currentIndex > this.advertisementImagesArray.length - 1) {
      this.currentIndex = 0;
    }
    this.currentLightboxImage = this.advertisementImagesArray[this.currentIndex];
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

  copyToClipboard() {
    const url = window.location.href;

    navigator.clipboard
      .writeText(url)
      .then(() => {
        const errorText = this.$t('riportalApp.company.copyLink');
        this.$notify({
          text: errorText,
          type: 'info',
          duration: 1000,
        });
      })
      .catch(error => {
        console.error('Failed to copy URL to clipboard: ', error);
        alert('Failed to copy URL to clipboard');
      });
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
    for (let advertisement of this.advertisement.documents) {
      if (advertisement.type.type === 'image') {
        number++;
      }
    }
    return number;
  }

  updateImgWidth() {
    console.log('Update image width called');
    const carousel = this.$refs.carousel as HTMLElement;
    const totalImages = carousel.querySelectorAll('img').length;
    const firstImg = carousel.querySelectorAll('img')[0] as HTMLElement;

    if (firstImg) {
      this.updateImageWidths(totalImages);
      this.firstImgWidth = firstImg.clientWidth;
      console.log('First image width:', this.firstImgWidth);
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

  // GALLERY LOGIC END

  public retrieveAdvertisement(advertisementId) {
    this.advertisementService()
      .find(advertisementId)
      .then(res => {
        this.advertisement = res;
        this.checkCompanyOwnership();
        this.getCompanyRatings(this.advertisement.company.id);
        this.retrieveAdvertisementSupporters(this.advertisement.id);
        return this.companyImages();
      })
      .catch(error => {
        console.error('Error fetching company details or images:', error);
      });
  }

  public retrieveAdvertisementSupporters(advertisementId): void {
    this.advertisementSupporterService()
      .retrieveAdvertisementSupporters(advertisementId)
      .then(res => {
        this.advertisement.advertisementSupporters = res;
      });
  }

  public getCompanyRatings(companyId: number): void {
    this.collaborationService()
      .getCompanyRatings(companyId)
      .then(res => {
        this.companyRatingsDTO = res;
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

  public getExpirationDate(advertisement: IAdvertisement): Date {
    const expirationDate = new Date(advertisement.activationDatetime);
    expirationDate.setMonth(expirationDate.getMonth() + Number(advertisement.duration.duration));

    return expirationDate;
  }

  get isCompanyOwner(): boolean {
    return this.isCompanyOwnerValue;
  }

  public checkCompanyOwnership(): void {
    const user = this.$store.getters.account;

    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          if (this.portalUser.company?.id === this.advertisement.company.id) {
            this.isCompanyOwnerValue = true;
          }
        });
    }
  }

  public prepareAdInquiry(instance: IAdvertisement): void {
    this.inquiryDTO = {
      advertisement: this.advertisement,
      datetime: new Date(),
      subject: '',
      content: '',
      companySender: this.portalUser.company,
      companyReceiver: this.advertisement.company,
      portalUserSender: this.portalUser,
    };

    if (<any>this.$refs.adInquiry) {
      (<any>this.$refs.adInquiry).show();
    }
  }

  public closeAdInquiry(): void {
    (<any>this.$refs.adInquiry).hide();
  }

  public prepareAdCollaboration(instance: IAdvertisement): void {
    if (<any>this.$refs.adCollaboration) {
      (<any>this.$refs.adCollaboration).show();
    }
  }

  public closeAdCollaboration(): void {
    (<any>this.$refs.adCollaboration).hide();
  }

  public startAdCollaboration(): void {
    this.collaborationService()
      .createCollaborationForAdvertisement(this.advertisement.id)
      .then(res => {
        const message = 'Vaš zahtev za saradnju za oglas "' + this.advertisement.title + '" je poslat.';
        this.$notify({
          text: message,
        });
      });

    this.closeAdCollaboration();
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
          const message = 'Vaš upit za oglas "' + this.advertisement.title + '" je poslat.';
          this.$notify({
            text: message,
          });
        });
    }

    this.closeAdInquiry();
  }

  public prepareCreateMeetingModal(advertisement: IAdvertisement): void {
    this.companiesSearch = [];
    this.companySearchText = '';
    this.companiesMeetingParticipants = [];
    this.nonB2BParticipantsEmails = [];
    this.nonB2BMeetingParticipantEmail = null;

    this.meetingEvent = { ...DEFAULT_MEETING_EVENT };
    this.meetingEvent.startDate = this.formatDateStringFromDate(new Date());
    this.meetingEvent.endDate = this.formatDateStringFromDate(new Date());
    this.meetingEvent.advertisement = advertisement;

    const user = this.$store.getters.account;
    if (!user) {
      const message = 'Sistemska greška! Trenutno nije moguće zakazati sastanak.';
      this.$notify({
        text: message,
      });
      return;
    }

    this.portalUserService()
      .findByUserId(user.id)
      .then(res => {
        this.portalUser = res;
      });

    (<any>this.$refs.createMeetingModal).show();
  }

  public createMeeting(): void {
    let newMeeting: IMeeting = {
      id: null,
      title: this.meetingEvent.title,
      description: this.meetingEvent.description,
      location: this.meetingEvent.location,
      datetime: new Date(),
      datetimeStart: this.combineDateAndTime(this.meetingEvent.startDate, this.meetingEvent.startTime),
      datetimeEnd: this.combineDateAndTime(this.meetingEvent.endDate, this.meetingEvent.endTime),
      advertisement: this.meetingEvent.advertisement,
      isAcepted: false,
      comment: null,
      notes: null,
      meetingParticipants: null,
      company: this.portalUser.company,
      portalUserOrganizer: null,
      type: null,
    };

    const organizerId = this.portalUser.company.id;
    let participantIds = this.companiesMeetingParticipants.map(company => company.id);
    participantIds.push(this.advertisement.company.id);

    this.meetingService()
      .createMeetingWithParticipants(newMeeting, organizerId, participantIds, this.nonB2BParticipantsEmails)
      .then(res => {
        const message = 'Sastanak je zakazan.';
        this.$notify({
          text: message,
        });
      })
      .catch(err => {
        console.log(err);
      });

    this.closeCreateMeetingModal();
  }

  public closeCreateMeetingModal() {
    (<any>this.$refs.createMeetingModal).hide();
  }

  public prepareCollaborationsModal(): void {
    this.collaborationService()
      .getAllRatedCollaborationsForCompany(this.advertisement.company.id)
      .then(res => {
        this.collaborations = res;

        if (<any>this.$refs.collaborationsModal) {
          (<any>this.$refs.collaborationsModal).show();
        }
      });
  }

  public closeCollaborationsModal() {
    (<any>this.$refs.collaborationsModal).hide();
  }

  public combineDateAndTime(dateString: string, timeString: string): Date {
    const date = new Date(dateString);
    if (isNaN(date.getTime())) {
      throw new Error('Invalid date string');
    }

    const [hours, minutes, seconds] = timeString.split(':').map(Number);
    if (hours === undefined || minutes === undefined || seconds === undefined) {
      throw new Error('Invalid time string format. Expected format is HH:MM:SS');
    }

    date.setHours(hours, minutes, seconds, 0);
    return date;
  }

  /**
   * Format Date object to date string 'yyyy-MM-dd'.
   * Needed for initializing v-model of b-form-datepicker.
   */
  public formatDateStringFromDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  public updateMeetingEndDate() {
    if (this.meetingEvent.endDate < this.meetingEvent.startDate) {
      this.meetingEvent.endDate = this.meetingEvent.startDate;
    }
  }

  public getCompaniesBySearchText(): void {
    // this.showCompaniesSearch = true;

    // This is neccessary as Java side, for some reason,
    // cannot handle empty string in path variable.
    // Java side will check if string only consists of blanks,
    // and choose appropriate repository method to call.
    let searchText = this.companySearchText;
    if (this.companySearchText === '') {
      searchText = ' ';
    }

    let excludedIds = this.companiesMeetingParticipants.map(company => company.id);
    excludedIds.push(this.portalUser.company.id);
    excludedIds.push(this.advertisement.company.id);

    this.companyService()
      .getAllAutocompleteByNameWithoutExlcuded(searchText, excludedIds)
      .then(res => {
        this.companiesSearch = res.data;
      });
  }

  public addToCompaniesMeetingParticipants(company: ICompany) {
    this.companiesMeetingParticipants.push(company);
    this.companySearchText = '';
  }

  public removeFromCompaniesMeetingParticipants(company: ICompany) {
    let index = this.companiesMeetingParticipants.indexOf(company);
    if (index > -1) {
      this.companiesMeetingParticipants.splice(index, 1);
    }
  }

  public isInCompaniesMeetingParticipants(company: ICompany): boolean {
    let index = this.companiesMeetingParticipants.indexOf(company);
    console.log('HELLOO!!!');
    console.log(index > -1);
    return index > -1;
  }

  public toggleSearchList(event, toggleControl: string) {
    if (event.type === 'focusout') {
      this[toggleControl] = false;
    } else if (event.type === 'focusin') {
      this[toggleControl] = true;
      this.getCompaniesBySearchText();
    } else {
      this[toggleControl] = !this[toggleControl];
    }
  }

  public getCompanyInitials(company: ICompany): string {
    return company.name
      .split(' ')
      .map(word => word[0])
      .join('')
      .toUpperCase();
  }

  public addNonB2BMeetingParticipant(): void {
    this.$v.nonB2BMeetingParticipantEmail.$touch();
    if (this.$v.nonB2BMeetingParticipantEmail.$invalid) {
      this.isEmailValid = false;
      return;
    }

    if (this.nonB2BMeetingParticipantEmail) {
      if (!this.nonB2BParticipantsEmails.includes(this.nonB2BMeetingParticipantEmail)) {
        this.nonB2BParticipantsEmails.push(this.nonB2BMeetingParticipantEmail);
      }
    }

    this.isEmailValid = true;
    this.nonB2BMeetingParticipantEmail = null;
  }

  public removeNonB2BMeetingParticipant(email: string): void {
    const index = this.nonB2BParticipantsEmails.indexOf(email);
    if (index !== -1) {
      this.nonB2BParticipantsEmails.splice(index, 1);
    }
  }
}
