import Vuex from 'vuex';
import VueI18n from 'vue-i18n';
import JhiFormatter from './formatter';
import { setupAxiosInterceptors } from '@/shared/config/axios-interceptor';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons/faArrowLeft';
import { faAsterisk } from '@fortawesome/free-solid-svg-icons/faAsterisk';
import { faBan } from '@fortawesome/free-solid-svg-icons/faBan';
import { faBars } from '@fortawesome/free-solid-svg-icons/faBars';
import { faBell } from '@fortawesome/free-solid-svg-icons/faBell';
import { faBook } from '@fortawesome/free-solid-svg-icons/faBook';
import { faCloud } from '@fortawesome/free-solid-svg-icons/faCloud';
import { faCogs } from '@fortawesome/free-solid-svg-icons/faCogs';
import { faEye } from '@fortawesome/free-solid-svg-icons/faEye';
import { faFlag } from '@fortawesome/free-solid-svg-icons/faFlag';
import { faHdd } from '@fortawesome/free-solid-svg-icons/faHdd';
import { faHeart } from '@fortawesome/free-solid-svg-icons/faHeart';
import { faHome } from '@fortawesome/free-solid-svg-icons/faHome';
import { faList } from '@fortawesome/free-solid-svg-icons/faList';
import { faLock } from '@fortawesome/free-solid-svg-icons/faLock';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons/faPencilAlt';
import { faPlus } from '@fortawesome/free-solid-svg-icons/faPlus';
import { faRoad } from '@fortawesome/free-solid-svg-icons/faRoad';
import { faSave } from '@fortawesome/free-solid-svg-icons/faSave';
import { faSearch } from '@fortawesome/free-solid-svg-icons/faSearch';
import { faSignInAlt } from '@fortawesome/free-solid-svg-icons/faSignInAlt';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons/faSignOutAlt';
import { faSort } from '@fortawesome/free-solid-svg-icons/faSort';
import { faSortDown } from '@fortawesome/free-solid-svg-icons/faSortDown';
import { faSortUp } from '@fortawesome/free-solid-svg-icons/faSortUp';
import { faSync } from '@fortawesome/free-solid-svg-icons/faSync';
import { faTachometerAlt } from '@fortawesome/free-solid-svg-icons/faTachometerAlt';
import { faTasks } from '@fortawesome/free-solid-svg-icons/faTasks';
import { faThList } from '@fortawesome/free-solid-svg-icons/faThList';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import { faTimes } from '@fortawesome/free-solid-svg-icons/faTimes';
import { faTrash } from '@fortawesome/free-solid-svg-icons/faTrash';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons/faUserPlus';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';
import { faWrench } from '@fortawesome/free-solid-svg-icons/faWrench';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons/faQuestionCircle';
import { faCheck } from '@fortawesome/free-solid-svg-icons/faCheck';
import { faCopy } from '@fortawesome/free-solid-svg-icons/faCopy';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons/faEnvelope';
import { faStar } from '@fortawesome/free-solid-svg-icons/faStar';
import { faHandshake } from '@fortawesome/free-solid-svg-icons/faHandshake';
import { faLightbulb } from '@fortawesome/free-solid-svg-icons/faLightbulb';
import { faToggleOn } from '@fortawesome/free-solid-svg-icons/faToggleOn';
import { faCaretLeft } from '@fortawesome/free-solid-svg-icons/faCaretLeft';
import { faCaretRight } from '@fortawesome/free-solid-svg-icons/faCaretRight';
import { faShareAlt } from '@fortawesome/free-solid-svg-icons/faShareAlt';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons/faCalendarAlt';

import VueCookie from 'vue-cookie';
import Vuelidate from 'vuelidate';
import Vue2Filters from 'vue2-filters';

import * as filters from '@/shared/date/filters';
import { accountStore } from '@/shared/config/store/account-store';
import { alertStore } from '@/shared/config/store/alert-store';
import { translationStore } from '@/shared/config/store/translation-store';
import { portalUserStore } from '@/shared/config/store/portal-user-store';

const dateTimeFormats = {
  sr: {
    short: {
      year: 'numeric',
      month: 'numeric',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
    },
    shortWithoutMinutes: {
      year: 'numeric',
      month: 'numeric',
      day: 'numeric',
    },
    medium: {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      weekday: 'short',
      hour: 'numeric',
      minute: 'numeric',
    },
    long: {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      weekday: 'long',
      hour: 'numeric',
      minute: 'numeric',
    },
  },
  en: {
    short: {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
    },
    shortWithoutMinutes: {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    },
    medium: {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      weekday: 'short',
      hour: 'numeric',
      minute: 'numeric',
    },
    long: {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      weekday: 'long',
      hour: 'numeric',
      minute: 'numeric',
    },
  },
  // jhipster-needle-i18n-language-date-time-format - JHipster will add/remove format options in this object
};

export function initVueApp(vue) {
  vue.use(VueCookie);
  vue.use(Vuelidate);
  vue.use(Vue2Filters);
  setupAxiosInterceptors(() => console.log('Unauthorized!'));
  filters.initFilters();
}

export function initFortAwesome(vue) {
  library.add(
    faArrowLeft,
    faAsterisk,
    faBan,
    faBars,
    faBell,
    faBook,
    faCloud,
    faCogs,
    faEye,
    faFlag,
    faHdd,
    faHeart,
    faHome,
    faList,
    faLock,
    faPencilAlt,
    faPlus,
    faRoad,
    faSave,
    faSearch,
    faSignInAlt,
    faSignOutAlt,
    faSort,
    faSortDown,
    faSortUp,
    faSync,
    faTachometerAlt,
    faTasks,
    faThList,
    faTimes,
    faTimesCircle,
    faTrash,
    faUser,
    faUserPlus,
    faWrench,
    faQuestionCircle,
    faCheck,
    faCopy,
    faEnvelope,
    faStar,
    faHandshake,
    faLightbulb,
    faToggleOn,
    faCaretLeft,
    faCaretRight,
    faShareAlt,
    faCalendarAlt
  );
}

export function initI18N(vue) {
  vue.use(VueI18n);
  return new VueI18n({
    dateTimeFormats,
    silentTranslationWarn: true,
    formatter: new JhiFormatter(),
  });
}

export function initVueXStore(vue) {
  vue.use(Vuex);
  return new Vuex.Store({
    modules: {
      accountStore,
      alertStore,
      translationStore,
      portalUserStore,
    },
  });
}
