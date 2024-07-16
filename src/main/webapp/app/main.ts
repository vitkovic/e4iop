// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';

//Dodao Dragan
//dodao dragan
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/locale/sr';
import 'vue2-datepicker/index.css';
import Notifications from 'vue-notification';

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import { BootstrapVueIcons } from 'bootstrap-vue';

import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';
import NumericDataUtils from '@/shared/data/numeric-data-utils.service';

/* tslint:disable */

import PortalUserService from '@/entities/portal-user/portal-user.service';
import PortalUserRoleService from '@/entities/portal-user-role/portal-user-role.service';
import PortalUserStatusService from '@/entities/portal-user-status/portal-user-status.service';
import PortalUserOrganizationService from '@/entities/portal-user-organization/portal-user-organization.service';
import PuoLegalStatusService from '@/entities/puo-legal-status/puo-legal-status.service';
import RiResearchOrganizationService from '@/entities/ri-research-organization/ri-research-organization.service';
import RipoActivityStatusService from '@/entities/ripo-activity-status/ripo-activity-status.service';
import ScientificDomainService from '@/entities/scientific-domain/scientific-domain.service';
import ScientificSubdomainService from '@/entities/scientific-subdomain/scientific-subdomain.service';
import ResearchInfrastructureService from '@/entities/research-infrastructure/research-infrastructure.service';
import RiStatusService from '@/entities/ri-status/ri-status.service';
import RiCategoryService from '@/entities/ri-category/ri-category.service';
import RiSubcategoryService from '@/entities/ri-subcategory/ri-subcategory.service';
import RiAccessTypeService from '@/entities/ri-access-type/ri-access-type.service';
import RiAccessModeService from '@/entities/ri-access-mode/ri-access-mode.service';
import RiFinanceSourceService from '@/entities/ri-finance-source/ri-finance-source.service';
import RiServiceService from '@/entities/ri-service/ri-service.service';
import ServiceTypeService from '@/entities/service-type/service-type.service';
import ServiceSubtypeService from '@/entities/service-subtype/service-subtype.service';
import ServiceAccreditationService from '@/entities/service-accreditation/service-accreditation.service';
import ServiceAvailabilityService from '@/entities/service-availability/service-availability.service';
import RequestForServiceService from '@/entities/request-for-service/request-for-service.service';
import RequestRespondService from '@/entities/request-respond/request-respond.service';
import RequestAnswerService from '@/entities/request-answer/request-answer.service';
import ServiceOfferRespondService from '@/entities/service-offer-respond/service-offer-respond.service';
import AnswerToOfferService from '@/entities/answer-to-offer/answer-to-offer.service';
import RiServiceStatusService from '@/entities/ri-service-status/ri-service-status.service';
import RiProcurementProposalService from '@/entities/ri-procurement-proposal/ri-procurement-proposal.service';
import ServiceProposalService from '@/entities/service-proposal/service-proposal.service';
import ResearcherService from '@/entities/researcher/researcher.service';
import ResearchInstitutionService from '@/entities/research-institution/research-institution.service';
import RiCalendarService from '@/entities/ri-calendar/ri-calendar.service';
import ReportService from '@/entities/reports/report.service';

