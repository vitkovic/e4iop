import Component from 'vue-class-component';
// import Home from './home.vue'
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';

import { ICmsSlider } from '@/shared/model/cms-slider.model';
import { IDocument } from '@/shared/model/document.model';
import { DocumentTypeOptions } from '@/shared/model/document-type.model';

import CmsSliderService from '../cms-slider/cms-slider.service';
import DocumentService from '../document/document.service';

@Component
export default class B2BHome extends Vue {

   props: ['advs','news']




  @Inject('cmsSliderService') private cmsSliderService: () => CmsSliderService;
  @Inject('documentService') private documentService: () => DocumentService;

  public cmsSliders: ICmsSlider[] = [];

  private advertisements;
  data() {
    return {
      value: [],
      options: [
        { name: 'Vue.js', language: 'JavaScript' },
        { name: 'Adonis', language: 'JavaScript' },
        { name: 'Rails', language: 'Ruby' },
        { name: 'Sinatra', language: 'Ruby' },
        { name: 'Laravel', language: 'PHP' },
        { name: 'Phoenix', language: 'Elixir' },
      ],
    };
  }

  @Inject('loginService')
  private loginService: () => LoginService;

  public imagesArray = [
    {
      src: '/content/images/test2-520.jpg',
      srcSmall: '/content/images/test2-720.jpg',
      link: 'https://einovacije.rs/',
    },
    {
      src: '/content/images/test5-520.jpg',
      srcSmall: '/content/images/test5-720.jpg',

      link: '/b2b/questions',
    },
    {
      src: '/content/images/test7-520.jpg',
      srcSmall: '/content/images/test7-720.jpg',

      link: 'https://einovacije.rs/',
    },
    {
      src: '/content/images/test10-520.jpg',
      srcSmall: '/content/images/test10-720.jpg',

      link: '/b2b/questions',
    },
  ];

  // -------- LOGIC FOR BANNER RESPONSIVE AND WHICH IMAGE SIZE TO SHOW -----------

  public slide = 0;

  getImageSrc(slideIndex: number) {
    const image = this.imagesArray[slideIndex];
    return window.innerWidth <= 640 ? image.srcSmall : image.src;
  }

  created() {
    this.cmsSliderService()
      .retrieveAll()
      .then(res => {
        this.cmsSliders = res;
        console.log(this.cmsSliders);
      });
      
   //   alert(this.$t('riportalApp.researchInfrastructure.errors.deleteFailed') + this.currentLanguage);
  }

  mounted() {
    window.addEventListener('resize', this.handleResize);
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
  }

  // No need to write any code here, we just need this to call resize
  handleResize() {}

  // -------- LOGIC FOR BANNER RESPONSIVE AND WHICH IMAGE SIZE TO SHOW END-----------

  public handleImageClick(cmsSlider: ICmsSlider) {
    if (cmsSlider.newTab) {
      window.open(cmsSlider.link, '_blank');
    } else {
      window.open(cmsSlider.link, '_self');
    }
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public retrieveFile(file: IDocument): string {
    if (file.type.type === DocumentTypeOptions.IMAGE) {
      return this.documentService().retrieveImage(file.filename);
    } else if (file.type.type === DocumentTypeOptions.DOCUMENT) {
      return this.documentService().retrieveDocument(file.filename);
    } else {
      return '';
    }
  }
}
