import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import { required, email } from 'vuelidate/lib/validators';

import { ICompany } from '@/shared/model/company.model';

import AccountService from '@/account/account.service';
import CompanyService from '@/entities/company.service';
import PortalUserService from '@/entities/portal-user.service';
import UserManagementService from '@/admin/user-management.service';

import { Authority } from '../shared/security/authority';

const validations: any = {
  emailAddress: { required, email },
};

@Component({
  mixins: [Vue2Filters.mixin],
  validations,
})
export default class CompanyPortalUsers extends mixins(AlertMixin) {
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('userService') private userManagementService: () => UserManagementService;

  private hasAnyAuthorityValue = false;

  public company: ICompany | null = null;
  public companyId: number;
  public users: any[] = [];
  public userToDelete: any | null = null;
  public emailAddress: string | null = null;

  public authority = Authority;

  public error: string | null = '';
  public success: string | null = '';

  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.retrieveAllB2BCMSUsers();
    });
  }

  public retrieveAllB2BCMSUsers() {
    this.userManagementService()
      .findAllB2BCMSUsers({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .then(res => {
        this.users = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
      });
  }

  public sort(): any {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllB2BCMSUsers();
  }

  public changeOrder(propOrder: string): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public authorityLanguage(authority: string): string {
    if (authority != this.authority.CMS_SUPER_ADMIN && authority != this.authority.CMS_ADMIN) {
      return '';
    }

    return this.$t('authority.cms.' + authority);
  }

  public setActive(user, isActivated): void {
    this.userManagementService()
      .activateUser(user.login, isActivated)
      .then(() => {
        this.error = null;
        this.success = 'OK';
        this.retrieveAllB2BCMSUsers();
      })
      .catch(() => {
        this.success = null;
        this.error = 'ERROR';
        this.retrieveAllB2BCMSUsers();
        // user.activated = false;
      });
  }

  public prepareDeleteUserModal(user) {
    this.userToDelete = user;

    if (<any>this.$refs.deleteUserModal) {
      (<any>this.$refs.deleteUserModal).show();
    }
  }

  public closeDeleteUserModal(): void {
    (<any>this.$refs.deleteUserModal).hide();
  }

  public deleteUser() {
    this.userManagementService()
      .remove(this.userToDelete.login)
      .then(res => {
        this.retrieveAllB2BCMSUsers();
        const notificatonMessage = 'Administrator je obrisan';
        this.$notify({
          text: notificatonMessage,
        });
      });

    this.closeDeleteUserModal();
  }
}
