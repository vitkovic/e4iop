import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import B2BJhiFooter from '@/core/b2b-jhi-footer/b2b-jhi-footer.vue';
import B2BJhiNavbar from '@/core/b2b-jhi-navbar/b2b-jhi-navbar.vue';
import SearchSection from '@/core/b2b-jhi-navbar/b2b-searchsection.vue';
import LoginForm from '@/account/login-form/login-form.vue';


@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'jhi-footer': JhiFooter,
    'b2b-jhi-navbar': B2BJhiNavbar,
    'b2b-searchsection': SearchSection,
    'b2b-jhi-footer': B2BJhiFooter,
    'login-form': LoginForm,
    'b2b-searchsection':SearchSection
   
  },
})
export default class App extends Vue {
   
 
   
   public advertisements = [];
   public cmsNews = [];
   public cmsquestions = [];
   public companies = [];
   public showbutt = false;
   public columnDefs = [];
   public rowData = [];
   public currentLanguage = '';
   
   data() {
      return {
       advertisements: [],
       cmsnews:[],
       cmsquestions:[],
       companies:[],
       toggle:true,
       dissbutton:'disabled',
       showbutt:false,
       columnDefs: [
	        { headerName: this.$t('riportalApp.advertisement.title'), field: "make",filter:'true' },
	        { headerName: "Model", field: "model" },
	        { headerName: "Price", field: "price" },
	      ],
       rowData: [
	        { make: "Toyota", model: "Celica", price: 35000 },
	        { make: "Ford", model: "Mondeo", price: 32000 },
	        { make: "Porsche", model: "Boxster", price: 72000 },
	      ],
      }
    }

	
  

  public isB2BDomain = false;
  
  mounted() {
  /*
    fetch('https://www.ag-grid.com/example-assets/small-row-data.json')
             .then(result => result.json())
             .then(rowData => this.rowData = rowData);
 */ 	
  }
  created() {
    this.currentLanguage = this.$store.getters.currentLanguage;
    //alert(JSON.stringify($t('riportalApp.advertisement.title')));
  }
  
  public changeAdv(value,event): void {
  //    console.log(value[0]);
   //   console.log(this.showbutt);
      this.advertisements = value;
  //    console.log(value);
      if (value != null && typeof(value)!='undefined' && this.advertisements.length > 0 )
      	this.showbutt = true;
      else
      	this.showbutt = false;
      	
    //  	console.log(this.showbutt);
      
      console.log(this.advertisements)
      
  }
  
  public changeNews(value,event): void {
     console.log("news");
      
      this.cmsnews = value;
      
       if (value != null && typeof(value)!='undefined' && this.cmsnews.length > 0 )
      	this.showbutt = true;
      else
      	this.showbutt = false;
      
   //   console.log(this.cmsnews)
      
  }
  
  public changeQuestions(value,event): void {
     console.log("news");
      
      this.cmsquestions = value;
      
       if (value != null && typeof(value)!='undefined' && this.questions.length > 0 )
      	this.showbutt = true;
      else
      	this.showbutt = false;
      
   //   console.log(this.cmsnews)
      
  }
  
  public changeCompanies(value,event): void {
     console.log("news");
      
      this.companies = value;
      
       if (value != null && typeof(value)!='undefined' && this.companies.length > 0 )
      	this.showbutt = true;
      else
      	this.showbutt = false;
      
   //   console.log(this.cmsnews)
      
  }
  
  public checkB2Bdomain(): boolean {
    const currentUrl: string = window.location.pathname;
    const isB2BDomain = currentUrl.startsWith('/b2b');

    // console.log(currentUrl);
    // console.log(isB2BDomain);
    
   //console.log(this.$refs.b2bnavbar);

    return isB2BDomain;
  }
  
  
  
}
