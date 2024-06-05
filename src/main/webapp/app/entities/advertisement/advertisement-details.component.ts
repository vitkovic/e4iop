import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { ICompany } from '@/shared/model/company.model';
import { IMeeting } from '@/shared/model/meeting.model';

import InquiryService from './inquiry.service';
import AccountService from '@/account/account.service';
import AdvertisementService from './advertisement.service';
import CollaborationService from '../../entities/collaboration/collaboration.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import MeetingService from '../../entities/meeting/meeting.service';
import CompanyService from '../../entities/company/company.service';

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
  advertisement: null,
};

@Component
export default class AdvertisementDetails extends Vue {
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('collaborationService') private collaborationService: () => CollaborationService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('inquiryService') private inquiryService: () => InquiryService;
  @Inject('meetingService') private meetingService: () => MeetingService;
  @Inject('companyService') private companyService: () => CompanyService;

  public portalUser: IPortalUser = null;
  private hasAnyAuthorityValue = false;
  private isCompanyOwnerValue = false;

  public advertisement: IAdvertisement = {};
  public inquiryDTO: InquiryDTO | null = null;
  public newMeeting: IMeeting | null = null;
  public meetingEvent = { ...DEFAULT_MEETING_EVENT };
  public companiesSearch: ICompany[] = [];
  public companiesMeetingParticipants: ICompany[] = [];

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

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementId) {
        vm.retrieveAdvertisement(to.params.advertisementId);
      }
    });
  }

  public retrieveAdvertisement(advertisementId) {
    this.advertisementService()
      .find(advertisementId)
      .then(res => {
        this.advertisement = res;
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

  public isCompanyOwner(): boolean {
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
    return this.isCompanyOwnerValue;
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
      .createMeetingWithParticipants(newMeeting, organizerId, participantIds)
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
}