import AdvertisementService from '@/entities/advertisement/advertisement.service';
import CompanyService from '@/entities/company/company.service';
import MessageService from '@/entities/message/message.service';
import BadgeService from '@/entities/badge/badge.service';
import AdvertisementSupporterService from '@/entities/advertisement-supporter/advertisement-supporter.service';
import AdvertisementSupporterStatusService from '@/entities/advertisement-supporter-status/advertisement-supporter-status.service';
import AdvertisementStatusService from '@/entities/advertisement-status/advertisement-status.service';
import AdvertisementDurationService from '@/entities/advertisement-duration/advertisement-duration.service';
import AdvertisementTypeService from '@/entities/advertisement-type/advertisement-type.service';
import AdvertisementKindService from '@/entities/advertisement-kind/advertisement-kind.service';
import AdvertisementCategoryService from '@/entities/advertisement-category/advertisement-category.service';
import AdvertisementSubcategoryService from '@/entities/advertisement-subcategory/advertisement-subcategory.service';
import AdvertisementSubsubcategoryService from '@/entities/advertisement-subsubcategory/advertisement-subsubcategory.service';
import CollaborationService from '@/entities/collaboration/collaboration.service';
import CollaborationStatusService from '@/entities/collaboration-status/collaboration-status.service';
import CollaborationRatingService from '@/entities/collaboration-rating/collaboration-rating.service';
import ThreadService from '@/entities/thread/thread.service';
import MeetingService from '@/entities/meeting/meeting.service';
import MeetingParticipantService from '@/entities/meeting-participant/meeting-participant.service';
import MeetingParticipantNonB2BService from '@/entities/meeting-participant-non-b2b/meeting-participant-non-b2b.service';
import MeetingParticipantStatusService from '@/entities/meeting-participant-status/meeting-participant-status.service';
import MeetingTypeService from '@/entities/meeting-type/meeting-type.service';
import CmsNewsService from '@/entities/cms-news/cms-news.service';
import CmsPageService from '@/entities/cms-page/cms-page.service';
import CmsPageTypeService from '@/entities/cms-page-type/cms-page-type.service';
import CmsTestimonialsService from '@/entities/cms-testimonials/cms-testimonials.service';
import CmsQuestionService from '@/entities/cms-question/cms-question.service';
import CmsSliderService from '@/entities/cms-slider/cms-slider.service';
import CmsCarouselService from '@/entities/cms-carousel/cms-carousel.service';
import CmsAdvertisementCategoryService from '@/entities/cms-advertisement-category/cms-advertisement-category.service';
import DocumentService from '@/entities/document/document.service';
import DocumentTypeService from '@/entities/document-type/document-type.service';
import DocumentKindService from '@/entities/document-kind/document-kind.service';
import InquiryService from '@/entities/inquiry/inquiry.service';
import ScrollToTop from '@/shared/components/scroll-to-top/ScrollToTop.vue';

import 'vue-multiselect/dist/vue-multiselect.min.css';
import SearchPageService from './core/b2b-jhi-navbar/searchpage.service';
// import vSelect from 'vue-select'
// Vue.component('v-select', vSelect)

import VueMultiselect from 'vue-multiselect';
import Multiselect from 'vue-multiselect';
Vue.component('VueMultiselect', VueMultiselect);
Vue.component('Multiselect', Multiselect);
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

// Vitko
import UploadFiles from '@/utility/upload/uploadfiles.vue';

import { BImg } from 'bootstrap-vue';
//import AutoComp from '@/utility/autocomplete/autocomponent.vue';
import { ListGroupPlugin } from 'bootstrap-vue';
Vue.use(ListGroupPlugin);

import { BootstrapVue } from 'bootstrap-vue';

Vue.use(BootstrapVue);

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.use(Notifications);
Vue.component('DatePicker', DatePicker);
Vue.use(DatePicker);
Vue.use(BootstrapVueIcons);
Vue.component('b-img', BImg);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);
Vue.component('ScrollToTop', ScrollToTop);

// Upload i Autocomplete
Vue.component('UploadFiles', UploadFiles);
//Vue.component('AutoComp', AutoComp);

import { AgGridVue } from '@ag-grid-community/vue';

import { ModuleRegistry } from '@ag-grid-community/core';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';

ModuleRegistry.registerModules([ClientSideRowModelModule]);

Vue.component('AgGridVue', AgGridVue);

import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-alpine.css';

//Dodao Dragan
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
Vue.component('FullCalendar', FullCalendar);

//Vue.use(FullCalendar);

import Vue2Editor from "vue2-editor";

Vue.use(Vue2Editor);

import VueCaptchaCode from '@johnnyguan/vue-captcha-code'
Vue.use(VueCaptchaCode)


const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, (<any>Vue).cookie, translationService, router);
const portalUserService = new PortalUserService(store);








