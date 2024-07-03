import { Authority } from '@/shared/security/authority';

/* tslint:disable */
// prettier-ignore
const CMSB2B = () => import('@/cms-b2b/cms-b2b.vue');
const CMSAdvertsement = () => import('@/entities/advertisement/advertisement.vue');
const CMSCodebook = () => import('@/cms-b2b/cms-b2b-codebook.vue');
const CMSNews = () => import('@/entities/cms-news/cms-news.vue');
const CMSNewsUpdate = () => import('@/entities/cms-news/cms-news-update.vue');
const CMSNewsDetails = () => import('@/entities/cms-news/cms-news-details.vue');

export default [
  {
    path: '/b2b/cms',
    name: 'CMSB2B',
    component: CMSB2B,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/advertisement',
    name: 'CMSAdvertsement',
    component: CMSAdvertsement,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/codebook',
    name: 'CMSCodebook',
    component: CMSCodebook,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/news',
    name: 'CMSNews',
    component: CMSNews,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/news/:cmsNewsId/view',
    name: 'CMSNewsDetails',
    component: CMSNewsDetails,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/news/:cmsNewsId/view',
    name: 'CMSNewsEdit',
    component: CMSNewsUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/b2b/cms/news/new',
    name: 'CMSNewsCreate',
    component: CMSNewsUpdate,
    meta: { authorities: [Authority.ADMIN] },
  },
];