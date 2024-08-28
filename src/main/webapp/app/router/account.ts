import { Authority } from '@/shared/security/authority';

const Register = () => import('@/account/register/register.vue');
const confirmAccountData = () => import('@/account/register/confirm-account-data.vue');
const Activate = () => import('@/account/activate/activate.vue');
const ResetPasswordInit = () => import('@/account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('@/account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('@/account/change-password/change-password.vue');
const Settings = () => import('@/account/settings/settings.vue');
const MeetingAccept = () => import('@/account/meeting-accept/meeting-accept.vue');
const AdvertisementSupporterAccept = () => import('@/account/advertisement-supporter-accept/advertisement-supporter-accept.vue');

export default [
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/b2b/register/confirm',
    name: 'confirmAccountData',
    component: confirmAccountData,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/account/activate',
    name: 'Activate',
    component: Activate,
  },
  {
    path: '/account/reset/request',
    name: 'ResetPasswordInit',
    component: ResetPasswordInit,
  },
  {
    path: '/account/reset/finish',
    name: 'ResetPasswordFinish',
    component: ResetPasswordFinish,
  },
  {
    path: '/account/password',
    name: 'ChangePassword',
    component: ChangePassword,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/account/settings',
    name: 'Settings',
    component: Settings,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/b2b/meeting-accept',
    name: 'MeetingAccept',
    component: MeetingAccept,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/b2b/advertisement-supporter-accept',
    name: 'AdvertisementSupporterAccept',
    component: AdvertisementSupporterAccept,
    meta: { authorities: [Authority.USER] },
  },
];
