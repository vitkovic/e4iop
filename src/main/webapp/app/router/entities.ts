import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const PortalUser = () => import('@/entities/portal-user/portal-user.vue');
// prettier-ignore
const PortalUserUpdate = () => import('@/entities/portal-user/portal-user-update.vue');
// prettier-ignore
const PortalUserDetails = () => import('@/entities/portal-user/portal-user-details.vue');
const ActivateUserRoles = () => import('@/entities/portal-user/activate-user-roles.vue');
// prettier-ignore
const PortalUserRole = () => import('@/entities/portal-user-role/portal-user-role.vue');
// prettier-ignore
const PortalUserRoleUpdate = () => import('@/entities/portal-user-role/portal-user-role-update.vue');
// prettier-ignore
const PortalUserRoleDetails = () => import('@/entities/portal-user-role/portal-user-role-details.vue');

const PortalUserStatus = () => import('@/entities/portal-user-status/portal-user-status.vue');
// prettier-ignore
const PortalUserStatusUpdate = () => import('@/entities/portal-user-status/portal-user-status-update.vue');
// prettier-ignore
const PortalUserStatusDetails = () => import('@/entities/portal-user-status/portal-user-status-details.vue');

// prettier-ignore
const PortalUserOrganization = () => import('@/entities/portal-user-organization/portal-user-organization.vue');
const PortalUserOrganizationPregled = () => import('@/entities/portal-user-organization/portal-user-organization-pregled.vue');
const PortalUserOrganizationPregledDetails = () =>
  import('@/entities/portal-user-organization/portal-user-organization-pregled-details.vue');
