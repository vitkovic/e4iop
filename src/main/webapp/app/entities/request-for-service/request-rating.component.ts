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
export default class RequestRating extends mixins(AlertMixin) {
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
  public isSaving = false;
  public hasAnyAuthorityValue = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.retrieveAllRequestForServices();
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

  public save(index: number): void {
    this.isSaving = true;
    if (this.requestForServices[index].id) {
      this.requestForServiceService()
        .update(this.requestForServices[index])
        .then(res => {
          this.isSaving = false;
          this.requestForServices[index] = res;
        });
    }
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
      .retrieveForRating(paginationQuery)
      .then(
        res => {
          this.requestForServices = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
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

  public previewRespond(instance: IRequestForService): any {
	
	return true;
	/*
    this.requestRespondService()
      .findForRequestService(instance.id)
      .then(res => {
        if (res.id) {
          return true;
        }
      })
      .catch(() => {
        return false;
      });
      */
  }
}
