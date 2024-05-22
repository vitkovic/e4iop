import { Module } from 'vuex';

export const portalUserStore: Module<any, any> = {
  state: {
    roles: null,
  },
  getters: {
    roles: state => state.roles,
  },
  mutations: {
    setRoles(state, roles) {
      state.roles = roles;
    },
  },
};