// prettier-ignore
const PortalUserOrganizationUpdate = () => import('@/entities/portal-user-organization/portal-user-organization-update.vue');
// prettier-ignore
const PortalUserOrganizationDetails = () => import('@/entities/portal-user-organization/portal-user-organization-details.vue');
// prettier-ignore
const CompanyPortalUsers = () => import('@/entities/portal-user/company-portal-users.vue');
// prettier-ignore
const PuoLegalStatus = () => import('@/entities/puo-legal-status/puo-legal-status.vue');
// prettier-ignore
const PuoLegalStatusUpdate = () => import('@/entities/puo-legal-status/puo-legal-status-update.vue');
// prettier-ignore
const PuoLegalStatusDetails = () => import('@/entities/puo-legal-status/puo-legal-status-details.vue');
// prettier-ignore
const RiResearchOrganization = () => import('@/entities/ri-research-organization/ri-research-organization.vue');
// prettier-ignore
const RiResearchOrganizationUpdate = () => import('@/entities/ri-research-organization/ri-research-organization-update.vue');
// prettier-ignore
const RiResearchOrganizationDetails = () => import('@/entities/ri-research-organization/ri-research-organization-details.vue');
// prettier-ignore
const RipoActivityStatus = () => import('@/entities/ripo-activity-status/ripo-activity-status.vue');
// prettier-ignore
const RipoActivityStatusUpdate = () => import('@/entities/ripo-activity-status/ripo-activity-status-update.vue');
// prettier-ignore
const RipoActivityStatusDetails = () => import('@/entities/ripo-activity-status/ripo-activity-status-details.vue');
// prettier-ignore
const ScientificDomain = () => import('@/entities/scientific-domain/scientific-domain.vue');
// prettier-ignore
const ScientificDomainUpdate = () => import('@/entities/scientific-domain/scientific-domain-update.vue');
// prettier-ignore
const ScientificDomainDetails = () => import('@/entities/scientific-domain/scientific-domain-details.vue');
// prettier-ignore
const ScientificSubdomain = () => import('@/entities/scientific-subdomain/scientific-subdomain.vue');
// prettier-ignore
const ScientificSubdomainUpdate = () => import('@/entities/scientific-subdomain/scientific-subdomain-update.vue');
// prettier-ignore
const ScientificSubdomainDetails = () => import('@/entities/scientific-subdomain/scientific-subdomain-details.vue');
// prettier-ignore
const ResearchInfrastructurePregled = () => import('@/entities/research-infrastructure/research-infrastructure-pregled.vue');
const ResearchInfrastructurePregledDetails = () => import('@/entities/research-infrastructure/research-infrastructure-pregled-details.vue');
const ResearchInfrastructure = () => import('@/entities/research-infrastructure/research-infrastructure.vue');
// prettier-ignore
const ResearchInfrastructureUpdate = () => import('@/entities/research-infrastructure/research-infrastructure-update.vue');
// prettier-ignore
const ResearchInfrastructureDetails = () => import('@/entities/research-infrastructure/research-infrastructure-details.vue');
// prettier-ignore
const RiStatus = () => import('@/entities/ri-status/ri-status.vue');
// prettier-ignore
const RiStatusUpdate = () => import('@/entities/ri-status/ri-status-update.vue');
// prettier-ignore
const RiStatusDetails = () => import('@/entities/ri-status/ri-status-details.vue');
// prettier-ignore
const RiCategory = () => import('@/entities/ri-category/ri-category.vue');
// prettier-ignore
const RiCategoryUpdate = () => import('@/entities/ri-category/ri-category-update.vue');
// prettier-ignore
const RiCategoryDetails = () => import('@/entities/ri-category/ri-category-details.vue');
// prettier-ignore
const RiSubcategory = () => import('@/entities/ri-subcategory/ri-subcategory.vue');
// prettier-ignore
const RiSubcategoryUpdate = () => import('@/entities/ri-subcategory/ri-subcategory-update.vue');
// prettier-ignore
const RiSubcategoryDetails = () => import('@/entities/ri-subcategory/ri-subcategory-details.vue');
// prettier-ignore
const RiAccessType = () => import('@/entities/ri-access-type/ri-access-type.vue');
// prettier-ignore
const RiAccessTypeUpdate = () => import('@/entities/ri-access-type/ri-access-type-update.vue');
// prettier-ignore
const RiAccessTypeDetails = () => import('@/entities/ri-access-type/ri-access-type-details.vue');
// prettier-ignore
const RiAccessMode = () => import('@/entities/ri-access-mode/ri-access-mode.vue');
// prettier-ignore
const RiAccessModeUpdate = () => import('@/entities/ri-access-mode/ri-access-mode-update.vue');
// prettier-ignore
const RiAccessModeDetails = () => import('@/entities/ri-access-mode/ri-access-mode-details.vue');
// prettier-ignore
const RiFinanceSource = () => import('@/entities/ri-finance-source/ri-finance-source.vue');
// prettier-ignore
const RiFinanceSourceUpdate = () => import('@/entities/ri-finance-source/ri-finance-source-update.vue');
// prettier-ignore
const RiFinanceSourceDetails = () => import('@/entities/ri-finance-source/ri-finance-source-details.vue');
// prettier-ignore
const RiServicePregled = () => import('@/entities/ri-service/ri-service-pregled.vue');
const RiServicePregledDetails = () => import('@/entities/ri-service/ri-service-pregled-details.vue');
const RiService = () => import('@/entities/ri-service/ri-service.vue');
// prettier-ignore
const RiServiceUpdate = () => import('@/entities/ri-service/ri-service-update.vue');
// prettier-ignore
const RiServiceDetails = () => import('@/entities/ri-service/ri-service-details.vue');
// prettier-ignore
const ServiceType = () => import('@/entities/service-type/service-type.vue');
// prettier-ignore
const ServiceTypeUpdate = () => import('@/entities/service-type/service-type-update.vue');
// prettier-ignore
const ServiceTypeDetails = () => import('@/entities/service-type/service-type-details.vue');
// prettier-ignore
const ServiceSubtype = () => import('@/entities/service-subtype/service-subtype.vue');
// prettier-ignore
const ServiceSubtypeUpdate = () => import('@/entities/service-subtype/service-subtype-update.vue');
// prettier-ignore
const ServiceSubtypeDetails = () => import('@/entities/service-subtype/service-subtype-details.vue');
// prettier-ignore
const ServiceAccreditation = () => import('@/entities/service-accreditation/service-accreditation.vue');
// prettier-ignore
const ServiceAccreditationUpdate = () => import('@/entities/service-accreditation/service-accreditation-update.vue');
// prettier-ignore
const ServiceAccreditationDetails = () => import('@/entities/service-accreditation/service-accreditation-details.vue');
// prettier-ignore
const ServiceAvailability = () => import('@/entities/service-availability/service-availability.vue');
// prettier-ignore
const ServiceAvailabilityUpdate = () => import('@/entities/service-availability/service-availability-update.vue');
// prettier-ignore
const ServiceAvailabilityDetails = () => import('@/entities/service-availability/service-availability-details.vue');
// prettier-ignore
const RequestForService = () => import('@/entities/request-for-service/request-for-service.vue');
const RequestRating = () => import('@/entities/request-for-service/request-rating.vue');
// prettier-ignore
const RequestForServiceUpdate = () => import('@/entities/request-for-service/request-for-service-update.vue');
// prettier-ignore
const RequestForServiceDetails = () => import('@/entities/request-for-service/request-for-service-details.vue');
const RequestForServiceZbirno = () => import('@/entities/request-for-service/request-for-service-zbirno.vue');
// prettier-ignore
const RequestRespond = () => import('@/entities/request-respond/request-respond.vue');
// prettier-ignore
const RequestRespondUpdate = () => import('@/entities/request-respond/request-respond-update.vue');
// prettier-ignore
const RequestRespondDetails = () => import('@/entities/request-respond/request-respond-details.vue');
// prettier-ignore
const RequestAnswer = () => import('@/entities/request-answer/request-answer.vue');
// prettier-ignore
const RequestAnswerUpdate = () => import('@/entities/request-answer/request-answer-update.vue');
// prettier-ignore
const RequestAnswerDetails = () => import('@/entities/request-answer/request-answer-details.vue');
// prettier-ignore
const ServiceOfferRespond = () => import('@/entities/service-offer-respond/service-offer-respond.vue');
// prettier-ignore
const ServiceOfferRespondUpdate = () => import('@/entities/service-offer-respond/service-offer-respond-update.vue');
// prettier-ignore
const ServiceOfferRespondDetails = () => import('@/entities/service-offer-respond/service-offer-respond-details.vue');
// prettier-ignore
const AnswerToOffer = () => import('@/entities/answer-to-offer/answer-to-offer.vue');
// prettier-ignore
const AnswerToOfferUpdate = () => import('@/entities/answer-to-offer/answer-to-offer-update.vue');
// prettier-ignore
const AnswerToOfferDetails = () => import('@/entities/answer-to-offer/answer-to-offer-details.vue');
// prettier-ignore
const RiServiceStatus = () => import('@/entities/ri-service-status/ri-service-status.vue');
// prettier-ignore
const RiServiceStatusUpdate = () => import('@/entities/ri-service-status/ri-service-status-update.vue');
// prettier-ignore
const RiServiceStatusDetails = () => import('@/entities/ri-service-status/ri-service-status-details.vue');
// prettier-ignore
const RiProcurementProposal = () => import('@/entities/ri-procurement-proposal/ri-procurement-proposal.vue');
// prettier-ignore
const RiProcurementProposalUpdate = () => import('@/entities/ri-procurement-proposal/ri-procurement-proposal-update.vue');
// prettier-ignore
const RiProcurementProposalDetails = () => import('@/entities/ri-procurement-proposal/ri-procurement-proposal-details.vue');
// prettier-ignore
const ServiceProposal = () => import('@/entities/service-proposal/service-proposal.vue');
// prettier-ignore
const ServiceProposalUpdate = () => import('@/entities/service-proposal/service-proposal-update.vue');
// prettier-ignore
const ServiceProposalDetails = () => import('@/entities/service-proposal/service-proposal-details.vue');
// prettier-ignore
const Researcher = () => import('@/entities/researcher/researcher.vue');
// prettier-ignore
const ResearcherUpdate = () => import('@/entities/researcher/researcher-update.vue');
// prettier-ignore
const ResearcherDetails = () => import('@/entities/researcher/researcher-details.vue');
// prettier-ignore
const ResearchInstitution = () => import('@/entities/research-institution/research-institution.vue');
// prettier-ignore
const ResearchInstitutionUpdate = () => import('@/entities/research-institution/research-institution-update.vue');
// prettier-ignore
const ResearchInstitutionDetails = () => import('@/entities/research-institution/research-institution-details.vue');
// prettier-ignore
const RiCalendar = () => import('@/entities/ri-calendar/ri-calendar.vue');
// prettier-ignore
const RiCalendarUpdate = () => import('@/entities/ri-calendar/ri-calendar-update.vue');
// prettier-ignore
const RiCalendarDetails = () => import('@/entities/ri-calendar/ri-calendar-details.vue');

const ReportForOrganizations = () => import('@/entities/reports/for-organizations.vue');
const InfrastructureStatistic = () => import('@/entities/reports/infrastructure-statistic.vue');
const ServiceStatistic = () => import('@/entities/reports/service-statistic.vue');
const ServiceStatisticNio = () => import('@/entities/reports/service-statistic-nio.vue');

/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Advertisement = () => import('@/entities/advertisement/advertisement.vue');
// prettier-ignore
const AdvertisementUpdate = () => import('@/entities/advertisement/advertisement-update.vue');
// prettier-ignore
const AdvertisementDetails = () => import('@/entities/advertisement/advertisement-details.vue');
// prettier-ignore
const AdvertisementBrowse = () => import('@/entities/advertisement/advertisement-browse.vue');
// prettier-ignore
const AdvertisementSearch = () => import('@/entities/advertisement/advertisement-search.vue');
// prettier-ignore
const AdvertisementGet = () => import('@/entities/advertisement/advertisement-get.vue');

