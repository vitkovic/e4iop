import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import  CMSB2BService from './cms-b2b.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IInquiry } from '@/shared/model/inquiry.model';
import AccountService from '@/account/account.service';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import AdvertisementCategoryService from '@/entities/advertisement-category/advertisement-category.service';
import AdvertisementTypeService from '@/entities/advertisement-type/advertisement-type.service';
import AdvertisementKindService from '@/entities/advertisement-kind/advertisement-kind.service';
import CollaborationStatusService from '@/entities/collaboration-status/collaboration-status.service';
import CompanyService from '@/entities/company/company.service';
import { numeric, required, minLength, maxLength, minValue, maxValue, requiredIf } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CMSB2BReports extends Vue {
 
 @Inject('cmsB2BService') private cmsB2BService: () => CMSB2BService;
 @Inject('accountService') private accountService: () => AccountService;
 @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;
 @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
 @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;
 @Inject('companyService') private companyService: () => CompanyService;
 @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;
   
 public advertisements: IAdvertisement[] = [];
 public collaborations: ICollaboration[] = [];
 
 public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public isFetching = false;
  public advCount = 0;
  private advCategList = null;
  private mainSearchCategory = null;
  public advType = 1;
  public advTypeList = null;
  public advKind = 1;
  public advKindList = null;
  public advCompany = 1;
  public advCompanyList = null;
  public activationDatetimeTo = null;
  public activationDatetimeFrom = null;
  public collabStatusList = null;
  public collabStatus = 1;
  public collabCount = 0;
  public inqStatus = 1;
  public inqCount = 0;
  public inqStatusList = null;
  
  data() {
    return {
      advcount: 0,
      mainSearchCategory: 1,
      advType:1,
      advKind:1,
      collabCount: 0,
      collabStatus: 1,
      inqCount: 0,
      inqStatus: 1,
      activationDatetimeTo: null,
      activationDatetimeFrom: null
    }
  }
  
 
   
 private hasAnyAuthorityValue = false;


public retrieveCollaborations(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
   
    this.cmsB2BService()
        .retrieveCoolaborationsByStatus(this.collabStatus, paginationQuery)
        .then(
          res => {
            this.collaborations = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.collabCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
  


  
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
            this.advCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
  
 public retrieveAdvertisementsByCategory(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
        .retrieveSearch("", this.mainSearchCategory, paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.advCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
    
    
     public retrieveAdvertisementsByDateInterval(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    
    this.cmsB2BService()
        .retrieveSearchDates(this.activationDatetimeFrom.toString(), this.activationDatetimeTo.toString(), paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.advCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
    
    
    
     public retrieveAdvertisementsByKind(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
        .retrieveSearchKind(this.advKind,paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.advCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
    
    
    
    public retrieveAdvertisementsByType(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
        .retrieveSearchType(this.advType, paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.advCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
  public retrieveCollaborationsByDateInterval(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    
    this.cmsB2BService()
        .retrieveSearchCollabDates(this.activationDatetimeFrom.toString(), this.activationDatetimeTo.toString(),this.collabStatus, paginationQuery)
        .then(
          res => {
            this.collaborations = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.collabCount = this.queryCount;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
   
       
    }
 public updateInstantFieldFrom(event) {
    if (event.target.value) {
      this.activationDatetimeFrom= event.target.value;
      console.log( this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveAdvertisementsByDateInterval();
    } else {
      this.activationDatetimeFrom = null;
    }
    
 }
 
 public updateInstantFieldTo(event) {
    if (event.target.value) {
      this.activationDatetimeTo= event.target.value
      if (this.activationDatetimeFrom != null) this.retrieveAdvertisementsByDateInterval();
    } else {
      this.activationDatetimeTo = null;
    }
    
 }

public updateInstantFieldFromCollab(event) {
    if (event.target.value) {
      this.activationDatetimeFrom= event.target.value;
      console.log( this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveCollaborationsByDateInterval();
    } else {
      this.activationDatetimeFrom = null;
    }
    
 }
 
 public updateInstantFieldToCollab(event) {
    if (event.target.value) {
      this.activationDatetimeTo= event.target.value
      if (this.activationDatetimeFrom != null) this.retrieveCollaborationsByDateInterval();
    } else {
      this.activationDatetimeTo = null;
    }
    
 }


 
 public retrieveAdvertisementsByCompany(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
        .retrieveSearchCompany(paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.advCount = this.queryCount;
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
    this.initRelationships();
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
  
  public initRelationships(): void {
    this.advertisementCategoryService()
      .retrieve()
      .then(res => {
       this.advCategList = res.data;
       this.mainSearchCategory = 1;
      });
  
  this.advertisementTypeService()
      .retrieve()
      .then(res => {
        this.advTypeList = res.data;
        console.log(res.data);
        this.advType = 1;
      });
  
  this.advertisementKindService()
      .retrieve()
      .then(res => {
        this.advKindList = res.data;
        console.log(res.data);
        this.advKind = 1;
      });
  
  this.companyService()
      .retrieve()
      .then(res => {
        this.advCompanyList = res.data;
        console.log(res.data);
        this.advCompany = 1;
      });
      
    this.collaborationStatusService()
      .retrieve()
      .then(res => {
        this.collabStatusList = res.data;
        console.log(res.data);
        this.collabStatus = 1;
      });  
    
  }
  public exportCSVFile(items, fileTitle): any {
	  
  var headers;
  var type;
  
  if (fileTitle == 'advertisements') { 
	  headers = { 
  		id: 'ID', createdAt: 'Kreiran', modifiedAt:'Modifikovan', activationD:'Datum aktivacije', expirationD:'Datum isteka',
  		delD:'Datum brisanja', title:'Naslov', desc:'Opis', budget:'Budžet'};
  	  type = 0;	
  } else if (fileTitle == 'collaborations') { 
	    headers = { 
  		id: 'ID', createdAt: 'Datum', isa:'Prihvacen',commentO:'Komentar oglašivača', commentT:'Komentar Tražioca', advC1:'Oglašivač',
  		advC2:'Tražilac', adv:'Naslov oglasa', mark1:'Ocena Oglašivača', mark2:'Ocena tražioca', state:'Status'};
  	  type = 1;	
  }
  
  
  items = this[items];
  
  if (headers) {
    items.unshift(headers);
  }
  
 
  
 // const jsonObject = JSON.stringify(items);
  const csv = this.convertToCSV(items,type);
  const exportedFilename = fileTitle + '.csv' || 'export.csv'; // eslint-disable-line
  const blob = new Blob(["\ufeff",csv], { type: 'text/csv;charset=utf-8;' });
   if ((window.navigator as any).msSaveBlob) {
        (window.navigator as any).msSaveBlob(blob, exportedFilename);
      
  } else {
    const link = document.createElement('a');
    if (link.download !== undefined) {
      const url = URL.createObjectURL(blob);
      link.setAttribute('href', url);
      link.setAttribute('download', exportedFilename);
      link.style.visibility = 'hidden';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }
}
public convertToCSV(objArray, type): any {
  const array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
  let str = '';
  for (let i = 0; i < array.length; i++) { // eslint-disable-line
    let line = '';
    console.log(array[i]);
    let j = 0;
	for (const index in array[i]) {
		if (j==9 && type == 0) break;
		if (j==11 && type == 1) break;
  		console.log(`${index}: ${array[i][index]}`);
  		let div = document.createElement("div");
		div.innerHTML = array[i][index];
		let text = div.textContent || div.innerText || "";
  		str += text + ",";
  		j++;
	}
    str += '\r\n'; // eslint-disable-line
  }
  return str;
}

 public changeOrderCollab(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transitionCollab();
  }
  
  public transitionCollab(): void {
    this.retrieveCollaborations();
  }

 

}
