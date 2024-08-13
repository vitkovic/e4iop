import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRequestForService } from '@/shared/model/request-for-service.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RequestForServiceService from './request-for-service.service';

import AccountService from '@/account/account.service';

import RequestRespondService from '@/entities/request-respond/request-respond.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RequestForService extends mixins(AlertMixin) {
  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('requestRespondService') private requestRespondService: () => RequestRespondService;

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public currentLanguage = '';

  public requestForServices: IRequestForService[] = [];

  public isFetching = false;
  public hasAnyAuthorityValue = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceId) {
        vm.retrieveForGivenService(to.params.serviceId);
      } else {
        vm.retrieveAllRequestForServices();
      }
    });
  }

  public mounted(): void {
    //this.retrieveAllRequestForServices();
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

  /**
   * Vraca otvorene zahteve za zadati servis
   */
  public retrieveForGivenService(serviceId): void {
    this.requestForServiceService()
      .retrieveOpenedForGivenService(serviceId)
      .then(
        res => {
          this.requestForServices = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRequestForServices();
  }

  public retrieveAllRequestForServices(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
   
    this.requestForServiceService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.requestForServices = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRequestForService): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRequestForService(): void {
    this.requestForServiceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.requestForService.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRequestForServices();
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
    this.retrieveAllRequestForServices();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public sendToRespond(instance: IRequestForService): void {
    this.$router.push({
      name: 'RequestRespondCreateChoice',
      params: { requestForServiceId: JSON.stringify(instance.id).replace(/["]/g, '') },
    });
  }

  public previewRespond(instance: IRequestForService): void {
    this.requestRespondService()
      .findForRequestService(instance.id)
      .then(res => {
        if (res.id) {
          this.$router.push({
            name: 'RequestRespondViewForService',
            params: { requestForServiceId: JSON.stringify(instance.id).replace(/["]/g, '') },
          });
        }
      })
      .catch(() => {
        const por = this.$t('riportalApp.requestForService.notExists');
        this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'info', duration: 10000 });
      });
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

  public get account(): any {
    return this.$store.getters.account;
  }
}
