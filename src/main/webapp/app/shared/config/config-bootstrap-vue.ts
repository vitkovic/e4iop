import {
  BForm,
  BFormInput,
  BFormCheckbox,
  BFormGroup,
  BProgress,
  BProgressBar,
  BPagination,
  BButton,
  BNavbar,
  BNavbarNav,
  BNavbarBrand,
  BNavbarToggle,
  BNavItem,
  BNavItemDropdown,
  BCollapse,
  BBadge,
  BDropdown,
  BDropdownItem,
  BLink,
  BAlert,
  BModal,
  VBModal,
  BFormDatepicker,
  BInputGroup,
  BInputGroupPrepend,
  BContainer,
  BFormRow,
  BRow,
  BCol,
  BFormTextarea,
  BFormRadio,
  BCard,
  BCardImg,
  BCardText,
  BInputGroupAppend,
  BTable,
  BFormRating,
  BFormFile,
} from 'bootstrap-vue';

export function initBootstrapVue(vue) {
  vue.component('b-badge', BBadge);
  vue.component('b-dropdown', BDropdown);
  vue.component('b-dropdown-item', BDropdownItem);
  vue.component('b-link', BLink);
  vue.component('b-alert', BAlert);
  vue.component('b-modal', BModal);
  vue.component('b-button', BButton);
  vue.component('b-navbar', BNavbar);
  vue.component('b-navbar-nav', BNavbarNav);
  vue.component('b-navbar-brand', BNavbarBrand);
  vue.component('b-navbar-toggle', BNavbarToggle);
  vue.component('b-pagination', BPagination);
  vue.component('b-progress', BProgress);
  vue.component('b-progress-bar', BProgressBar);
  vue.component('b-form', BForm);
  vue.component('b-form-input', BFormInput);
  vue.component('b-form-group', BFormGroup);
  vue.component('b-form-checkbox', BFormCheckbox);
  vue.component('b-collapse', BCollapse);
  vue.component('b-nav-item', BNavItem);
  vue.component('b-nav-item-dropdown', BNavItemDropdown);
  vue.component('b-modal', BModal);
  vue.directive('b-modal', VBModal);
  vue.component('b-form-datepicker', BFormDatepicker);
  vue.component('b-input-group', BInputGroup);
  vue.component('b-input-group-prepend', BInputGroupPrepend);
  vue.component('b-container', BContainer);
  vue.component('b-form-row', BFormRow);
  vue.component('b-row', BRow);
  vue.component('b-col', BCol);
  vue.component('b-form-textarea', BFormTextarea);
  vue.component('b-form-radio', BFormRadio);
  vue.component('b-card', BCard);
  vue.component('b-card-img', BCardImg);
  vue.component('b-card-text', BCardText);
  vue.component('b-input-group-append', BInputGroupAppend);
  vue.component('b-table', BTable);
  vue.component('b-form-rating', BFormRating);
  vue.component('b-form-file', BFormFile);
}
