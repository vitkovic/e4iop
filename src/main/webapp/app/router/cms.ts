import { Authority } from '@/shared/security/authority';

/* tslint:disable */
// prettier-ignore
const CMSB2B = () => import('@/cms-b2b/cms-b2b.vue');
const CMSAdministrators = () => import('@/cms-b2b/cms-b2b-administrators.vue');
const CMSReports = () => import('@/cms-b2b/cms-b2b-reports.vue');
const CMSAdvertisements = () => import('@/entities/advertisement/advertisement.vue');
const CMSCodebook = () => import('@/cms-b2b/cms-b2b-codebook.vue');
const CMSPages = () => import('@/cms-b2b/cms-b2b-pages.vue');
const CMSPageUpdateSpecific = () => import('@/entities/cms-page/cms-page-update-specific.vue');
const CMSNews = () => import('@/entities/cms-news/cms-news.vue');
const CMSNewsUpdate = () => import('@/entities/cms-news/cms-news-update.vue');
const CMSNewsDetails = () => import('@/entities/cms-news/cms-news-details.vue');
const CMSUserManagement = () => import('@/cms-b2b/cms-b2b-user-management.vue');
const CMSUsers = () => import('@/cms-b2b/cms-b2b-users.vue');
const CMSCompanies = () => import('@/cms-b2b/cms-b2b-companies.vue');
const CMSCompanyCollaborations = () => import('@/cms-b2b/cms-b2b-company-collaborations.vue');

export default [
  {
    path: '/b2b/cms',
    name: 'CMSB2B',
    component: CMSB2B,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN] },
  },
  {
    path: '/b2b/cms/administrators',
    name: 'CMSAdministrators',
    component: CMSAdministrators,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN] },
  },
  {
    path: '/b2b/cms/reports',
    name: 'CMSReports',
    component: CMSReports,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/user-management',
    name: 'CMSUserManagement',
    component: CMSUserManagement,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/user-management/users',
    name: 'CMSUsers',
    component: CMSUsers,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/user-management/companies',
    name: 'CMSCompanies',
    component: CMSCompanies,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/user-management/companies/:companyId/collaborations',
    name: 'CMSCompanyCollaborations',
    component: CMSCompanyCollaborations,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/advertisement',
    name: 'CMSAdvertisements',
    component: CMSAdvertisements,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/codebook',
    name: 'CMSCodebook',
    component: CMSCodebook,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/pages',
    name: 'CMSPages',
    component: CMSPages,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/page/edit/:pageType',
    name: 'CMSPageEditSpecific',
    component: CMSPageUpdateSpecific,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/news',
    name: 'CMSNews',
    component: CMSNews,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/news/:cmsNewsId/view',
    name: 'CMSNewsDetails',
    component: CMSNewsDetails,
    // meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/news/:cmsNewsId/edit',
    name: 'CMSNewsEdit',
    component: CMSNewsUpdate,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
  {
    path: '/b2b/cms/news/new',
    name: 'CMSNewsCreate',
    component: CMSNewsUpdate,
    meta: { authorities: [Authority.ADMIN, Authority.CMS_SUPER_ADMIN, Authority.CMS_ADMIN] },
  },
];
