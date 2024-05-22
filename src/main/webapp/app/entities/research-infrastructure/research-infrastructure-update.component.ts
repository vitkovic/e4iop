import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, requiredUnless, requiredIf } from 'vuelidate/lib/validators';

import RiServiceService from '../ri-service/ri-service.service';
import { IRiService } from '@/shared/model/ri-service.model';

import RiCalendarService from '../ri-calendar/ri-calendar.service';
import { IRiCalendar, RiCalendar } from '@/shared/model/ri-calendar.model';

import RiStatusService from '../ri-status/ri-status.service';
import { IRiStatus } from '@/shared/model/ri-status.model';

import RiAccessModeService from '../ri-access-mode/ri-access-mode.service';
import { IRiAccessMode } from '@/shared/model/ri-access-mode.model';

import RiAccessTypeService from '../ri-access-type/ri-access-type.service';
import { IRiAccessType } from '@/shared/model/ri-access-type.model';

import RiCategoryService from '../ri-category/ri-category.service';
import { IRiCategory } from '@/shared/model/ri-category.model';

import ScientificSubdomainService from '../scientific-subdomain/scientific-subdomain.service';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';

import RiSubcategoryService from '../ri-subcategory/ri-subcategory.service';
import { IRiSubcategory } from '@/shared/model/ri-subcategory.model';

import RiResearchOrganizationService from '../ri-research-organization/ri-research-organization.service';
import { IRiResearchOrganization } from '@/shared/model/ri-research-organization.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IResearchInfrastructure, ResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import ResearchInfrastructureService from './research-infrastructure.service';

import AccountService from '@/account/account.service';

import axios from 'axios';
import { RI_ORGANIZATION_MUST_EXIST_TYPE, INFRASTRUCTURE_IMAGES_LIMIT } from '@/constants';

import UploadFilesService from '@/utility/upload/upload_service';
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { runInNewContext } from 'vm';
import momentPlugin from '@fullcalendar/moment';

import allLocales from '@fullcalendar/core/locales-all';

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons';

//axios.defaults.baseURL = 'http://localhost:8080/'; // for dev only i autocomplete
const validations: any = {
  researchInfrastructure: {
    nameSr: {},
    nameEn: {},
    departmentName: {},
    riLogo: {},
    riWebsite: {},
    descriptionSr: {},
    descriptionEn: {},
    technicalSpecificationSr: {},
    technicalSpecificationEn: {},
    technicalSpecificationLinkSr: {},
    technicalSpecificationLinkEn: {},
    userManualLinkSr: {},
    userManualLinkEn: {},
    keywords: {},
    keywordsEn: {},
    keywordsCyr: {},
    nameSrCyr: {},
    descriptionSrCyr: {},
    technicalSpecificationSrCyr: {},
    technicalSpecificationLinkSrCyr: {},
    userManualLinkSrCyr: {},
    termsOfUseSr: {},
    termsOfUseEn: {},
    termsOfUseSrCyr: {},
    owner: {
      required: requiredIf(function () {
        return this.hasAnyRole(['PA']) || this.hasAnyAuthority('ROLE_ADMIN');
      }),
    },
    projectName: {},
    financeSource: {},
    acquisitionYear: {},
    purchaseValueEuro: {},
    purchaseValueDin: {},
  },
};

library.add(faQuestionCircle);

