import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdvertisementService from './advertisement.service';

import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';

import AccountService from '@/account/account.service';

//import { IdRenderer } from "./IdRenderer";

import { ICellRendererParams } from '@ag-grid-community/core';




@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Advertisement extends mixins(AlertMixin) {
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  @Inject('accountService') private accountService: () => AccountService;

  public advertisementStatuses: IAdvertisementStatus[] = [];
  private hasAnyAuthorityValue = false;

  public itemsPerPage = 1000000;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public advertisements: IAdvertisement[] = [];
  public selectedAdvertisements: IAdvertisement[] = [];

  public isFetching = false;
  public txtsearch;
  public category;
  public tlang = this.$t;
   public columnDefs = [];
   public rowData = [];
  
   data() {
      return {
       columnDefs: [
	        { headerName: this.$t('riportalApp.advertisement.title'), field: "title",filter:'true', sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.status'), field: "status",filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.type'), field: "type",filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.kind'), field: "kind",filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.subsubcategory'), field: "subsubcategory",filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.budget'), field: "budget",filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.company'), field: "company",filter:'true',filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.activationDatetime'), field: "datetime",filter:'true',sortable: true },
	        { headerName: this.$t('riportalApp.advertisement.id'), field: "id",filter:'true', cellRenderer: this.IdRenderer },
	      
	      ],
       rowData: [],
      }
    }
  
 public IdRenderer(params: ICellRendererParams):any {
    const lnktxt = this.$t('entity.action.view');
    const link = `<a href="/b2b/advertisement/${params.value}/view" target="_blank">`+this.$t('entity.action.view')+`</a>`;
    
   
    return link;
}
  public mounted(): void {
	 this.txtsearch = this.$route.query.search;
     this.category =  this.$route.query.category;
     
     if (this.txtsearch == null) {
     const urlParams = new URLSearchParams(window.location.search);
	 this.txtsearch = urlParams.get('search');
     this.category =  urlParams.get('category');
    }
    
	  
    this.retrieveAllAdvertisements();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllAdvertisements();
  }

  public retrieveAllAdvertisements(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    
	 
    this.advertisementService()
       .retrieveSearch(this.txtsearch, Number(this.category),paginationQuery)
        .then(
        res => {
          // Ovo koristiti az originalno povucene rezultate pretrage
          this.advertisements = res.data;
          console.log(this.advertisements);
         
          
          this.rowData =  this.createRows(this.advertisements)
          
          console.log(this.rowData);
          // Ovo koristiti za filtrirane rezultate pretrage
          this.selectedAdvertisements = this.advertisements;

          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
     
  }

  public createRows(advs): any {
   
   var i = 0;
   var rows = [];
  	while (i < advs.length) {
	   	
	    var subobj = {};
	  
	    subobj['title'] = advs[i].title;
	    subobj['status'] = advs[i].status.status;
	    subobj['type'] = advs[i].type.type);
	    subobj['kind'] = advs[i].kind.kind;
	    subobj['subsubcategory'] = advs[i].subsubcategory.name;
	    subobj['budget']=advs[i].budget;
	    subobj['company'] = advs[i].company.name;
	    subobj['datetime'] = advs[i].activationDatetime;
	    subobj['id'] = advs[i].id;
	   
	    
	   
     	rows.push(subobj);
	    console.log(rows);
	    i++;
	}
    console.log(rows);
  	return rows;
  
  
  [
    "Analiza faktora zastoja mašine",
    "Активан",
    "Потражња",
    "Експертиза",
    "статистичка обрада",
    42000,
    "B2B Kompanija",
    "2024-05-29T10:58:05.408Z"
]
  
  
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
    this.retrieveAllAdvertisements();
  }

  public changeOrder(propOrder): void {
	//  console.log("change order klfjsdkfldjlfdjfsdlfjsdlfj");
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public getExpirationDate(advertisement: IAdvertisement): Date {
    const expirationDate = new Date(advertisement.activationDatetime);
    expirationDate.setMonth(expirationDate.getMonth() + Number(advertisement.duration.duration));

    return expirationDate;
  }
}
