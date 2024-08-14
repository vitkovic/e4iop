import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

const About = () => import('@/pages/about/about.vue');
// prettier-ignore
const Questions = () => import('@/pages/questions/questions.vue');
// prettier-ignore
const News = () => import('@/entities/cms-news/cms-news-search.vue');
const NewsView = () => import('@/pages/news/news.vue');
const PrivacyPolicy = () => import('@/pages/footer/privacy-policy.vue');
const TermsOfUse = () => import('@/pages/footer/terms.vue');
const CookiePolicy = () => import('@/pages/footer/cookies.vue');
const ContactUs = () => import('@/pages/footer/contact.vue');
const RiPrivacyPolicy = () => import('@/pages/ri-footer/ri-privacy-policy.vue');
const RiContact = () => import('@/pages/ri-footer/ri-contact.vue');
const RiAbout = () => import('@/pages/ri-footer/ri-about.vue');
const RiInstructions = () => import('@/pages/ri-footer/ri-instructions.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/privacy-policy',
    name: 'Privacy policy',
    component: RiPrivacyPolicy,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/about',
    name: 'About',
    component: RiAbout,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/contact',
    name: 'Contact',
    component: RiContact,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/instructions',
    name: 'Instructions',
    component: RiInstructions,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/about',
    name: 'About',
    component: About,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/questions',
    name: 'Questions',
    component: Questions,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/privacy-policy',
    name: 'PrivacyPolicy',
    component: PrivacyPolicy,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/terms-of-use',
    name: 'TermsOfUse',
    component: TermsOfUse,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/cookie-policy',
    name: 'CookiePolicy',
    component: CookiePolicy,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/contact-us',
    name: 'ContactUs',
    component: ContactUs,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/news',
    name: 'News',
    component: News,
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/b2b/news/:cmsNewsId',
    name: 'NewsView',
    component: NewsView,
    // meta: { authorities: [Authority.USER] },
  },

  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