const AdvertisementViewan = () => import('@/entities/advertisement/advertisement-viewan.vue');

// prettier-ignore
const CompanyAdvertisementsView = () => import('@/entities/advertisement/company-advertisements-view.vue');
// prettier-ignore
const CompanyAdvertisementsManage = () => import('@/entities/advertisement/company-advertisements-manage.vue');
// prettier-ignore
const Company = () => import('@/entities/company/company.vue');
// prettier-ignore
const CompanyUpdate = () => import('@/entities/company/company-update.vue');
// prettier-ignore
const CompanyDetails = () => import('@/entities/company/company-details.vue');
// prettier-ignore
const CompanyCalendar = () => import('@/entities/company/company-calendar.vue');
//
const CompanySearch = () => import('@/entities/company/company-search.vue');

const CmsNewsSearch = () => import('@/entities/cms-news/cms-news-search.vue');

// prettier-ignore
const Message = () => import('@/entities/message/message.vue');
// prettier-ignore
const MessageUpdate = () => import('@/entities/message/message-update.vue');
// prettier-ignore
const MessageDetails = () => import('@/entities/message/message-details.vue');
const Badge = () => import('@/entities/badge/badge.vue');
// prettier-ignore
const BadgeUpdate = () => import('@/entities/badge/badge-update.vue');
// prettier-ignore
const BadgeDetails = () => import('@/entities/badge/badge-details.vue');
// prettier-ignore
const AdvertisementSupporter = () => import('@/entities/advertisement-supporter/advertisement-supporter.vue');
// prettier-ignore
const AdvertisementSupporterUpdate = () => import('@/entities/advertisement-supporter/advertisement-supporter-update.vue');
// prettier-ignore
const AdvertisementSupporterDetails = () => import('@/entities/advertisement-supporter/advertisement-supporter-details.vue');
// prettier-ignore
const AdvertisementSupporterStatus = () => import('@/entities/advertisement-supporter-status/advertisement-supporter-status.vue');
// prettier-ignore
const AdvertisementSupporterStatusDetails = () => import('@/entities/advertisement-supporter-status/advertisement-supporter-status-details.vue');
// prettier-ignore
const AdvertisementSupporterStatusUpdate = () => import('@/entities/advertisement-supporter-status/advertisement-supporter-status-update.vue');
// prettier-ignore
const AdvertisementStatus = () => import('@/entities/advertisement-status/advertisement-status.vue');
// prettier-ignore
const AdvertisementStatusUpdate = () => import('@/entities/advertisement-status/advertisement-status-update.vue');
// prettier-ignore
const AdvertisementStatusDetails = () => import('@/entities/advertisement-status/advertisement-status-details.vue');
// prettier-ignore
const AdvertisementDuration = () => import('@/entities/advertisement-duration/advertisement-duration.vue');
// prettier-ignore
const AdvertisementDurationUpdate = () => import('@/entities/advertisement-duration/advertisement-duration-update.vue');
// prettier-ignore
const AdvertisementDurationDetails = () => import('@/entities/advertisement-duration/advertisement-duration-details.vue');
// prettier-ignore
const AdvertisementType = () => import('@/entities/advertisement-type/advertisement-type.vue');
// prettier-ignore
const AdvertisementTypeUpdate = () => import('@/entities/advertisement-type/advertisement-type-update.vue');
// prettier-ignore
const AdvertisementTypeDetails = () => import('@/entities/advertisement-type/advertisement-type-details.vue');
// prettier-ignore
const AdvertisementKind = () => import('@/entities/advertisement-kind/advertisement-kind.vue');
// prettier-ignore
const AdvertisementKindUpdate = () => import('@/entities/advertisement-kind/advertisement-kind-update.vue');
// prettier-ignore
const AdvertisementKindDetails = () => import('@/entities/advertisement-kind/advertisement-kind-details.vue');
// prettier-ignore
const AdvertisementCategory = () => import('@/entities/advertisement-category/advertisement-category.vue');
// prettier-ignore
const AdvertisementCategoryUpdate = () => import('@/entities/advertisement-category/advertisement-category-update.vue');
// prettier-ignore
const AdvertisementCategoryDetails = () => import('@/entities/advertisement-category/advertisement-category-details.vue');
// prettier-ignore
const AdvertisementSubcategory = () => import('@/entities/advertisement-subcategory/advertisement-subcategory.vue');
// prettier-ignore
const AdvertisementSubcategoryUpdate = () => import('@/entities/advertisement-subcategory/advertisement-subcategory-update.vue');
// prettier-ignore
const AdvertisementSubcategoryDetails = () => import('@/entities/advertisement-subcategory/advertisement-subcategory-details.vue');
// prettier-ignore
const AdvertisementSubsubcategory = () => import('@/entities/advertisement-subsubcategory/advertisement-subsubcategory.vue');
// prettier-ignore
const AdvertisementSubsubcategoryUpdate = () => import('@/entities/advertisement-subsubcategory/advertisement-subsubcategory-update.vue');
// prettier-ignore
const AdvertisementSubsubcategoryDetails = () => import('@/entities/advertisement-subsubcategory/advertisement-subsubcategory-details.vue');
// prettier-ignore
const Collaboration = () => import('@/entities/collaboration/collaboration.vue');
// prettier-ignore
const CollaborationUpdate = () => import('@/entities/collaboration/collaboration-update.vue');
// prettier-ignore
const CollaborationDetails = () => import('@/entities/collaboration/collaboration-details.vue');
// prettier-ignore
const CompanyCollaborations = () => import('@/entities/collaboration/company-collaborations.vue');
// prettier-ignore
const CollaborationStatus = () => import('@/entities/collaboration-status/collaboration-status.vue');
// prettier-ignore
const CollaborationStatusUpdate = () => import('@/entities/collaboration-status/collaboration-status-update.vue');
// prettier-ignore
const CollaborationStatusDetails = () => import('@/entities/collaboration-status/collaboration-status-details.vue');
// prettier-ignore
const CollaborationRating = () => import('@/entities/collaboration-rating/collaboration-rating.vue');
// prettier-ignore
const CollaborationRatingUpdate = () => import('@/entities/collaboration-rating/collaboration-rating-update.vue');
// prettier-ignore
const CollaborationRatingDetails = () => import('@/entities/collaboration-rating/collaboration-rating-details.vue');
// prettier-ignore
const Thread = () => import('@/entities/thread/thread.vue');
// prettier-ignore
const ThreadUpdate = () => import('@/entities/thread/thread-update.vue');
// prettier-ignore
const ThreadDetails = () => import('@/entities/thread/thread-details.vue');
// prettier-ignore
const CompanyThreads = () => import('@/entities/thread/company-threads.vue');
// prettier-ignore
const Meeting = () => import('@/entities/meeting/meeting.vue');
// prettier-ignore
const MeetingUpdate = () => import('@/entities/meeting/meeting-update.vue');
// prettier-ignore
const MeetingDetails = () => import('@/entities/meeting/meeting-details.vue');
// prettier-ignore
const MeetingParticipant = () => import('@/entities/meeting-participant/meeting-participant.vue');
// prettier-ignore
const MeetingParticipantUpdate = () => import('@/entities/meeting-participant/meeting-participant-update.vue');
// prettier-ignore
const MeetingParticipantDetails = () => import('@/entities/meeting-participant/meeting-participant-details.vue');
// prettier-ignore
const MeetingParticipantStatus  = () => import('@/entities/meeting-participant-status/meeting-participant-status.vue');
// prettier-ignore
const MeetingParticipantStatusDetails  = () => import('@/entities/meeting-participant-status/meeting-participant-status-details.vue');
// prettier-ignore
const MeetingParticipantStatusUpdate  = () => import('@/entities/meeting-participant-status/meeting-participant-status-update.vue');
// prettier-ignore
const MeetingType = () => import('@/entities/meeting-type/meeting-type.vue');
// prettier-ignore
const MeetingTypeUpdate = () => import('@/entities/meeting-type/meeting-type-update.vue');
// prettier-ignore
const MeetingTypeDetails = () => import('@/entities/meeting-type/meeting-type-details.vue');
// prettier-ignore
const CmsPage = () => import('@/entities/cms-page/cms-page.vue');
// prettier-ignore
const CmsPageUpdate = () => import('@/entities/cms-page/cms-page-update.vue');
// prettier-ignore
const CmsPageDetails = () => import('@/entities/cms-page/cms-page-details.vue');
// prettier-ignore
const CmsPageType = () => import('@/entities/cms-page-type/cms-page-type.vue');
// prettier-ignore
const CmsPageTypeUpdate = () => import('@/entities/cms-page-type/cms-page-type-update.vue');
// prettier-ignore
const CmsPageTypeDetails = () => import('@/entities/cms-page-type/cms-page-type-details.vue');
// prettier-ignore
const CmsTestimonials = () => import('@/entities/cms-testimonials/cms-testimonials.vue');
// prettier-ignore
const CmsTestimonialsUpdate = () => import('@/entities/cms-testimonials/cms-testimonials-update.vue');
// prettier-ignore
const CmsTestimonialsDetails = () => import('@/entities/cms-testimonials/cms-testimonials-details.vue');
// prettier-ignore
const CmsQuestion = () => import('@/entities/cms-question/cms-question.vue');
// prettier-ignore
const CmsQuestionUpdate = () => import('@/entities/cms-question/cms-question-update.vue');
// prettier-ignore
const CmsQuestionDetails = () => import('@/entities/cms-question/cms-question-details.vue');

