import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';

import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import AdvertisementCategoryService from '../../entities/advertisement-category/advertisement-category.service';
import AdvertisementCategory from '../../entities/advertisement/advertisement.service';
import { IPortalUser } from '@/shared/model/portal-user.model';
import AdvertisementService from '../../entities/advertisement/advertisement.service';
import buildPaginationQueryOpts from '@/shared/sort/sorts';

import SearchPageService from '../../core/b2b-jhi-navbar/searchpage.service';

@Component
export default class B2BJhiNavbar extends Vue {

 
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;

  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  
  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;
  
  @Inject('searchPageService') private searchPageService: () => SearchPageService;

  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValue = false;
  private isPrviNavVisible = true;
  private mainSearchCategory = null;
  public portalUser: IPortalUser = null;
  public companyId: number;
  public companyLink = '';
  private advCategList = null;
  private txtsearchNav;
  private mainSearchCategory = null;
  public advertisements = null;
  public cmsnews = null;
  public cmsquestions = null;
  public companies = null;
  public isActive: boolean = false;
  public valuetype;
  
  public category;
  
  private companies;
  private questions;
  private news;
  

 beforeRouteEnter(to, from, next) {
    to(vm => {
      vm.initRelationships();
    });
  }
  
  created() {
	
	
    this.translationService().refreshTranslation(this.currentLanguage);
    this.mainSearchCategory = 1;
    
    
   
  }
  
  
 
  data() {
     return {
       advertisements:[],
       cmsnews:[],
       cmsquestions:[],
       companies:[],
       txtsearchNav: '',
       mainSearchCategory:1,
       valuetype: [],
       options: [
        {name: 'Оглас', value: '0'},
        {name: 'Вест', value: '1'},
        {name: 'Компанија', value: '2'},
        {name: 'Најчешћа питања', value: '3'},
        ]
      }
    },
  	  
	  beforeMount () {
	  // conosle.log('u beforemount')
	    this.$emit('update', this.advertisements)
	  }
  
  
  
  
  mounted() {
	
  
	this.initRelationships();
    // Call the function to check screen size and update isActive
    this.checkScreenSize();
    // Add event listener to track window resize
    window.addEventListener('resize', this.checkScreenSize);
    
     this.txtsearchNav = this.$route.query.search;
     this.mainSearchCategory =  this.$route.query.category;
     
     //console.log( this.$route.query)
    
    if (this.txtsearchNav == null) {
     const urlParams = new URLSearchParams(window.location.search);
	 this.txtsearchNav = urlParams.get('search');
     this.mainSearchCategory =  urlParams.get('category');
    }
    
     // this.advertisements = "hsdgadjhdgajdhg"
    
    
  }

  beforeDestroy() {
    // Remove event listener when component is destroyed
    window.removeEventListener('resize', this.checkScreenSize);
  }
  
  

  public checkScreenSize() {
    this.isActive = window.innerWidth < 786;
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  
  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    this.$router.push('/');
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    this.getCompanyLink();
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

  public get swaggerEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('swagger') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }

  public getCompanyLink(): string {
    const user = this.$store.getters.account;
    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          if (this.portalUser?.company) {
            this.companyLink = '/company/' + this.portalUser.company.id + '/view';
          }
        });
    }

    return this.companyLink;
  }
  
  private advList;
  
  public searchAdv(): void {
  
  
  
    //this.advertisements = ['kukuriku'];
    
    
    
   // console.log(this.advertisements);
  
	
	if (this.valuetype!= null && typeof(this.valuetype) != 'undefined'  && this.valuetype.length == 1) {
			const searchtype = this.valuetype[0].value;
			
		//	console.log(this.valuetype[0].value);
			
			const baseApiUrlSearchAdv = '/b2b/advertisement-search';
			const baseApiUrlSearchCmp = '/b2b/company-search';
			const baseApiUrlSearchQA = '/b2b/cms-questions/search';
			const baseApiUrlSearchNews = '/b2b/cms-news/search';
			
			var ppathAdv = baseApiUrlSearchAdv + `?search=${this.txtsearchNav}`+ `&category=${this.mainSearchCategory}`;
			var ppathCmp = baseApiUrlSearchCmp + `?search=${this.txtsearchNav}`+ `&category=${this.mainSearchCategory}`;
			var ppathQa = baseApiUrlSearchQA + `?search=${this.txtsearchNav}`+ `&category=${this.mainSearchCategory}`;
			var ppathNw = baseApiUrlSearchNews + `?search=${this.txtsearchNav}`+ `&category=${this.mainSearchCategory}`;
			var ppath = '';
			switch (Number(searchtype)) {
				case 0: 
					ppath = ppathAdv;
					break;
				case 1: 
					ppath = ppathNw;
					break;
				case 2: 
					ppath = ppathCmp;
					break;
				case 3: 
					ppath = ppathQa;
					break;
				default:
					ppath = ppathAdv; 
					break;
					
			}
	
	     window.location.href= ppath;
	 }
	
	
//	console.log(this.valuetype);
	
	
	if (this.valuetype != null && typeof(this.valuetype) != 'undefined'  && this.valuetype.length > 1) {
	
	const num = this.valuetype.length;
	
	for (var i = 0; i <= num - 1; i++) 
	{
		const val = this.valuetype[i].value;
		
		if (val == '0') {
				this.searchPageService()
				 .retrieveSearchAdv(this.txtsearchNav)
			      .then(resa => {
			        //console.log(resa.data);
			        this.advList = resa.data;
			        this.advertisements = resa.data;
			        //this.advertisements.push(resa.data);
			       this.$emit('adv:change', this.advertisements);
			       
			      // console.log(this.advertisements);
			       
			      });
		    } 
		
		 if (val == '2') {  
			     this.searchPageService()
				 .retrieveSearchCmp(this.txtsearchNav)
			      .then(resc => {
			       // console.log(resc.data);
			        this.advList = resc.data;
			        this.companies = resc.data;
			        
			        this.$emit('companies:change', this.companies);
			       
			      });
		 }
		 
		  if (val == '3') {   
			      this.searchPageService()
				 .retrieveSearchQA(this.txtsearchNav)
			      .then(resqa => {
			       // console.log(resqa.data);
			        this.advList = resqa.data;
			        this.questions= resqa.data;
			        this.cmsquestions = resqa.data;
			        console.log(this.cmsquestions);
			        this.$emit('quests:change', this.cmsquestions);
			       
		  		    });
		  }
		  if (val == '1') {    
		     
		     	this.searchPageService()
			 		.retrieveSearchNW(this.txtsearchNav)
		      		.then(resnw => {
		        		//console.log(resnw.data);
		        		this.advList = resnw.data;
		        		this.news = resnw.data;
		        		this.cmsnews = resnw.data;
			        	this.$emit('news:change', this.cmsnews);
		        		
		        		
		     	 });
		 	}
		  
		}
			
			
     
	//console.log(this.txtsearchNav + Number(this.mainSearchCategory) );
  }

  private searchinput;
  
  
  public initRelationships(): void {
    this.advertisementCategoryService()
      .retrieve()
      .then(res => {
	    console.log(res.data);
        this.advCategList = res.data;
        this.$refs.mainSearchCategory = this.advCategList;
          this.mainSearchCategory = 1;
        
      });

   
  }
  
 
  
}
