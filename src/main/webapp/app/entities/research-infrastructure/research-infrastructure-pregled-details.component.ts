import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import ResearchInfrastructureService from './research-infrastructure.service';

import RiCalendarService from '../ri-calendar/ri-calendar.service';
import { IRiCalendar, RiCalendar } from '@/shared/model/ri-calendar.model';

import UploadFilesService from '@/utility/upload/upload_service';

import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import momentPlugin from '@fullcalendar/moment';
import allLocales from '@fullcalendar/core/locales-all';

@Component
export default class ResearchInfrastructurePregledDetails extends Vue {
  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;
  public researchInfrastructure: IResearchInfrastructure = {};

  public riCalendar: IRiCalendar = new RiCalendar();
  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;

  public riCalendars: IRiCalendar[] = [];

  public currentLanguage = '';
  private pathInformation = null;
  public fullcalendarapi;
  public calevents = [];
  public calevent;

  public calendarOptions = {
    locales: allLocales,
    locale: this.getCalendarLanguageCode(),
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
    eventsSet: this.handleEvents,
    events: this.calevents,
    //eventAdd:this.addEventCalendar,
    //eventChange:this.updateEventCalendar,
    //eventRemove:this.deleteEventCalendar,
  };

  handleWeekendsToggle() {
    this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
  }
  handleDateSelect(selectInfo) {
    let calendarApi = this.fullcalendarapi;

    this.calevents = calendarApi.getEvents();
  }
  handleEventClick(clickInfo) {
    let event = clickInfo.event;
    //if (confirm(`Are you sure you want to change the event '${event.title}'`)) {
    this.calevent = event;
    this.openEvent();
    //}
  }
  public openEvent(): void {
    if (<any>this.$refs.calevent) {
      (<any>this.$refs.calevent).show(0, function () {
        //  console.log(this.$refs);
      });
      this.$nextTick(() => {
        //console.log(event);
        (<any>this.$refs.evtTitle).value = this.calevent.title;
        (<any>this.$refs.evtStartDate).value = this.calevent.startStr;
        (<any>this.$refs.evtEndDate).value = this.calevent.endStr;
      });
      //  (<any>this.$refs.evtTitle).value = event.title;
    }
  }
  public closeEventDialog(): void {
    (<any>this.$refs.calevent).hide();
  }
  handleEvents(events) {
    if (this.fullcalendarapi == null || typeof this.fullcalendarapi == 'undefined')
      this.fullcalendarapi = (<any>this.$refs.fullCalendar).getApi();
    this.fullcalendarapi.refetchEvents();
    window.dispatchEvent(new Event('resize'));
  }

  public closeDialog(): void {
    (<any>this.$refs.calendar).hide();
  }

  public openCalendar(): void {
    if (<any>this.$refs.calendar) {
      (<any>this.$refs.calendar).show();
      window.dispatchEvent(new Event('resize'));
    }
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researchInfrastructureId) {
        vm.retrieveResearchInfrastructure(to.params.researchInfrastructureId);
      }
      vm.getCalendar();
      if (to.params.pathInformation) {
        vm.pathInformation = to.params.pathInformation;
      }
    });
  }
  formLink(link, name): void {
    //UploadFilesService.getFile(link,name);
    UploadFilesService.getFile(link);
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
        this.calendarOptions.locale = this.getCalendarLanguageCode();
      }
    );
  }

  public loadImage(filename): string {
    return 'api/research-infrastructures/image/' + filename;
  }

  public retrieveResearchInfrastructure(researchInfrastructureId) {
    this.researchInfrastructureService()
      .findPregled(researchInfrastructureId)
      .then(res => {
        this.researchInfrastructure = res;
      });
  }
  public getCalendar() {
    this.riCalendarService()
      .retrieve()
      .then(res => {
        this.riCalendars = res.data;
        this.calevents = [];
        let cal = {};
        //console.log("calevents");
        for (const rical of this.riCalendars) {
          cal = { id: rical.id, title: rical.calComment, start: rical.startTime, end: rical.endTime, allDay: true };
          this.calevents.push(cal);
        }
        this.calendarOptions.events = this.calevents;
      });
  }

  public previousState() {
    if (this.pathInformation) {
      this.$router.push({ name: this.pathInformation.path, params: { search: this.pathInformation.search } });
    } else this.$router.go(-1);
  }

  /**
   * Determine the language code for calendar locale.
   * @returns String of language code used for calendar locale. Default is for Serbian Latin.
   */
  public getCalendarLanguageCode(): string {
    var currentLanguage: string = this.$store.getters.currentLanguage;

    // Default is for Serbian Latin ('sr')
    var calendarLanguage: string = 'sr';

    if (currentLanguage === 'en') {
      calendarLanguage = 'en';
    } else if (currentLanguage === 'src') {
      calendarLanguage = 'sr-cyrl';
    }

    return calendarLanguage;
  }
}
