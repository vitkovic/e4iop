import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

const About = () => import('@/pages/about/about.vue');
// prettier-ignore
const Questions = () => import('@/pages/questions/questions.vue');
// prettier-ignore
const News = () => import('@/pages/news/news.vue');

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

  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
