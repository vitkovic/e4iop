import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICmsNews } from '@/shared/model/cms-news.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CmsNewsService from './cms-news.service';
import DocumentService from '@/entities/document/document.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CmsNews extends mixins(AlertMixin) {
  @Inject('cmsNewsService') private cmsNewsService: () => CmsNewsService;
  @Inject('documentService') private documentService: () => DocumentService;

  private removeId: number | null = null;
  public itemsPerPage = 20;
  public queryCount: number | null = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'date';
  public reverse = false;
  public totalItems = 0;
  public txtsearch;
  public cmsNews: ICmsNews[] = [];
  public txtsearchNav;

  public isFetching = false;

  public mounted(): void {
    this.txtsearch = this.$route.query.search;
    if (this.txtsearch == null) {
      const urlParams = new URLSearchParams(window.location.search);
      this.txtsearch = urlParams.get('search');
      //  this.category =  urlParams.get('category');
    }
    this.retrieveAllCmsNews();
  }


 data() {
     return {
       txtsearchNav: '',
      }
    }
  public notifsearchshown = false;
  public autoQ(): any {
    
    if (!this.notifsearchshown) {
    this.$notify({
            text: JSON.stringify(this.$t('global.navbar.autosearchnote')),
            type: 'info',
            duration: 3000,
          });
   	this.notifsearchshown = true;
   }
   if (this.txtsearchNav.length >= 3) {
	
	   this.isFetching = true;
	    
	    this.txtsearch = this.txtsearchNav;
	    
	    const paginationQuery = {
	      page: this.page - 1,
	      size: this.itemsPerPage,
	      sort: this.sort()
	    };
	    
	  	 
	     this.cmsNewsService()
	      .retrieveSearch(this.txtsearch,paginationQuery)
	      .then(
	        res => {
	          this.cmsNews = res.data;
	          this.totalItems = Number(res.headers['x-total-count']);
	          this.queryCount = this.totalItems;
	          this.isFetching = false;
	        },
	        err => {
	          this.isFetching = false;
	        }
	      );
	   } else if (this.txtsearchNav.length ==null || this.txtsearchNav.trim() == '') {
		 this.isFetching = true;
	    
	    this.txtsearch = '';
	    
	    const paginationQuery = {
	      page: this.page - 1,
	      size: this.itemsPerPage,
	      sort: this.sort()
	    };
	    
	  	 
	     this.cmsNewsService()
	      .retrieveSearch(this.txtsearch,paginationQuery)
	      .then(
	        res => {
	          this.cmsNews = res.data;
	          this.totalItems = Number(res.headers['x-total-count']);
	          this.queryCount = this.totalItems;
	          this.isFetching = false;
	        },
	        err => {
	          this.isFetching = false;
	        }
	      );
		
	   }
  }


 public searchQ(): void {
	  
	  this.txtsearch = this.txtsearchNav;
	  console.log(this.txtsearch);
	  this.retrieveAllCmsNews();
	  
  }


  public clear(): void {
    this.page = 1;
    this.retrieveAllCmsNews();
  }

  public retrieveAllCmsNews(): void {
    //console.log("usao u cms newserkerjterkljekltejtklerjterkltjekltjekltjekltejtlk");
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.cmsNewsService()
      .retrieveSearch(this.txtsearch, paginationQuery)
      .then(
        res => {
          this.cmsNews = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICmsNews): void {
    this.removeId = instance.id!;
    if (this.$refs.removeEntity) {
      (this.$refs.removeEntity as any).show();
    }
  }

  public removeCmsNews(): void {
    if (this.removeId !== null) {
      this.cmsNewsService()
        .delete(this.removeId)
        .then(() => {
          const message = this.$t('riportalApp.cmsNews.deleted', { param: this.removeId });
          this.alertService().showAlert(message, 'danger');
          this.getAlertFromStore();
          this.removeId = null;
          this.retrieveAllCmsNews();
          this.closeDialog();
        });
    }
  }

  public sort(): string[] {
    const result = [`${this.propOrder},${this.reverse ? 'asc' : 'desc'}`];
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
    this.retrieveAllCmsNews();
  }

  public changeOrder(propOrder: string): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    if (this.$refs.removeEntity) {
      (this.$refs.removeEntity as any).hide();
    }
  }

  public retrieveImage(filename: string): string {
    return this.documentService().retrieveImage(filename);
  }
}