const CmsQuestionSearch = () => import('@/entities/cms-question/cms-question-search.vue');

// prettier-ignore
const CmsSlider = () => import('@/entities/cms-slider/cms-slider.vue');
// prettier-ignore
const CmsSliderUpdate = () => import('@/entities/cms-slider/cms-slider-update.vue');
// prettier-ignore
const CmsSliderDetails = () => import('@/entities/cms-slider/cms-slider-details.vue');
// prettier-ignore
const CmsCarousel = () => import('@/entities/cms-carousel/cms-carousel.vue');
// prettier-ignore
const CmsCarouselUpdate = () => import('@/entities/cms-carousel/cms-carousel-update.vue');
// prettier-ignore
const CmsCarouselDetails = () => import('@/entities/cms-carousel/cms-carousel-details.vue');
// prettier-ignore
const CmsAdvertisementCategory = () => import('@/entities/cms-advertisement-category/cms-advertisement-category.vue');
// prettier-ignore
const CmsAdvertisementCategoryUpdate = () => import('@/entities/cms-advertisement-category/cms-advertisement-category-update.vue');
// prettier-ignore
const CmsAdvertisementCategoryDetails = () => import('@/entities/cms-advertisement-category/cms-advertisement-category-details.vue');
// prettier-ignore
const Document = () => import('@/entities/document/document.vue');
// prettier-ignore
const DocumentUpdate = () => import('@/entities/document/document-update.vue');
// prettier-ignore
const DocumentDetails = () => import('@/entities/document/document-details.vue');
// prettier-ignore
const DocumentType = () => import('@/entities/document-type/document-type.vue');
// prettier-ignore
const DocumentTypeUpdate = () => import('@/entities/document-type/document-type-update.vue');
// prettier-ignore
const DocumentTypeDetails = () => import('@/entities/document-type/document-type-details.vue');
// prettier-ignore
const DocumentKind = () => import('@/entities/document-kind/document-kind.vue');
// prettier-ignore
const DocumentKindUpdate = () => import('@/entities/document-kind/document-kind-update.vue');
// prettier-ignore
const DocumentKindDetails = () => import('@/entities/document-kind/document-kind-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/portal-user',
    name: 'PortalUser',
    component: PortalUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user/new',
    name: 'PortalUserCreate',
    component: PortalUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user/:portalUserId/edit',
    name: 'PortalUserEdit',
    component: PortalUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user/:portalUserId/view',
    name: 'PortalUserView',
    component: PortalUserDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/activate-user-roles/:portalUserId',
    name: 'ActivateUserRoles',
    component: ActivateUserRoles,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-role',
    name: 'PortalUserRole',
    component: PortalUserRole,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-role/new',
    name: 'PortalUserRoleCreate',
    component: PortalUserRoleUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-role/:portalUserRoleId/edit',
    name: 'PortalUserRoleEdit',
    component: PortalUserRoleUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-role/:portalUserRoleId/view',
    name: 'PortalUserRoleView',
    component: PortalUserRoleDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/portal-user-status',
    name: 'PortalUserStatus',
    component: PortalUserStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-status/new',
    name: 'PortalUserStatusCreate',
    component: PortalUserStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-status/:portalUserStatusId/edit',
    name: 'PortalUserStatusEdit',
    component: PortalUserStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-role/:portalUserStatusId/view',
    name: 'PortalUserStatusView',
    component: PortalUserStatusDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/portal-user-organization-pregled',
    name: 'PortalUserOrganizationPregled',
    component: PortalUserOrganizationPregled,
  },
  {
    path: '/portal-user-organization-pregled-details/:portalUserOrganizationId',
    name: 'PortalUserOrganizationPregledDetails',
    component: PortalUserOrganizationPregledDetails,
  },
  {
    path: '/portal-user-organization',
    name: 'PortalUserOrganization',
    component: PortalUserOrganization,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-organization/new',
    name: 'PortalUserOrganizationCreate',
    component: PortalUserOrganizationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-organization/:portalUserOrganizationId/edit',
    name: 'PortalUserOrganizationEdit',
    component: PortalUserOrganizationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/portal-user-organization/:portalUserOrganizationId/view',
    name: 'PortalUserOrganizationView',
    component: PortalUserOrganizationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/users',
    name: 'CompanyPortalUsers',
    component: CompanyPortalUsers,
    meta: { authorities: [Authority.COMPANY_ADMIN] },
  },
  {
    path: '/puo-legal-status',
    name: 'PuoLegalStatus',
    component: PuoLegalStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/puo-legal-status/new',
    name: 'PuoLegalStatusCreate',
    component: PuoLegalStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/puo-legal-status/:puoLegalStatusId/edit',
    name: 'PuoLegalStatusEdit',
    component: PuoLegalStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/puo-legal-status/:puoLegalStatusId/view',
    name: 'PuoLegalStatusView',
    component: PuoLegalStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-research-organization',
    name: 'RiResearchOrganization',
    component: RiResearchOrganization,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-research-organization/new',
    name: 'RiResearchOrganizationCreate',
    component: RiResearchOrganizationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-research-organization/:riResearchOrganizationId/edit',
    name: 'RiResearchOrganizationEdit',
    component: RiResearchOrganizationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-research-organization/:riResearchOrganizationId/view',
    name: 'RiResearchOrganizationView',
    component: RiResearchOrganizationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ripo-activity-status',
    name: 'RipoActivityStatus',
    component: RipoActivityStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ripo-activity-status/new',
    name: 'RipoActivityStatusCreate',
    component: RipoActivityStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ripo-activity-status/:ripoActivityStatusId/edit',
    name: 'RipoActivityStatusEdit',
    component: RipoActivityStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ripo-activity-status/:ripoActivityStatusId/view',
    name: 'RipoActivityStatusView',
    component: RipoActivityStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-domain',
    name: 'ScientificDomain',
    component: ScientificDomain,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-domain/new',
    name: 'ScientificDomainCreate',
    component: ScientificDomainUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-domain/:scientificDomainId/edit',
    name: 'ScientificDomainEdit',
    component: ScientificDomainUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-domain/:scientificDomainId/view',
    name: 'ScientificDomainView',
    component: ScientificDomainDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-subdomain',
    name: 'ScientificSubdomain',
    component: ScientificSubdomain,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-subdomain/new',
    name: 'ScientificSubdomainCreate',
    component: ScientificSubdomainUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-subdomain/:scientificSubdomainId/edit',
    name: 'ScientificSubdomainEdit',
    component: ScientificSubdomainUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/scientific-subdomain/:scientificSubdomainId/view',
    name: 'ScientificSubdomainView',
    component: ScientificSubdomainDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-infrastructure-pregled',
    name: 'ResearchInfrastructurePregled',
    component: ResearchInfrastructurePregled,
    //meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-infrastructure-pregled-details/:researchInfrastructureId',
    name: 'ResearchInfrastructurePregledDetails',
    component: ResearchInfrastructurePregledDetails,
    props: { search: {} },
    //meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-infrastructure',
    name: 'ResearchInfrastructure',
    component: ResearchInfrastructure,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-infrastructure/new',
    name: 'ResearchInfrastructureCreate',
    component: ResearchInfrastructureUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-infrastructure/:researchInfrastructureId/edit',
    name: 'ResearchInfrastructureEdit',
    component: ResearchInfrastructureUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-infrastructure/:researchInfrastructureId/view',
    name: 'ResearchInfrastructureView',
    component: ResearchInfrastructureDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-status',
    name: 'RiStatus',
    component: RiStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-status/new',
    name: 'RiStatusCreate',
    component: RiStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-status/:riStatusId/edit',
    name: 'RiStatusEdit',
    component: RiStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-status/:riStatusId/view',
    name: 'RiStatusView',
    component: RiStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-category',
    name: 'RiCategory',
    component: RiCategory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-category/new',
    name: 'RiCategoryCreate',
    component: RiCategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-category/:riCategoryId/edit',
    name: 'RiCategoryEdit',
    component: RiCategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-category/:riCategoryId/view',
    name: 'RiCategoryView',
    component: RiCategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-subcategory',
    name: 'RiSubcategory',
    component: RiSubcategory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-subcategory/new',
    name: 'RiSubcategoryCreate',
    component: RiSubcategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-subcategory/:riSubcategoryId/edit',
    name: 'RiSubcategoryEdit',
    component: RiSubcategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-subcategory/:riSubcategoryId/view',
    name: 'RiSubcategoryView',
    component: RiSubcategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-type',
    name: 'RiAccessType',
    component: RiAccessType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-type/new',
    name: 'RiAccessTypeCreate',
    component: RiAccessTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-type/:riAccessTypeId/edit',
    name: 'RiAccessTypeEdit',
    component: RiAccessTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-type/:riAccessTypeId/view',
    name: 'RiAccessTypeView',
    component: RiAccessTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-mode',
    name: 'RiAccessMode',
    component: RiAccessMode,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-mode/new',
    name: 'RiAccessModeCreate',
    component: RiAccessModeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-mode/:riAccessModeId/edit',
    name: 'RiAccessModeEdit',
    component: RiAccessModeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-access-mode/:riAccessModeId/view',
    name: 'RiAccessModeView',
    component: RiAccessModeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-finance-source',
    name: 'RiFinanceSource',
    component: RiFinanceSource,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-finance-source/new',
    name: 'RiFinanceSourceCreate',
    component: RiFinanceSourceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-finance-source/:riFinanceSourceId/edit',
    name: 'RiFinanceSourceEdit',
    component: RiFinanceSourceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-finance-source/:riFinanceSourceId/view',
    name: 'RiFinanceSourceView',
    component: RiFinanceSourceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service-pregled',
    name: 'RiServicePregled',
    component: RiServicePregled,
  },
  {
    path: '/ri-service-pregled-details/:riServiceId',
    name: 'RiServicePregledDetails',
    component: RiServicePregledDetails,
  },
  {
    path: '/ri-service',
    name: 'RiService',
    component: RiService,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service/new',
    name: 'RiServiceCreate',
    component: RiServiceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service/:riServiceId/edit',
    name: 'RiServiceEdit',
    component: RiServiceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service/:riServiceId/view',
    name: 'RiServiceView',
    component: RiServiceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-type',
    name: 'ServiceType',
    component: ServiceType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-type/new',
    name: 'ServiceTypeCreate',
    component: ServiceTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-type/:serviceTypeId/edit',
    name: 'ServiceTypeEdit',
    component: ServiceTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-type/:serviceTypeId/view',
    name: 'ServiceTypeView',
    component: ServiceTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-subtype',
    name: 'ServiceSubtype',
    component: ServiceSubtype,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-subtype/new',
    name: 'ServiceSubtypeCreate',
    component: ServiceSubtypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-subtype/:serviceSubtypeId/edit',
    name: 'ServiceSubtypeEdit',
    component: ServiceSubtypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-subtype/:serviceSubtypeId/view',
    name: 'ServiceSubtypeView',
    component: ServiceSubtypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-accreditation',
    name: 'ServiceAccreditation',
    component: ServiceAccreditation,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-accreditation/new',
    name: 'ServiceAccreditationCreate',
    component: ServiceAccreditationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-accreditation/:serviceAccreditationId/edit',
    name: 'ServiceAccreditationEdit',
    component: ServiceAccreditationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-accreditation/:serviceAccreditationId/view',
    name: 'ServiceAccreditationView',
    component: ServiceAccreditationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-availability',
    name: 'ServiceAvailability',
    component: ServiceAvailability,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-availability/new',
    name: 'ServiceAvailabilityCreate',
    component: ServiceAvailabilityUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-availability/:serviceAvailabilityId/edit',
    name: 'ServiceAvailabilityEdit',
    component: ServiceAvailabilityUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-availability/:serviceAvailabilityId/view',
    name: 'ServiceAvailabilityView',
    component: ServiceAvailabilityDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-for-service',
    name: 'RequestForService',
    component: RequestForService,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-rating',
    name: 'Requestrating',
    component: RequestRating,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-for-service/new',
    name: 'RequestForServiceCreate',
    component: RequestForServiceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-for-service/:requestForServiceId/edit',
    name: 'RequestForServiceEdit',
    component: RequestForServiceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-for-service/:serviceId/createForService',
    name: 'RequestForServiceCreateForService',
    component: RequestForServiceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-for-service/:requestForServiceId/view',
    name: 'RequestForServiceView',
    component: RequestForServiceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-for-service/:requestForServiceId/zbirno',
    name: 'RequestForServiceZbirno',
    component: RequestForServiceZbirno,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-respond',
    name: 'RequestRespond',
    component: RequestRespond,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-respond/new',
    name: 'RequestRespondCreate',
    component: RequestRespondUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-respond/choice/:requestForServiceId',
    name: 'RequestRespondCreateChoice',
    component: RequestRespondUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-respond/:requestRespondId/edit',
    name: 'RequestRespondEdit',
    component: RequestRespondUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-respond/:requestRespondId/view',
    name: 'RequestRespondView',
    component: RequestRespondDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-respond/:requestForServiceId/viewForService',
    name: 'RequestRespondViewForService',
    component: RequestRespondDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-answer',
    name: 'RequestAnswer',
    component: RequestAnswer,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-answer/new',
    name: 'RequestAnswerCreate',
    component: RequestAnswerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-answer/:requestAnswerId/edit',
    name: 'RequestAnswerEdit',
    component: RequestAnswerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request-answer/:requestAnswerId/view',
    name: 'RequestAnswerView',
    component: RequestAnswerDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-offer-respond',
    name: 'ServiceOfferRespond',
    component: ServiceOfferRespond,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-offer-respond/new',
    name: 'ServiceOfferRespondCreate',
    component: ServiceOfferRespondUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-offer-respond/choice/:requestRespondId',
    name: 'ServiceOfferRespondRespondCreateChoice',
    component: ServiceOfferRespondUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-offer-respond/:serviceOfferRespondId/edit',
    name: 'ServiceOfferRespondEdit',
    component: ServiceOfferRespondUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-offer-respond/:serviceOfferRespondId/view',
    name: 'ServiceOfferRespondView',
    component: ServiceOfferRespondDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/answer-to-offer',
    name: 'AnswerToOffer',
    component: AnswerToOffer,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/answer-to-offer/new',
    name: 'AnswerToOfferCreate',
    component: AnswerToOfferUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/answer-to-offer/:answerToOfferId/edit',
    name: 'AnswerToOfferEdit',
    component: AnswerToOfferUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/answer-to-offer/:answerToOfferId/view',
    name: 'AnswerToOfferView',
    component: AnswerToOfferDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service-status',
    name: 'RiServiceStatus',
    component: RiServiceStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service-status/new',
    name: 'RiServiceStatusCreate',
    component: RiServiceStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service-status/:riServiceStatusId/edit',
    name: 'RiServiceStatusEdit',
    component: RiServiceStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-service-status/:riServiceStatusId/view',
    name: 'RiServiceStatusView',
    component: RiServiceStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-procurement-proposal',
    name: 'RiProcurementProposal',
    component: RiProcurementProposal,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-procurement-proposal/new',
    name: 'RiProcurementProposalCreate',
    component: RiProcurementProposalUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-procurement-proposal/:riProcurementProposalId/edit',
    name: 'RiProcurementProposalEdit',
    component: RiProcurementProposalUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-procurement-proposal/:riProcurementProposalId/view',
    name: 'RiProcurementProposalView',
    component: RiProcurementProposalDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-proposal',
    name: 'ServiceProposal',
    component: ServiceProposal,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-proposal/new',
    name: 'ServiceProposalCreate',
    component: ServiceProposalUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-proposal/:serviceProposalId/edit',
    name: 'ServiceProposalEdit',
    component: ServiceProposalUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/service-proposal/:serviceProposalId/view',
    name: 'ServiceProposalView',
    component: ServiceProposalDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/researcher',
    name: 'Researcher',
    component: Researcher,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/researcher/new',
    name: 'ResearcherCreate',
    component: ResearcherUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/researcher/:researcherId/edit',
    name: 'ResearcherEdit',
    component: ResearcherUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/researcher/:researcherId/view',
    name: 'ResearcherView',
    component: ResearcherDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-institution',
    name: 'ResearchInstitution',
    component: ResearchInstitution,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-institution/new',
    name: 'ResearchInstitutionCreate',
    component: ResearchInstitutionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-institution/:researchInstitutionId/edit',
    name: 'ResearchInstitutionEdit',
    component: ResearchInstitutionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/research-institution/:researchInstitutionId/view',
    name: 'ResearchInstitutionView',
    component: ResearchInstitutionDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-calendar',
    name: 'RiCalendar',
    component: RiCalendar,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-calendar/new',
    name: 'RiCalendarCreate',
    component: RiCalendarUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-calendar/:riCalendarId/edit',
    name: 'RiCalendarEdit',
    component: RiCalendarUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/ri-calendar/:riCalendarId/view',
    name: 'RiCalendarView',
    component: RiCalendarDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reports/organizations',
    name: 'ReportForOrganizations',
    component: ReportForOrganizations,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reports/infrastructure-statistic',
    name: 'InfrastructureStatistic',
    component: InfrastructureStatistic,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reports/service-statistic',
    name: 'ServiceStatistic',
    component: ServiceStatistic,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reports/service-statistic-nio',
    name: 'ServiceStatisticNio',
    component: ServiceStatisticNio,
    meta: { authorities: [Authority.USER] },
  },

  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  {
    path: '/b2b/advertisement',
    name: 'Advertisement',
    component: Advertisement,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement/new',
    name: 'AdvertisementCreate',
    component: AdvertisementUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement/:advertisementId/edit',
    name: 'AdvertisementEdit',
    component: AdvertisementUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement/:advertisementId/view',
    name: 'AdvertisementView',
    component: AdvertisementDetails,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement/viewan/:advertisementId',
    name: 'AdvertisementViewan',
    component: AdvertisementViewan,
  },
  {
    path: '/b2b/advertisement-browse',
    name: 'AdvertisementBrowse',
    component: AdvertisementBrowse,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-search',
    name: 'AdvertisementSearch',
    component: AdvertisementSearch,
  },
  {
    path: '/b2b/advertisement-get',
    name: 'AdvertisementGet',
    component: AdvertisementGet,
  },

  {
    path: '/b2b/company/:companyId/advertisements-view',
    name: 'CompanyAdvertisementsView',
    component: CompanyAdvertisementsView,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/advertisements-manage',
    name: 'CompanyAdvertisementsManage',
    component: CompanyAdvertisementsManage,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company',
    name: 'Company',
    component: Company,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company-search',
    name: 'CompanySearch',
    component: CompanySearch,
  },
  {
    path: '/b2b/company/new',
    name: 'CompanyCreate',
    component: CompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/edit',
    name: 'CompanyEdit',
    component: CompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/view',
    name: 'CompanyView',
    component: CompanyDetails,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/calendar',
    name: 'CompanyCalendar',
    component: CompanyCalendar,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/message',
    name: 'Message',
    component: Message,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/message/new',
    name: 'MessageCreate',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/message/:messageId/edit',
    name: 'MessageEdit',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/message/:messageId/view',
    name: 'MessageView',
    component: MessageDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/badge',
    name: 'Badge',
    component: Badge,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/badge/new',
    name: 'BadgeCreate',
    component: BadgeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/badge/:badgeId/edit',
    name: 'BadgeEdit',
    component: BadgeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/badge/:badgeId/view',
    name: 'BadgeView',
    component: BadgeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-supporter',
    name: 'AdvertisementSupporter',
    component: AdvertisementSupporter,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-supporter/new',
    name: 'AdvertisementSupporterCreate',
    component: AdvertisementSupporterUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-supporter/:advertisementSupporterId/edit',
    name: 'AdvertisementSupporterEdit',
    component: AdvertisementSupporterUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-supporter/:advertisementSupporterId/view',
    name: 'AdvertisementSupporterView',
    component: AdvertisementSupporterDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-supporter-status',
    name: 'AdvertisementSupporterStatus',
    component: AdvertisementSupporterStatus,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/advertisement-supporter-status/new',
    name: 'AdvertisementSupporterStatusCreate',
    component: AdvertisementSupporterStatusUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/advertisement-supporter-status/:advertisementSupporterStatusId/edit',
    name: 'AdvertisementSupporterStatusEdit',
    component: AdvertisementSupporterStatusUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/advertisement-supporter-status/:advertisementSupporterStatusId/view',
    name: 'AdvertisementSupporterStatusView',
    component: AdvertisementSupporterStatusDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/advertisement-status',
    name: 'AdvertisementStatus',
    component: AdvertisementStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-status/new',
    name: 'AdvertisementStatusCreate',
    component: AdvertisementStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-status/:advertisementStatusId/edit',
    name: 'AdvertisementStatusEdit',
    component: AdvertisementStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-status/:advertisementStatusId/view',
    name: 'AdvertisementStatusView',
    component: AdvertisementStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-duration',
    name: 'AdvertisementDuration',
    component: AdvertisementDuration,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-duration/new',
    name: 'AdvertisementDurationCreate',
    component: AdvertisementDurationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-duration/:advertisementDurationId/edit',
    name: 'AdvertisementDurationEdit',
    component: AdvertisementDurationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-duration/:advertisementDurationId/view',
    name: 'AdvertisementDurationView',
    component: AdvertisementDurationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-type',
    name: 'AdvertisementType',
    component: AdvertisementType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-type/new',
    name: 'AdvertisementTypeCreate',
    component: AdvertisementTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-type/:advertisementTypeId/edit',
    name: 'AdvertisementTypeEdit',
    component: AdvertisementTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-type/:advertisementTypeId/view',
    name: 'AdvertisementTypeView',
    component: AdvertisementTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-kind',
    name: 'AdvertisementKind',
    component: AdvertisementKind,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-kind/new',
    name: 'AdvertisementKindCreate',
    component: AdvertisementKindUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-kind/:advertisementKindId/edit',
    name: 'AdvertisementKindEdit',
    component: AdvertisementKindUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-kind/:advertisementKindId/view',
    name: 'AdvertisementKindView',
    component: AdvertisementKindDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-category',
    name: 'AdvertisementCategory',
    component: AdvertisementCategory,
  },
  {
    path: '/b2b/advertisement-category/new',
    name: 'AdvertisementCategoryCreate',
    component: AdvertisementCategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-category/:advertisementCategoryId/edit',
    name: 'AdvertisementCategoryEdit',
    component: AdvertisementCategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-category/:advertisementCategoryId/view',
    name: 'AdvertisementCategoryView',
    component: AdvertisementCategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subcategory',
    name: 'AdvertisementSubcategory',
    component: AdvertisementSubcategory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subcategory/new',
    name: 'AdvertisementSubcategoryCreate',
    component: AdvertisementSubcategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subcategory/:advertisementSubcategoryId/edit',
    name: 'AdvertisementSubcategoryEdit',
    component: AdvertisementSubcategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subcategory/:advertisementSubcategoryId/view',
    name: 'AdvertisementSubcategoryView',
    component: AdvertisementSubcategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subsubcategory',
    name: 'AdvertisementSubsubcategory',
    component: AdvertisementSubsubcategory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subsubcategory/new',
    name: 'AdvertisementSubsubcategoryCreate',
    component: AdvertisementSubsubcategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subsubcategory/:advertisementSubsubcategoryId/edit',
    name: 'AdvertisementSubsubcategoryEdit',
    component: AdvertisementSubsubcategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/advertisement-subsubcategory/:advertisementSubsubcategoryId/view',
    name: 'AdvertisementSubsubcategoryView',
    component: AdvertisementSubsubcategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration',
    name: 'Collaboration',
    component: Collaboration,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration/new',
    name: 'CollaborationCreate',
    component: CollaborationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration/:collaborationId/edit',
    name: 'CollaborationEdit',
    component: CollaborationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration/:collaborationId/view',
    name: 'CollaborationView',
    component: CollaborationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/collaborations',
    name: 'CompanyCollaborations',
    component: CompanyCollaborations,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-status',
    name: 'CollaborationStatus',
    component: CollaborationStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-status/new',
    name: 'CollaborationStatusCreate',
    component: CollaborationStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-status/:collaborationStatusId/edit',
    name: 'CollaborationStatusEdit',
    component: CollaborationStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-status/:collaborationStatusId/view',
    name: 'CollaborationStatusView',
    component: CollaborationStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-rating',
    name: 'CollaborationRating',
    component: CollaborationRating,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-rating/new',
    name: 'CollaborationRatingCreate',
    component: CollaborationRatingUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-rating/:collaborationRatingId/edit',
    name: 'CollaborationRatingEdit',
    component: CollaborationRatingUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/collaboration-rating/:collaborationRatingId/view',
    name: 'CollaborationRatingView',
    component: CollaborationRatingDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/thread',
    name: 'Thread',
    component: Thread,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/thread/new',
    name: 'ThreadCreate',
    component: ThreadUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/thread/:threadId/edit',
    name: 'ThreadEdit',
    component: ThreadUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/thread/:threadId/view',
    name: 'ThreadView',
    component: ThreadDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/company/:companyId/threads',
    name: 'CompanyThreads',
    component: CompanyThreads,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting',
    name: 'Meeting',
    component: Meeting,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting/new',
    name: 'MeetingCreate',
    component: MeetingUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting/:meetingId/edit',
    name: 'MeetingEdit',
    component: MeetingUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting/:meetingId/view',
    name: 'MeetingView',
    component: MeetingDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant',
    name: 'MeetingParticipant',
    component: MeetingParticipant,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant/new',
    name: 'MeetingParticipantCreate',
    component: MeetingParticipantUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant/:meetingParticipantId/edit',
    name: 'MeetingParticipantEdit',
    component: MeetingParticipantUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant/:meetingParticipantId/view',
    name: 'MeetingParticipantView',
    component: MeetingParticipantDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant-status',
    name: 'MeetingParticipantStatus',
    component: MeetingParticipantStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant-status/new',
    name: 'MeetingParticipantStatusCreate',
    component: MeetingParticipantStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant-status/:meetingParticipantStatusId/edit',
    name: 'MeetingParticipantStatusEdit',
    component: MeetingParticipantStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-participant-status/:meetingParticipantStatusId/view',
    name: 'MeetingParticipantStatusView',
    component: MeetingParticipantStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-type',
    name: 'MeetingType',
    component: MeetingType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-type/new',
    name: 'MeetingTypeCreate',
    component: MeetingTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-type/:meetingTypeId/edit',
    name: 'MeetingTypeEdit',
    component: MeetingTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/meeting-type/:meetingTypeId/view',
    name: 'MeetingTypeView',
    component: MeetingTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-news/search',
    name: 'CmsNewsSearch',
    component: CmsNewsSearch,
  },
  {
    path: '/b2b/cms-page',
    name: 'CmsPage',
    component: CmsPage,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page/new',
    name: 'CmsPageCreate',
    component: CmsPageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page/:cmsPageId/edit',
    name: 'CmsPageEdit',
    component: CmsPageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page/:cmsPageId/view',
    name: 'CmsPageView',
    component: CmsPageDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page-type',
    name: 'CmsPageType',
    component: CmsPageType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page-type/new',
    name: 'CmsPageTypeCreate',
    component: CmsPageTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page-type/:cmsPageTypeId/edit',
    name: 'CmsPageTypeEdit',
    component: CmsPageTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-page-type/:cmsPageTypeId/view',
    name: 'CmsPageTypeView',
    component: CmsPageTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-testimonials',
    name: 'CmsTestimonials',
    component: CmsTestimonials,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-testimonials/new',
    name: 'CmsTestimonialsCreate',
    component: CmsTestimonialsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-testimonials/:cmsTestimonialsId/edit',
    name: 'CmsTestimonialsEdit',
    component: CmsTestimonialsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-testimonials/:cmsTestimonialsId/view',
    name: 'CmsTestimonialsView',
    component: CmsTestimonialsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-question',
    name: 'CmsQuestion',
    component: CmsQuestion,
    //   meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-questions/search',
    name: 'CmsQuestionSearch',
    component: CmsQuestionSearch,
  },
  {
    path: '/b2b/cms-question/new',
    name: 'CmsQuestionCreate',
    component: CmsQuestionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-question/:cmsQuestionId/edit',
    name: 'CmsQuestionEdit',
    component: CmsQuestionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-question/:cmsQuestionId/view',
    name: 'CmsQuestionView',
    component: CmsQuestionDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-slider',
    name: 'CmsSlider',
    component: CmsSlider,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-slider/new',
    name: 'CmsSliderCreate',
    component: CmsSliderUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-slider/:cmsSliderId/edit',
    name: 'CmsSliderEdit',
    component: CmsSliderUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-slider/:cmsSliderId/view',
    name: 'CmsSliderView',
    component: CmsSliderDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-carousel',
    name: 'CmsCarousel',
    component: CmsCarousel,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-carousel/new',
    name: 'CmsCarouselCreate',
    component: CmsCarouselUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-carousel/:cmsCarouselId/edit',
    name: 'CmsCarouselEdit',
    component: CmsCarouselUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-carousel/:cmsCarouselId/view',
    name: 'CmsCarouselView',
    component: CmsCarouselDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-advertisement-category',
    name: 'CmsAdvertisementCategory',
    component: CmsAdvertisementCategory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-advertisement-category/new',
    name: 'CmsAdvertisementCategoryCreate',
    component: CmsAdvertisementCategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-advertisement-category/:cmsAdvertisementCategoryId/edit',
    name: 'CmsAdvertisementCategoryEdit',
    component: CmsAdvertisementCategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cms-advertisement-category/:cmsAdvertisementCategoryId/view',
    name: 'CmsAdvertisementCategoryView',
    component: CmsAdvertisementCategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document',
    name: 'Document',
    component: Document,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document/new',
    name: 'DocumentCreate',
    component: DocumentUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document/:documentId/edit',
    name: 'DocumentEdit',
    component: DocumentUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document/:documentId/view',
    name: 'DocumentView',
    component: DocumentDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-type',
    name: 'DocumentType',
    component: DocumentType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-type/new',
    name: 'DocumentTypeCreate',
    component: DocumentTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-type/:documentTypeId/edit',
    name: 'DocumentTypeEdit',
    component: DocumentTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-type/:documentTypeId/view',
    name: 'DocumentTypeView',
    component: DocumentTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-kind',
    name: 'DocumentKind',
    component: DocumentKind,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-kind/new',
    name: 'DocumentKindCreate',
    component: DocumentKindUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-kind/:documentKindId/edit',
    name: 'DocumentKindEdit',
    component: DocumentKindUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/document-kind/:documentKindId/view',
    name: 'DocumentKindView',
    component: DocumentKindDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
