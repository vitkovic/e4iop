import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

const About = () => import('@/pages/about/about.vue');
// prettier-ignore
const Questions = () => import('@/pages/questions/questions.vue');
// prettier-ignore
const News = () => import('@/pages/news/news.vue');
const PrivacyPolicy = () => import('@/pages/footer/privacy-policy.vue');
const TermsOfUse = () => import('@/pages/footer/terms.vue');
const CookiePolicy = () => import('@/pages/footer/cookies.vue');
const ContactUs = () => import('@/pages/footer/contact.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
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

  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
