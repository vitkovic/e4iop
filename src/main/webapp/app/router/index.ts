import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router from 'vue-router';

const Home = () => import('@/core/home/home.vue');
const B2BHome = () => import('@/core/b2b-home/b2b-home.vue');
const Error = () => import('@/core/error/error.vue');
import account from '@/router/account.ts';
import admin from '@/router/admin.ts';
import entities from '@/router/entities.ts';
import pages from '@/router/pages.ts';

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/b2b',
      name: 'B2BHome',
      component: B2BHome
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/upload',
      name: 'Upload'
    },
    {
      path: '/research-institutions/:name',
      name: 'RIN'
    },
     {
      path: '/research-infrastructures/:name',
      name: 'RINS'
    },
     {
      path: '/research-institutions/en/:name',
      name: 'RINSEN'
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    ...account,
    ...admin,
    ...entities,
    ...pages
  ]
});
