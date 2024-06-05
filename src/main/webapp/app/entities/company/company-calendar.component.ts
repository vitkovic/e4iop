import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompany } from '@/shared/model/company.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IMeeting } from '@/shared/model/meeting.model';
import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';

import CompanyService from './company.service';
import MeetingService from '@/entities/meeting/meeting.service';
import MeetingParticipantService from '@/entities/meeting-participant/meeting-participant.service';

import FullCalendar from '@fullcalendar/vue';
import allLocales from '@fullcalendar/core/locales-all';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';

interface MeetingEvent {
  id: number;
  date: string;
  startTime: string;
  endTime: string;
  title: string;
  description: string;
  location: string;
  startDate: string;
  endDate: string;
  organizer: IMeetingParticipant | null;
  advertiser: IMeetingParticipant | null;
  allParticipants: IMeetingParticipant[];
  otherParticipants: IMeetingParticipant[];
  advertisement: IAdvertisement;
}

const DEFAULT_MEETING_EVENT: MeetingEvent = {
  id: 0,
  date: '',
  startTime: '08:00:00',
  endTime: '09:00:00',
  title: '',
  description: '',
  location: '',
  startDate: '',
  endDate: '',
  organizer: null,
  advertiser: null,
  allParticipants: [],
  otherParticipants: [],
  advertisement: null,
};

@Component
export default class CompanyCalendar extends Vue {
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('meetingService') private meetingService: () => MeetingService;
  @Inject('meetingParticipantService') private meetingParticipantService: () => MeetingParticipantService;

  public company: ICompany | null = null;
  public companyId: number | null = null;
  public companyMeetingParticipants: IMeetingParticipant[] = [];
  public companyMeetings: IMeeting[] = [];
  public companiesSearch: ICompany[] = [];
  public companiesMeetingParticipants: ICompany[] = [];

  // public meetings: IMeeting[] = [];
  public newMeeting: IMeeting | null = null;
  public meetingEvent = { ...DEFAULT_MEETING_EVENT };
  public meetingToRemove = { ...DEFAULT_MEETING_EVENT };
  public selectedEvent = { ...DEFAULT_MEETING_EVENT };
  public fullcalendarapi;
  public calevents = [];
  eventGuid = 0;
  public isCalendarPopulated = false;
  public companySearchText = '';
  public showCompaniesSearch = false;