@Component({
  components: {
    FontAwesomeIcon,
  },
  validations,
})
export default class ResearchInfrastructureUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;

  public researchInfrastructure: IResearchInfrastructure = new ResearchInfrastructure();

  public riCalendar: IRiCalendar = new RiCalendar();

  @Inject('riServiceService') private riServiceService: () => RiServiceService;

  public riServices: IRiService[] = [];

  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;

  public riCalendars: IRiCalendar[] = [];

  @Inject('riStatusService') private riStatusService: () => RiStatusService;

  public riStatuses: IRiStatus[] = [];

  @Inject('riAccessModeService') private riAccessModeService: () => RiAccessModeService;

  public riAccessModes: IRiAccessMode[] = [];

  @Inject('riAccessTypeService') private riAccessTypeService: () => RiAccessTypeService;

  public riAccessTypes: IRiAccessType[] = [];

  @Inject('riCategoryService') private riCategoryService: () => RiCategoryService;

  public riCategories: IRiCategory[] = [];

  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;

  public scientificSubdomains: IScientificSubdomain[] = [];

  @Inject('riSubcategoryService') private riSubcategoryService: () => RiSubcategoryService;

  public riSubcategories: IRiSubcategory[] = [];

  @Inject('riResearchOrganizationService') private riResearchOrganizationService: () => RiResearchOrganizationService;

  public riResearchOrganizations: IRiResearchOrganization[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  @Inject('accountService') private accountService: () => AccountService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;
  private currentName = '';
  private currentDescription = '';
  private currentTechnicalSpecification = '';
  private currentKeywords = '';
  private butt_model = null;
  private search_data = [];
  private search_dataEn = [];
  private query = [];
  private linked0 = null;
  private linked1 = null;
  private linked3 = null;
  private linked4 = null;
  private linked5 = null;
  private linked6 = null;
  private keystrokesNUMSr = 0;
  private keystrokesNUMEn = 0;
  private riportalEntityShortName = 'ri';
  public imageFiles: Blob[] = [];
  public logo: Blob[] = [];
  public placeholdertext = '';
  public placeholdertextLogo = '';
  public browseText = '';
  public fullcalendarapi;
  public calevent = null;
  public calevents = [];
  public customEvents = [];
  private currentTooltip: string | null = null;
  eventGuid = 0;

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
        this.deleteEvent(arg.event);
      });
      // console.log(this.riCalendars);
    },
  };
  currentEvents: any[] = [];
  /*
  addCalendarEvent(arg) {
    let event = arg.event;
    this.riCalendar = new RiCalendar();
    this.riCalendar.calComment = event.title;
    this.riCalendar.startTime = event.start;
    this.riCalendar.endTime = event.end;
    this.riCalendar.researchInfrastructure = this.researchInfrastructure;
    
    this.riCalendarService()
        .create(this.riCalendar)
        .then(param => {
          event.setProp('id', param.id);
          this.calevent = event;
        }).catch(e => {alert('Problem saving event. Please try later.'+ e)});
    // console.log(this.calevent);
     this.fullcalendarapi.refetchEvents();
  }
  updateCalendarEvent(arg) {
	  console.log('update');
	let event = arg.event;
    this.riCalendar.calComment = event.title;
    this.riCalendar.startTime = event.start;
    this.riCalendar.endTime = event.end;
    this.riCalendar.researchInfrastructure = this.researchInfrastructure;
    this.riCalendar.id = event.id;
    
    this.riCalendarService().update(this.riCalendar).then(param => {event.setProp('id', this.riCalendar.id); this.calevent = event;}).catch(e => {alert('Problem saving event. Please try later.'+ e)});
   // console.log(this.calevent);
    this.fullcalendarapi.refetchEvents();
  }
  removeCalendarEvent(arg) {
	    console.log('delete');
	   let event = arg.event;
	   this.riCalendarService().delete(this.riCalendar.id).catch(e => {alert('Problem deleting event. Please try later.'+ e); this.fullcalendarapi.addEvent(event);});
	   this.calevents.filter((calevent) => calevent.id != event.id);
	   this.fullcalendarapi.refetchEvents();
  }
  */
  handleWeekendsToggle() {
    this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
  }

  handleDateSelect(selectInfo) {
    //  let calendarApi = selectInfo.view.calendar;
    let calendarApi = this.fullcalendarapi;

    this.calevents = calendarApi.getEvents();

    let eventexists = false;

    if (this.calevents != null && this.calevents.length > 0) {
      this.calevents.forEach(event => {
        if (event.startStr <= selectInfo.startStr && event.endStr >= selectInfo.endStr) {
          alert(this.$t('riportalApp.researchInfrastructure.eventAlreadyExistsText', { param: event.title }));
          eventexists = true;
          return;
        }
      });
    }
    if (eventexists) return;

    let title = prompt(JSON.stringify(this.$t('riportalApp.researchInfrastructure.eventPromptText')));

    calendarApi.unselect(); // clear date selection
    let event = null;
    if (title) {
      event = calendarApi.addEvent({
        id: this.eventGuid++,
        title,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        allDay: selectInfo.allDay,
      });

      this.customEvents.push(event);
      this.calevents.push(event);
      this.manipulateEvent(event, 1);
      // this.manipulateCalendarEventsToDatabase(event, true, false);
    }
  }

  handleEventClick(clickInfo) {
    let event = clickInfo.event;

    if (confirm(JSON.stringify(this.$t('riportalApp.researchInfrastructure.eventChangeText', { param: event.title })))) {
      // if (confirm(`Are you sure you want to change the event '${event.title}'`)) {
      this.calevent = event;
      this.openEvent();
    }
    /*
	
	if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
      clickInfo.event.remove();
     
    }
    */
  }

  handleEvents(events) {
    this.currentEvents = events;
    this.fullcalendarapi = (<any>this.$refs.fullCalendar).getApi();
    this.fullcalendarapi.refetchEvents();
    window.dispatchEvent(new Event('resize'));
  }
  public tempevent;
  public deleteEvent(event): void {
    this.tempevent = event;
    this.currentEvents.forEach(curevent => {
      if (curevent.id.toString() == this.tempevent.id.toString()) {
        this.tempevent.setProp('title', curevent.title);
        this.tempevent.setStart(curevent.start);
        this.tempevent.setStart(curevent.end);
      }
    });

    if (confirm(JSON.stringify(this.$t('riportalApp.researchInfrastructure.eventDeleteText', { param: this.tempevent.title })))) {
      event.remove();
      this.manipulateEvent(this.tempevent, 3);
    }
  }

  public manipulateEvent(event, save = 0): void {
    this.riCalendar = new RiCalendar();
    this.riCalendar.calComment = event.title;
    this.riCalendar.startTime = event.start;
    this.riCalendar.endTime = event.end;
    this.riCalendar.researchInfrastructure = this.researchInfrastructure;

    if (save == 1) {
      this.riCalendarService()
        .create(this.riCalendar)
        .then(param => {
          event.setProp('id', param.id);
          this.calevent = event;
        })
        .catch(e => {
          alert(this.$t('riportalApp.researchInfrastructure.eventProblemSave', { param: e }));
        });
      // console.log(this.calevent);
      this.fullcalendarapi.refetchEvents();
    } else if (save == 2) {
      this.riCalendar.id = event.id;
      this.riCalendarService()
        .update(this.riCalendar)
        .then(param => {
          event.setProp('id', this.riCalendar.id);
          this.calevent = event;
        })
        .catch(e => {
          alert(this.$t('riportalApp.researchInfrastructure.eventProblemSave', { param: e }));
        });
      this.calevent = event;
    } else {
      this.riCalendar.id = event.id;
      this.riCalendarService()
        .delete(this.riCalendar.id)
        .catch(e => {
          alert(this.$t('riportalApp.researchInfrastructure.eventProblemDelete', { param: e }));
          this.fullcalendarapi.addEvent(event);
        });
    }
  }

  public closeDialog(): void {
    (<any>this.$refs.calendar).hide();
  }

  public openCalendar(): void {
    if (<any>this.$refs.calendar) {
      (<any>this.$refs.calendar).show();

      this.riCalendarService()
        .retrieve()
        .then(res => {
          this.riCalendars = res.data;
          this.calevents = [];
          let cal = {};
          for (const rical of this.riCalendars) {
            cal = { id: rical.id, title: rical.calComment, start: rical.startTime, end: rical.endTime, allDay: true };
            this.calevents.push(cal);
          }
          this.calendarOptions.events = this.calevents;
        });

      window.dispatchEvent(new Event('resize'));
    }
  }
  public openEvent(): void {
    if (<any>this.$refs.calevent) {
      (<any>this.$refs.calevent).show(0, function () {
        // console.log(this.$refs);
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
  public closeEventDialogOK(): void {
    (<any>this.$refs.calevent).hide();
    let event = this.fullcalendarapi.getEventById(this.calevent.id);
    event.setProp('title', (<any>this.$refs.evtTitle).value);
    event.setStart((<any>this.$refs.evtStartDate).value);
    event.setEnd((<any>this.$refs.evtEndDate).value);
    this.manipulateEvent(event, 0);
  }

  formLink(link): void {
    UploadFilesService.getFile(link);
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.researchInfrastructureId) {
        vm.retrieveResearchInfrastructure(to.params.researchInfrastructureId);
      }
      vm.initRelationships();
    });
  }

  getData(): void {
    if (this.keystrokesNUMSr > 2 || this.researchInfrastructure.nameSr.length > 2) {
      var urlstr = '/api/research-infrastructures-autocomplete/' + this.researchInfrastructure.nameSr;
      axios.get(urlstr).then(response => {
        if (Array.isArray(response.data)) {
          this.search_data = response.data;
        }
      });
    } else {
      this.keystrokesNUMSr++;
    }
  }
  getDataEn(): void {
    if (this.keystrokesNUMEn > 2 || this.researchInfrastructure.nameEn.length > 2) {
      var urlstr = '/api/research-infrastructures-autocomplete/en/' + this.researchInfrastructure.nameEn;
      axios.get(urlstr).then(response => {
        if (Array.isArray(response.data)) {
          this.search_dataEn = response.data;
        }
      });
    } else {
      this.keystrokesNUMEn++;
    }
  }
  getNameSr(data, txt): void {
    this.query = data.nameSr;
    this.researchInfrastructure[txt] = data.nameSr;
    this.researchInfrastructure = data;
    //let route = this.$router.resolve({ path: "/research-infrastructure/"+data.id+"/edit" });
    //window.open(route.href);

    this.search_data = [];
    this.search_dataEn = [];
    this.keystrokesNUMSr = 0;
  }
  getNameEn(data, txt): void {
    this.query = data.nameEn;
    this.researchInfrastructure[txt] = data.nameEn;
    this.researchInfrastructure = data;
    this.search_data = [];
    this.search_dataEn = [];
    this.keystrokesNUMEn = 0;
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.butt_model = this.$refs['form_submit'];
    this.placeholdertext = JSON.stringify(this.$t('riportalApp.researchInfrastructure.chooseUpTo3Files')).replace(/["]/g, '');
    this.placeholdertextLogo = JSON.stringify(this.$t('riportalApp.researchInfrastructure.chooseUpTo1File')).replace(/["]/g, '');
    this.browseText = JSON.stringify(this.$t('riportalApp.researchInfrastructure.browseText')).replace(/["]/g, '');

    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
        this.calendarOptions.locale = this.getCalendarLanguageCode();
        //this.manipulateLatCyr();
        this.placeholdertext = JSON.stringify(this.$t('riportalApp.researchInfrastructure.chooseUpTo3Files')).replace(/["]/g, '');
        this.browseText = JSON.stringify(this.$t('riportalApp.researchInfrastructure.browseText')).replace(/["]/g, '');
      }
    );

    this.$watch(
      () => this.imageFiles,
      () => {
        let resFile: Blob[] = [];
        for (let i = 0; i < this.imageFiles.length; i++) {
          let newFile = this.imageFiles[i];
          if (newFile && !newFile.type.startsWith('image/')) {
            this.$nextTick(() => {
              this.imageFiles = [];
              const por = this.$t('riportalApp.researchInfrastructure.errors.fileTypeNotMatch');
              this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
              return;
            });
          }
        }
      }
    );
    this.researchInfrastructure.subdomains = [];
    this.researchInfrastructure.subcategories = [];
  }
  /**
   * Salje izabrane slike na server
   */
  public saveImages(): void {
    if (this.imageFiles.length == 0) {
      const por = this.$t('riportalApp.researchInfrastructure.errors.fileTypeNotMatch');
      this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      return;
    }
    this.isSaving = true;
    let formData = new FormData();

    for (let i = 0; i < this.imageFiles.length; i++) {
      formData.append('files', this.imageFiles[i]);
    }

    formData.append('riId', '' + this.researchInfrastructure.id);

    this.researchInfrastructureService()
      .uploadImages(formData)
      .then(res => {
        this.isSaving = false;
        this.imageFiles = [];
        this.researchInfrastructure.images = res;
      })
      .catch(error => {
        this.isSaving = false;
        if (error.response.status === 400 && error.response.data.type === INFRASTRUCTURE_IMAGES_LIMIT) {
          const por = this.$t('riportalApp.researchInfrastructure.errors.infrastructureImagesLimit');
          this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        }
      });
  }

  public saveLogo(): void {
    if (!this.logo) {
      const por = this.$t('riportalApp.researchInfrastructure.errors.fileTypeNotMatch');
      this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
      return;
    }
    this.isSaving = true;
    let formData1 = new FormData();
    formData1.append('files', this.logo[0]);
    formData1.append('riId', '' + this.researchInfrastructure.id);
    this.researchInfrastructureService()
      .uploadLogo(formData1)
      .then(res => {
        this.isSaving = false;
        this.researchInfrastructure.riLogo = res;
        this.logo = null;
      })
      .catch(error => {
        this.isSaving = false;
      });
  }

  public deleteImage(id: number) {
    this.researchInfrastructureService()
      .deleteImage(id)
      .then(res => {
        this.isSaving = false;
        this.researchInfrastructure.images = res;
      });
  }

  public loadImage(filename): string {
    return 'api/research-infrastructures/image/' + filename;
  }

  public formatNames(files): string {
    if (files.length === 1) return files[0].name;
    else {
      let out = '' + this.$t('riportalApp.researchInfrastructure.filesSelected', { param: files.length });
      return out;
    }

    //return files.length === 1 ? files[0].name : `${files.length} files selected`;
  }

  public formatNames1(files): string {
    if (files.length === 1) return files[0].name;
    else {
      let out = '' + this.$t('riportalApp.researchInfrastructure.filesSelected', { param: files.length });
      return out;
    }

    //return files.length === 1 ? files[0].name : `${files.length} files selected`;
  }

  public manipulateLatCyr(): void {
    if (this.currentLanguage === 'sr' || this.currentLanguage === 'en') {
      this.currentName = this.researchInfrastructure.nameSr;
      this.currentDescription = this.researchInfrastructure.descriptionSr;
      this.currentTechnicalSpecification = this.researchInfrastructure.technicalSpecificationSr;
      this.currentKeywords = this.researchInfrastructure.keywords;
    } else {
      this.currentName = this.researchInfrastructure.nameSrCyr;
      this.currentDescription = this.researchInfrastructure.descriptionSrCyr;
      this.currentTechnicalSpecification = this.researchInfrastructure.technicalSpecificationSrCyr;
      this.currentKeywords = this.researchInfrastructure.keywordsCyr;
    }
  }

  public hasAnyRole(roles: any): boolean {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public populateCyrOrLatValues(): void {
    if (this.currentLanguage === 'sr' || this.currentLanguage === 'en') {
      this.researchInfrastructure.nameSrCyr = this.researchInfrastructure.nameSr;
      this.researchInfrastructure.descriptionSrCyr = this.researchInfrastructure.descriptionSr;
      this.researchInfrastructure.technicalSpecificationSrCyr = this.researchInfrastructure.technicalSpecificationSr;
      this.researchInfrastructure.keywordsCyr = this.researchInfrastructure.keywords;
    } else {
      this.researchInfrastructure.nameSr = this.researchInfrastructure.nameSrCyr;
      this.researchInfrastructure.descriptionSr = this.researchInfrastructure.descriptionSrCyr;
      this.researchInfrastructure.technicalSpecificationSr = this.researchInfrastructure.technicalSpecificationSrCyr;
      this.researchInfrastructure.keywords = this.researchInfrastructure.keywordsCyr;
    }
  }

  public save(): void {
    this.isSaving = true;
    this.populateCyrOrLatValues();
    if (this.researchInfrastructure.id) {
      // console.log(this.$refs.upload_0_M)
      this.researchInfrastructureService()
        .update(this.researchInfrastructure)
        .then(param => {
          this.isSaving = false;
          this.setUploads();
          this.$notify({
            text: JSON.stringify(this.$t('riportalApp.riService.updated', { param: param.id })).replace(/["]/g, ''),
            type: 'info',
            duration: 5000,
          });
          //const message = this.$t('riportalApp.researchInfrastructure.updated', { param: param.id });
          //this.alertService().showAlert(message, 'info');
        });
    } else {
      this.researchInfrastructureService()
        .create(this.researchInfrastructure)
        .then(param => {
          this.isSaving = false;
          this.researchInfrastructure.id = param.id;
          this.$notify({
            text: JSON.stringify(this.$t('riportalApp.riService.created', { param: param.id })).replace(/["]/g, ''),
            type: 'info',
            duration: 5000,
          });
          //const message = this.$t('riportalApp.researchInfrastructure.created', { param: param.id });
          //this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          if (error.response.status === 400 && error.response.data.type === RI_ORGANIZATION_MUST_EXIST_TYPE) {
            const por = this.$t('riportalApp.researchInfrastructure.errors.riOrganizationMustExist');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
          }
        });
    }
  }

  public retrieveResearchInfrastructure(researchInfrastructureId): void {
    this.researchInfrastructureService()
      .find(researchInfrastructureId)
      .then(res => {
        this.researchInfrastructure = res;
        this.manipulateLatCyr();
        this.setUploads();
        this.setCalendar();
      });
  }
  public setCalendar() {
    this.fullcalendarapi = this.$refs.fullcalendar;
    // console.log(this.$refs);
  }
  public setUploads(): void {
    if (this.researchInfrastructure.technicalSpecificationLinkSr) this.linked0 = 1;
    if (this.researchInfrastructure.technicalSpecificationLinkEn) this.linked1 = 1;
    if (this.researchInfrastructure.userManualLinkEn) this.linked3 = 1;
    if (this.researchInfrastructure.userManualLinkSr) this.linked4 = 1;
    if (this.researchInfrastructure.termsOfUseSr) this.linked5 = 1;
    if (this.researchInfrastructure.termsOfUseEn) this.linked6 = 1;
  }
  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.riServiceService()
      .retrieve()
      .then(res => {
        this.riServices = res.data;
      });
    this.riCalendarService()
      .retrieve()
      .then(res => {
        this.riCalendars = res.data;
        this.calevents = [];
        let cal = {};
        for (const rical of this.riCalendars) {
          cal = { id: rical.id, title: rical.calComment, start: rical.startTime, end: rical.endTime, allDay: true };
          this.calevents.push(cal);
        }
        this.calendarOptions.events = this.calevents;
      });
    this.riStatusService()
      .retrieve()
      .then(res => {
        this.riStatuses = res.data;
      });
    this.riAccessModeService()
      .retrieve()
      .then(res => {
        this.riAccessModes = res.data;
      });
    this.riAccessTypeService()
      .retrieve()
      .then(res => {
        this.riAccessTypes = res.data;
      });
    this.riCategoryService()
      .retrieve()
      .then(res => {
        this.riCategories = res.data;
      });
    this.scientificSubdomainService()
      .retrieve()
      .then(res => {
        this.scientificSubdomains = res.data;
      });
    this.riSubcategoryService()
      .retrieve()
      .then(res => {
        this.riSubcategories = res.data;
      });
    this.riResearchOrganizationService()
      .retrieve()
      .then(res => {
        this.riResearchOrganizations = res.data;
      });
    if (this.hasAnyRole(['RPRIPO'])) {
      this.portalUserService()
        .retrieveForOrganizationOfCurrentUser()
        .then(res => {
          this.portalUsers = res.data;
        });
    }
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
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

  public showTooltip(input: string): void {
    // Set the corresponding currentTooltip property
    this.currentTooltip = input;
  }

  // Method to hide tooltip for a specific input
  public hideTooltip(input: string): void {
    // Clear the currentTooltip property
    this.currentTooltip = null;
  }
}
