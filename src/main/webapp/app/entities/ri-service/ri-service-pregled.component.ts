import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiService } from '@/shared/model/ri-service.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiServiceService from './ri-service.service';
import RequestForServiceService from '@/entities/request-for-service/request-for-service.service';
import { IScientificDomain } from '@/shared/model/scientific-domain.model';
import ScientificDomainService from '@/entities/scientific-domain/scientific-domain.service';
import AccountService from '@/account/account.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiServicePregled extends mixins(AlertMixin) {
  @Inject('riServiceService') private riServiceService: () => RiServiceService;
  @Inject('requestForServiceService') private requestForServiceService: () => RequestForServiceService;
  @Inject('scientificDomainService') private scientificDomainService: () => ScientificDomainService;
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
  public search = {
    searchTerms: '',
    currentLanguage: '',
    selSubdomains: [],
  };

  public riServices: IRiService[] = [];
  public selectedSubdomains = [];
  public scientificDomains: IScientificDomain[] = [];
  public showCounter = true; // prikazuje ili ne nove strane na dnu

  public isFetching = false;
  private hasAnyAuthorityValue = false;

  public mounted(): void {
    this.retrieveAllRiServices();
    this.retrieveAllScientificDomains();
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

  public retrieveAllScientificDomains(): void {
    this.isFetching = true;

    this.scientificDomainService()
      .retrieveWithSubdomains()
      .then(
        res => {
          this.scientificDomains = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  /**
   * Pronalazi otvoreni zahtev ovog korisnika, za izabranu uslugu (service)
   * Ako nema, ide se na kreiranje novog zahteva
   * @param serviceId
   */
  public checkOpenedForGivenService(serviceId): void {
    this.requestForServiceService()
      .checkOpenedForGivenService(serviceId)
      .then(res => {
        if (res > 0) {
          this.$router.push({ name: 'RequestForService', params: { serviceId: serviceId } });
        } else {
          this.$router.push({ name: 'RequestForServiceCreateForService', params: { serviceId: serviceId } });
        }
      });
  }

  public checkEmptySearch(): boolean {
    if (!this.search.searchTerms && !this.selectedSubdomains.length) return true;
    else return false;
  }

  public searchRiService(): void {
    this.isFetching = true;
    this.search.currentLanguage = this.currentLanguage;
    this.search.selSubdomains = this.selectedSubdomains;
    if (this.checkEmptySearch()) {
      this.showCounter = true;
      this.retrieveAllRiServices();
    } else {
      this.riServiceService()
        .search(this.search)
        .then(
          res => {
            this.riServices = res.data;
            this.showCounter = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    }
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRiServices();
  }

  public retrieveAllRiServices(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.riServiceService()
      .retrievePregled(paginationQuery)
      .then(
        res => {
          this.riServices = res.data;
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
    this.retrieveAllRiServices();
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
