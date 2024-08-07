import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICompany } from '@/shared/model/company.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CompanyService from './company.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Company extends mixins(AlertMixin) {
  @Inject('companyService') private companyService: () => CompanyService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public txtsearch;
  public companies: ICompany[] = [];

  public isFetching = false;

  public mounted(): void {
	 //  console.log("Usao asjghdfashjkdajkdsahdajkdhasjkdhasjkdsahdjkahdsjkadhsakjdhak");
	 this.txtsearch = this.$route.query.search;  
	  
	if (this.txtsearch == null) {
     const urlParams = new URLSearchParams(window.location.search);
	 this.txtsearch = urlParams.get('search');
   //  this.category =  urlParams.get('category');
    }
    this.retrieveAllCompanys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCompanys();
  }

  public retrieveAllCompanys(): void {
    this.isFetching = true;
   // console.log("Usao asjghdfashjkdajkdsahdajkdhasjkdhasjkdsahdjkahdsjkadhsakjdhak");
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.companyService()
       .retrieveSearch(this.txtsearch,paginationQuery)
      .then(
        res => {
          this.companies = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICompany): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCompany(): void {
    this.companyService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.company.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCompanys();
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
    this.retrieveAllCompanys();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