  public calendarOptions = {
    locales: allLocales,
    locale: this.getCalendarLanguageCode(),
    // locale: 'en',
    plugins: [
      dayGridPlugin,
      timeGridPlugin,
      interactionPlugin, // needed for dateClick
      momentPlugin,
    ],
    // Formating views in moment string is necessery in order for Serbian Latin to show correctly
    views: {
      dayGrid: {
        // options apply to dayGridMonth, dayGridWeek, and dayGridDay views
        titleFormat: 'MMMM YYYY.',
        dayHeaderFormat: 'ddd',
      },
      timeGrid: {
        // options apply to timeGridWeek and timeGridDay views
      },
      week: {
        // options apply to dayGridWeek and timeGridWeek views
        titleFormat: 'DD. MMM YYYY.',
        dayHeaderFormat: 'ddd DD.MM.',
      },
      day: {
        // options apply to dayGridDay and timeGridDay views
        titleFormat: 'DD. MMMM YYYY.',
        dayHeaderFormat: 'dddd',
      },
    },
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay',
    },
    height: '100%',
    initialView: 'dayGridMonth',
    //initialEvents: INITIAL_EVENTS, // alternatively, use the `events` setting to fetch from a feed
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    weekends: true,
    select: this.handleDateSelect,
    eventClick: this.handleEventClick,
    // eventsSet: this.handleEvents,
    events: this.calevents,
    /*
    eventAdd: this.addCalendarEvent,
    eventChange: this.updateCalendarEvent,
    eventRemove:this.removeCalendarEvent,
    */
    eventDidMount: arg => {
      arg.el.addEventListener('contextmenu', jsEvent => {
        //  console.log(arg.event);
        //   console.log(this.calevent);
        jsEvent.preventDefault();
        // this.deleteEvent(arg.event);
      });
      // console.log(this.riCalendars);
    },
    viewDidMount: this.populateCalendar,
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.companyId = to.params.companyId;
        vm.retrieveCompany(vm.companyId);
        // vm.populateCalendar();
      }
    });
  }

  public retrieveCompany(companyId) {
    this.companyService()
      .find(companyId)
      .then(res => {
        this.company = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }

  created(): void {
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.calendarOptions.locale = this.getCalendarLanguageCode();
      }
    );
  }

  mounted() {}

  public populateCalendar() {
    if (this.isCalendarPopulated) {
      return;
    }

    this.fullcalendarapi = this.$refs.fullCalendar.getApi();
    this.fullcalendarapi.removeAllEvents();

    this.meetingParticipantService()
      .findAllNotRemovedForCompany(this.companyId)
      .then(res => {
        this.companyMeetingParticipants = res;
        this.companyMeetings = this.companyMeetingParticipants.map(participant => participant.meeting);

        this.companyMeetings.forEach(meeting => {
          let event = this.fullcalendarapi.addEvent({
            // date: '',
            id: meeting.id,
            start: meeting.datetimeStart,
            end: meeting.datetimeEnd,
            // startTime: '08:00:00',
            // endTime: '09:00:00',
            title: meeting.title,
            description: meeting.description,
            location: meeting.location,
            advertisement: meeting.advertisement,
          });
          // this.calevents.push(event);
        });
      });

    // this.meetingService()
    // .findAllForCompany(this.companyId)
    // .then(res => {
    //   this.meetings = res.data;
    //   this.meetings.forEach(meeting => {
    //     let event = this.fullcalendarapi.addEvent({
    //       // date: '',
    //       id: meeting.id,
    //       start: meeting.datetimeStart,
    //       end: meeting.datetimeEnd,
    //       // startTime: '08:00:00',
    //       // endTime: '09:00:00',
    //       title: meeting.title,
    //       description: meeting.description,
    //     });
    //     // this.calevents.push(event);

    //   });
    // })

    this.isCalendarPopulated = true;
  }

  public setCalendar() {
    this.fullcalendarapi = (<any>this.$refs.fullCalendar).getApi();
  }

  handleDateSelect(selectInfo) {
    this.meetingEvent = { ...DEFAULT_MEETING_EVENT };
    this.meetingEvent.date = selectInfo.startStr;

    this.companiesSearch = [];
    this.companySearchText = '';
    this.companiesMeetingParticipants = [];

    this.meetingEvent = { ...DEFAULT_MEETING_EVENT };
    this.meetingEvent.startDate = selectInfo.startStr;

    if (selectInfo.view.type === 'dayGridMonth') {
      this.meetingEvent.endDate = this.getPreviousDate(selectInfo.endStr);
    } else {
      this.meetingEvent.endDate = selectInfo.endStr;
    }

    // this.meetingEvent.advertisement = advertisement;
    (<any>this.$refs.createMeetingModal).show();
  }

  handleEventClick(clickInfo) {
    this.selectedEvent = { ...DEFAULT_MEETING_EVENT };
    this.selectedEvent.id = clickInfo.event.id;
    this.selectedEvent.startDate = clickInfo.event.start.toLocaleDateString();
    this.selectedEvent.endDate = clickInfo.event.end.toLocaleDateString();
    this.selectedEvent.startTime = clickInfo.event.start.toLocaleTimeString(undefined, { hour: '2-digit', minute: '2-digit' });
    this.selectedEvent.endTime = clickInfo.event.end.toLocaleTimeString(undefined, { hour: '2-digit', minute: '2-digit' });
    this.selectedEvent.title = clickInfo.event.title;
    this.selectedEvent.description = clickInfo.event.extendedProps.description;
    this.selectedEvent.location = clickInfo.event.extendedProps.location;
    this.selectedEvent.advertisement = clickInfo.event.extendedProps.advertisement;

    const meetingId = clickInfo.event.id;

    this.meetingParticipantService()
      .findAllForMeeting(meetingId)
      .then(res => {
        this.selectedEvent.allParticipants = res;
        this.selectedEvent.otherParticipants = structuredClone(this.selectedEvent.allParticipants);

        this.selectedEvent.organizer = this.selectedEvent.allParticipants.find(participant => participant.isOrganizer === true);

        if (this.selectedEvent.advertisement) {
          this.selectedEvent.advertiser = this.selectedEvent.allParticipants.find(
            participant => participant.company.id === this.selectedEvent.advertisement.company.id
          );
        }

        if (this.selectedEvent.organizer) {
          this.selectedEvent.otherParticipants = this.selectedEvent.otherParticipants.filter(
            participant => participant.id !== this.selectedEvent.organizer.id
          );
        }

        if (this.selectedEvent.advertiser) {
          this.selectedEvent.otherParticipants = this.selectedEvent.otherParticipants.filter(
            participant => participant.id !== this.selectedEvent.advertiser.id
          );
        }

        (<any>this.$refs.viewMeetingModal).show();
      });
  }

  public closeCreateMeetingModal(): void {
    (<any>this.$refs.createMeetingModal).hide();
  }

  public closeViewMeetingModal(): void {
    (<any>this.$refs.viewMeetingModal).hide();
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
      company: this.company,
      portalUserOrganizer: null,
      type: null,
    };

    const organizerId = this.companyId;
    let participantIds = this.companiesMeetingParticipants.map(company => company.id);
    // participantIds.push(this.advertisement.company.id);

    this.meetingService()
      .createMeetingWithParticipants(newMeeting, organizerId, participantIds)
      .then(res => {
        this.isCalendarPopulated = false;
        this.populateCalendar();

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

  public prepareAceeptMeetingModal(): void {
    if (<any>this.$refs.acceptMeetingModal) {
      (<any>this.$refs.acceptMeetingModal).show();
    }
  }

  public acceptMeeting(): void {
    this.meetingParticipantService()
      .acceptMeetingForCompany(this.selectedEvent.id, this.companyId)
      .then(res => {
        this.isCalendarPopulated = false;
        this.populateCalendar();

        const message = 'Potvrdili ste poziv za sastanak - ' + this.selectedEvent.title;
        this.$notify({
          text: message,
        });
      });

    this.closeAcceptMeetingModal();
    this.closeViewMeetingModal();
  }

  public closeAcceptMeetingModal(): void {
    (<any>this.$refs.acceptMeetingModal).hide();
  }

  public prepareRemoveMeetingModal(): void {
    if (<any>this.$refs.removeMeetingModal) {
      (<any>this.$refs.removeMeetingModal).show();
    }
  }

  public removeMeeting(): void {
    this.meetingParticipantService()
      .removeMeetingForCompany(this.selectedEvent.id, this.companyId)
      .then(res => {
        this.isCalendarPopulated = false;
        this.populateCalendar();

        const message = 'Sastanak ' + this.selectedEvent.title + ' je uklonjen iz kalendara';
        this.$notify({
          text: message,
        });
      });

    this.closeRemoveMeetingModal();
    this.closeViewMeetingModal();
  }

  public closeRemoveMeetingModal(): void {
    (<any>this.$refs.removeMeetingModal).hide();
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

  public getCalendarLanguageCode(): string {
    const currentLanguage = this.$store.getters.currentLanguage;

    // Default is for Serbian Latin ('sr')
    let calendarLanguage = 'sr';

    if (currentLanguage === 'en') {
      calendarLanguage = 'en';
    } else if (currentLanguage === 'src') {
      calendarLanguage = 'sr-cyrl';
    }

    return calendarLanguage;
  }

  public getCompanyInitials(company: ICompany): string {
    return company.name
      .split(' ')
      .map(word => word[0])
      .join('')
      .toUpperCase();
  }

  public isMeetingAcceptedForCurrentCompany(selectedEvent: MeetingEvent): boolean {
    if (selectedEvent?.organizer?.company?.id == this.companyId) {
      return true;
    }

    if (selectedEvent?.advertiser?.company?.id == this.companyId) {
      return selectedEvent.advertiser.hasAccepted;
    }

    if (selectedEvent?.otherParticipants) {
      return selectedEvent.otherParticipants.some(participant => participant?.company?.id == this.companyId && participant.hasAccepted);
    }

    return false;
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
    excludedIds.push(this.company.id);
    // excludedIds.push(this.advertisement.company.id);

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

  /**
   * Get date for previous day in format 'yyyy-MM-dd'.
   * Needed for setting v-model of b-form-datepicker for end date
   * in the case of dayGridMonth view, because endStr parameter of fullCalendar event
   * will return the following date from the one selected.
   */
  public getPreviousDate(dateString: string): string {
    const date = new Date(dateString);
    date.setDate(date.getDate() - 1);

    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  public updateMeetingEndDate() {
    if (this.meetingEvent.endDate < this.meetingEvent.startDate) {
      this.meetingEvent.endDate = this.meetingEvent.startDate;
    }
  }
}
