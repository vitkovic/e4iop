import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import  CMSB2BService from './cms-b2b.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
//import { IInquiry } from '@/shared/model/inquiry.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
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
 public portalUsers: IPortalUser[] = [];
  
 public itemsPerPage = 500000;
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
  public usersCount = 0;
  public currentLanguage = '';
  public propOrderCollab = 'id';
  public reverseCollab = false;
  
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
      usersCount:0,
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
      sort: this.sortCollab(),
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
    
     public retrieveUsersByDateInterval(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    
    this.cmsB2BService()
        .retrieveSearchUsersDates( this.isFetching, this.activationDatetimeFrom.toString(), this.activationDatetimeTo.toString(), paginationQuery)
        .then(
          res => {
            this.portalUsers = res.data;
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.usersCount = this.queryCount;
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


public updateInstantFieldFromUsers(event) {
    if (event.target.value) {
      this.activationDatetimeFrom= event.target.value;
      console.log( this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveUsersByDateInterval();
    } else {
      this.activationDatetimeFrom = null;
    }
    
 }
 
 public updateInstantFieldToUsers(event) {
    if (event.target.value) {
      this.activationDatetimeTo= event.target.value
      if (this.activationDatetimeFrom != null) this.retrieveUsersByDateInterval();
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
public retrieveUsers(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
        .retrieveUsers(paginationQuery)
        .then(
          res => {
            this.portalUsers = res.data;
           // console.log("Users uuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
            console.log(res.data);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.usersCount = this.queryCount;
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

 public sortCollab(): Array<any> {
    const result = [this.propOrderCollab + ',' + (this.reverseCollab ? 'asc' : 'desc')];
    if (this.propOrderCollab !== 'id') {
      result.push('id');
    }
    return result;
  }
 public mounted(): void {
	this.currentLanguage = this.$store.getters.currentLanguage;
    this.retrieveAdvertisements();
    this.retrieveCollaborations();
    this.retrieveUsers();
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
  
  
   public transitionUsers(): void {
    this.retrieveUsers();
  }

  public changeOrderUsers(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transitionUsers();
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
       // console.log(res.data);
        this.advType = 1;
      });
  
  this.advertisementKindService()
      .retrieve()
      .then(res => {
        this.advKindList = res.data;
      //  console.log(res.data);
        this.advKind = 1;
      });
  
  this.companyService()
      .retrieve()
      .then(res => {
        this.advCompanyList = res.data;
     //   console.log(res.data);
        this.advCompany = 1;
      });
      
    this.collaborationStatusService()
      .retrieve()
      .then(res => {
        this.collabStatusList = res.data;
      //  console.log(res.data);
        this.collabStatus = 1;
      });  
    
  }
  
  
  public formArrayForAdv(items): any 
  {
	  let newitems = [];
	  
   	  for (let i = 0; i < items.length; i++) { // eslint-disable-line
	    let line = '';
	  //  console.log(items[i]);
		let newitemssub = [];
		for (const index in items[i]) {
			
	  		//console.log(`${index}: ${items[i][index]}`);
	  		
	  		if (index == 'id')
	  			newitemssub[index] = items[i][index];
	  			
	  		if (index == 'title')
	  			newitemssub[index] = items[i][index];		
	  		
	  		if (index == 'description')
	  			newitemssub[index] = items[i][index];	
	  			
	  		if (index == 'activationDatetime')
	  			newitemssub[index] = items[i][index];
	  			
	  			
	  		if (index == 'createdAt')
	  			newitemssub[index] = items[i][index];	
	  			
	  		if (index == 'expirationDatetime')
	  			newitemssub[index] = items[i][index];		
	  			
	  		if (index == 'budget')
	  			newitemssub[index] = items[i][index];
	  			
	  		if (index == 'company') {
				if (items[i][index].name !== 'undefined')
				newitemssub[index] = items[i][index].name;
			}
	  		
	  		if (index == 'kinds') {
				  if (items[i][index].length >0)
				  newitemssub[index]  = items[i][index][0].kind;
				  else  newitemssub[index] = 'Nije na raspolganju';
			}
	  		
		}
		
		newitems[i] = newitemssub;
	    console.log(newitems[i]);
	  }
	  return newitems;
  }
 
 public formArrayForCollab(items): any 
  {
	  let newitems = [];
	  
   	  for (let i = 0; i < items.length; i++) { // eslint-disable-line
	    let line = '';
	  //  console.log(items[i]);
		let newitemssub = [];
		for (const index in items[i]) {
			
	  		console.log(`${index}: ${items[i][index]}`);
	  		
	  		if (index == 'id')
	  			newitemssub[index] = items[i][index];
	  			
	  			
	  		if (index == 'ratingOffer' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].description !== 'undefined')
				newitemssub[index] = items[i][index].description;
			} else if (index == 'ratingOffer') {
				newitemssub[index] = 'Nije definisano';
			}
			
			if (index == 'ratingRequest' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].description !== 'undefined')
				newitemssub[index] = items[i][index].description;
			} else if (index == 'ratingRequest') {
				newitemssub[index] = 'Nije definisano';
			}
			
			if (index == 'companyOffer' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].name !== 'undefined')
				newitemssub[index] = items[i][index].name;
			} else if (index == 'companyOffer') {
				newitemssub[index] = 'Nije definisano';
			}
			
			if (index == 'companyRequest' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].name !== 'undefined')
				newitemssub[index] = items[i][index].name;
			} else if (index == 'companyRequest') {
				newitemssub[index] = 'Nije definisano';
			}
			
			if (index == 'status' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].status !== 'undefined')
				newitemssub[index] = items[i][index].status;
			} else if (index == 'status') {
				newitemssub[index] = 'Nije definisano';
			}
			
			if (index == 'commentOffer') {
				newitemssub[index] = items[i][index];
			}
			
			if (index == 'commentRequest') {
				newitemssub[index] = items[i][index];
			}
			
			
			if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].title !== 'undefined')
				newitemssub[index] = items[i][index].title;
			} else if (index == 'advertisement') {
				newitemssub[index] = 'Nije definisano';
			}
			
			if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].description !== 'undefined')
				newitemssub[index] = items[i][index].description;
			} else if (index == 'advertisement') {
				newitemssub[index] = 'Nije definisano';
			}
			
				
	  		if (index == 'datetime')
	  			newitemssub[index] = items[i][index];	
	  		
		}
		
		newitems[i] = newitemssub;
	    console.log(newitems[i]);
	  }
	  return newitems;
  }
  
   public formArrayForUsers(items): any 
    {
	  let newitems = [];
	  console.log(items);
   	  for (let i = 0; i < items.length; i++) { // eslint-disable-line
	    let line = '';
	  //  console.log(items[i]);
		let newitemssub = [];
		for (const index in items[i]) {
			
	  		console.log(`${index}: ${items[i][index]}`);
	  		
	  		if (index == 'id')
	  			newitemssub[index] = items[i][index];
	  			
	  			
	  		if (index == 'user' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].firstName !== 'undefined')
				newitemssub['firstName'] = items[i][index].firstName;
			} else if (index == 'user') {
				newitemssub['firstName'] = 'Nije definisano';
			}
		
			
			if (index == 'user' && items[i][index] != null && items[i][index] !== 'undefined' ) {
					if (items[i][index].lastName !== 'undefined')
					newitemssub['lastName'] = items[i][index].lastName;
				} else if (index == 'user') {
					newitemssub['lastName'] = 'Nije definisano';
				}
			
		
		
			if (index == 'user' && items[i][index] != null && items[i][index] !== 'undefined' ) {
					if (items[i][index].email !== 'undefined')
					newitemssub['email'] = items[i][index].email;
					console.log("email" + newitemssub[index]);
				} else if (index == 'user') {
					newitemssub['email'] = 'Nije definisano';
				}
			
		
			if (index == 'company' && items[i][index] != null && items[i][index] !== 'undefined' ) {
				if (items[i][index].name !== 'undefined')
					newitemssub[index] = items[i][index].name;
				} else if (index == 'company') {
					newitemssub[index] = 'Nije definisano';
				}
		
			if (index == 'userOrganization' && items[i][index] != null && items[i][index] !== 'undefined' ) {
					if (items[i][index].legalNameSr !== 'undefined')
					newitemssub[index] = items[i][index].legalNameSr;
				} else if (index == 'userOrganization') {
					newitemssub[index] = 'Nije definisano';
				}
		
			if (index == 'phone')
	  			newitemssub[index] = items[i][index];	
	  		
			if (index == 'createdAt')
	  			newitemssub[index] = items[i][index];	
	  		
	  		if (index == 'advertisementCreateds')
	  			newitemssub[index] = items[i][index];	
	  			
	  		
		}
		
		newitems[i] = newitemssub;
	    console.log(newitems[i]);
	  }
	  return newitems;
  }
  
  
  
  public exportCSVFile(items, fileTitle): any {
	  
  var headers;
  var type;
  
  items = this[items];
  
  
  if (fileTitle == 'advertisements') { 
	  headers = { 
  		id: 'ID', createdAt: 'Kreiran',activationD:'Datum aktivacije',  expirationD:'Datum isteka', title:'Naslov', desc:'Opis',budget:'Budžet' , kinds:'Kategorija',  company:'Kompanija'};
  	  type = 0;
  	  items = this.formArrayForAdv(items);	
  	  
  } else if (fileTitle == 'collaborations') { 
	    headers = { 
  		id: 'ID', createdAt: 'Datum', commentO:'Komentar oglašivača', commentT:'Komentar Tražioca', advC1:'Oglašivač',
  		advC2:'Tražilac', adv:'Naslov oglasa', mark1:'Ocena Oglašivača', mark2:'Ocena tražioca', state:'Status'};
  	  type = 1;	
  	  items = this.formArrayForCollab(items);	
  }
  
  else if (fileTitle == 'users') { 
	    headers = { 
  		id: 'ID',  createdat:"Datum kreiranja",phone:'Telefon',name: 'Ime', last:'Prezime' 
  		,email:'E-mail', ads:'Broj kreiranih oglasa', company:'Kompanija', organisation:'Organizacija'};
  	  type = 2;	
  	  console.log("users");
  	  items = this.formArrayForUsers(items);	
  }
  
  
  
  
  if (headers) {
    items.unshift(headers);
  }
  
  console.log(items);
  
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
   // console.log(array[i]);
    let j = 0;
	for (const index in array[i]) {
		if (j==9 && type == 0) break;
		if (j==11 && type == 1) break;
		if (j==9 && type == 2) break;
  	//	console.log(`${index}: ${array[i][index]}`);
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

 public changeOrderCollab(propOrderCollab): void {
    this.propOrderCollab =propOrderCollab;
    this.reverseCollab = !this.reverseCollab;
    this.transitionCollab();
  }
  
  public transitionCollab(): void {
    this.retrieveCollaborations();
  }

 

}
