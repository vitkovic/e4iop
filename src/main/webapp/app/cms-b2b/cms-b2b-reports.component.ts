import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import  CMSB2BService from './cms-b2b.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import AccountService from '@/account/account.service';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CMSB2BReports extends Vue {
 
 @Inject('cmsB2BService') private cmsB2BService: () => CMSB2BService;
 @Inject('accountService') private accountService: () => AccountService;
  
 public advertisements: IAdvertisement[] = [];
 public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public isFetching = false;

 private hasAnyAuthorityValue = false;
  
public retrieveAdvertisements(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
        .retrieve(paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
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

 public mounted(): void {
    this.retrieveAdvertisements();
 }

 public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }
public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAdvertisements();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

}
