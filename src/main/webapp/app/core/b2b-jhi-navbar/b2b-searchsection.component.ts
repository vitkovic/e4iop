import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';

import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';

import SearchPageService from '../../core/b2b-jhi-navbar/searchpage.service';


const advProps= Vue.extend({
  props: {
    advertisements: null,
    cmsnews:null,
    cmsquestions: null,
    companies:null
  }
})




@Component
export default class SearchSection extends advProps {

 @Inject('translationService') private translationService: () => TranslationService;

 private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;


      // public advertisements=[];
      	
     data() {
    return {
      toggle:true,
      dissbutton:'disabled'
    }
  },
       
  	created() {
	
	
    this.translationService().refreshTranslation(this.currentLanguage);
    this.mainSearchCategory = 1;
    
    
   
  }
   public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }
        
 }
 
  
 

