import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPortalUser } from '@/shared/model/portal-user.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PortalUserService from './portal-user.service';
import AccountService from '@/account/account.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PortalUser extends mixins(AlertMixin) {
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('accountService') private accountService: () => AccountService;

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;

  public portalUsers: IPortalUser[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPortalUsers();
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllPortalUsers();
  }

  public retrieveAllPortalUsers(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.portalUserService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.portalUsers = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPortalUser): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePortalUser(): void {
    this.portalUserService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.portalUser.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPortalUsers();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
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
    this.retrieveAllPortalUsers();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public hasAnyRole(roles: any): boolean {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