Vue.prototype.$testGlobal = 'test';

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(value => {
      if (!value) {
        sessionStorage.setItem('requested-url', to.fullPath);
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});

//import Vue from 'vue'

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),

    auditsService: () => new AuditsService(),

    healthService: () => new HealthService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    alertService: () => alertService,
    translationService: () => translationService,
    //portalUserService: () => new PortalUserService(),
    portalUserRoleService: () => new PortalUserRoleService(),
    portalUserStatusService: () => new PortalUserStatusService(),
    portalUserOrganizationService: () => new PortalUserOrganizationService(),
    puoLegalStatusService: () => new PuoLegalStatusService(),
    riResearchOrganizationService: () => new RiResearchOrganizationService(),
    ripoActivityStatusService: () => new RipoActivityStatusService(),
    scientificDomainService: () => new ScientificDomainService(),
    scientificSubdomainService: () => new ScientificSubdomainService(),
    researchInfrastructureService: () => new ResearchInfrastructureService(),
    riStatusService: () => new RiStatusService(),
    riCategoryService: () => new RiCategoryService(),
    riSubcategoryService: () => new RiSubcategoryService(),
    riAccessTypeService: () => new RiAccessTypeService(),
    riAccessModeService: () => new RiAccessModeService(),
    riFinanceSourceService: () => new RiFinanceSourceService(),
    riServiceService: () => new RiServiceService(),
    serviceTypeService: () => new ServiceTypeService(),
    serviceSubtypeService: () => new ServiceSubtypeService(),
    serviceAccreditationService: () => new ServiceAccreditationService(),
    serviceAvailabilityService: () => new ServiceAvailabilityService(),
    requestForServiceService: () => new RequestForServiceService(),
    requestRespondService: () => new RequestRespondService(),
    requestAnswerService: () => new RequestAnswerService(),
    serviceOfferRespondService: () => new ServiceOfferRespondService(),
    answerToOfferService: () => new AnswerToOfferService(),
    riServiceStatusService: () => new RiServiceStatusService(),
    riProcurementProposalService: () => new RiProcurementProposalService(),
    serviceProposalService: () => new ServiceProposalService(),
    researcherService: () => new ResearcherService(),
    researchInstitutionService: () => new ResearchInstitutionService(),
    riCalendarService: () => new RiCalendarService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
    portalUserService: () => portalUserService,
    reportService: () => new ReportService(),

    advertisementService: () => new AdvertisementService(),
    companyService: () => new CompanyService(),
    messageService: () => new MessageService(),
    badgeService: () => new BadgeService(),
    advertisementSupporterService: () => new AdvertisementSupporterService(),
    advertisementSupporterStatusService: () => new AdvertisementSupporterStatusService(),
    advertisementStatusService: () => new AdvertisementStatusService(),
    advertisementDurationService: () => new AdvertisementDurationService(),
    advertisementTypeService: () => new AdvertisementTypeService(),
    advertisementKindService: () => new AdvertisementKindService(),
    advertisementCategoryService: () => new AdvertisementCategoryService(),
    advertisementSubcategoryService: () => new AdvertisementSubcategoryService(),
    advertisementSubsubcategoryService: () => new AdvertisementSubsubcategoryService(),
    collaborationService: () => new CollaborationService(),
    collaborationStatusService: () => new CollaborationStatusService(),
    collaborationRatingService: () => new CollaborationRatingService(),
    threadService: () => new ThreadService(),
    meetingService: () => new MeetingService(),
    meetingParticipantService: () => new MeetingParticipantService(),
    meetingParticipantNonB2BService: () => new MeetingParticipantNonB2BService(),
    meetingParticipantStatusService: () => new MeetingParticipantStatusService(),
    meetingTypeService: () => new MeetingTypeService(),
    cmsNewsService: () => new CmsNewsService(),
    cmsPageService: () => new CmsPageService(),
    cmsPageTypeService: () => new CmsPageTypeService(),
    cmsTestimonialsService: () => new CmsTestimonialsService(),
    cmsQuestionService: () => new CmsQuestionService(),
    cmsSliderService: () => new CmsSliderService(),
    cmsCarouselService: () => new CmsCarouselService(),
    cmsAdvertisementCategoryService: () => new CmsAdvertisementCategoryService(),
    documentService: () => new DocumentService(),
    documentTypeService: () => new DocumentTypeService(),
    documentKindService: () => new DocumentKindService(),
    inquiryService: () => new InquiryService(),
    searchPageService: () => new SearchPageService(),
    numericDataUtils: () => new NumericDataUtils(),
  },
  i18n,
  store,
});
