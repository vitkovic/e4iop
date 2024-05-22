import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import BadgeService from '../badge/badge.service';
import { IBadge } from '@/shared/model/badge.model';

import CmsSliderService from '../cms-slider/cms-slider.service';
import { ICmsSlider } from '@/shared/model/cms-slider.model';

import CmsCarouselService from '../cms-carousel/cms-carousel.service';
import { ICmsCarousel } from '@/shared/model/cms-carousel.model';

import CmsAdvertisementCategoryService from '../cms-advertisement-category/cms-advertisement-category.service';
import { ICmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';

import CmsTestimonialsService from '../cms-testimonials/cms-testimonials.service';
import { ICmsTestimonials } from '@/shared/model/cms-testimonials.model';

import DocumentTypeService from '../document-type/document-type.service';
import { IDocumentType } from '@/shared/model/document-type.model';

import DocumentKindService from '../document-kind/document-kind.service';
import { IDocumentKind } from '@/shared/model/document-kind.model';

import CmsPageService from '../cms-page/cms-page.service';
import { ICmsPage } from '@/shared/model/cms-page.model';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocument, Document } from '@/shared/model/document.model';
import DocumentService from './document.service';

const validations: any = {
  document: {
    filename: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class DocumentUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentService') private documentService: () => DocumentService;
  public document: IDocument = new Document();

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];

  @Inject('badgeService') private badgeService: () => BadgeService;

  public badges: IBadge[] = [];

  @Inject('cmsSliderService') private cmsSliderService: () => CmsSliderService;

  public cmsSliders: ICmsSlider[] = [];

  @Inject('cmsCarouselService') private cmsCarouselService: () => CmsCarouselService;

  public cmsCarousels: ICmsCarousel[] = [];

  @Inject('cmsAdvertisementCategoryService') private cmsAdvertisementCategoryService: () => CmsAdvertisementCategoryService;

  public cmsAdvertisementCategories: ICmsAdvertisementCategory[] = [];

  @Inject('cmsTestimonialsService') private cmsTestimonialsService: () => CmsTestimonialsService;

  public cmsTestimonials: ICmsTestimonials[] = [];

  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;

  public documentTypes: IDocumentType[] = [];

  @Inject('documentKindService') private documentKindService: () => DocumentKindService;

  public documentKinds: IDocumentKind[] = [];

  @Inject('cmsPageService') private cmsPageService: () => CmsPageService;

  public cmsPages: ICmsPage[] = [];

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentId) {
        vm.retrieveDocument(to.params.documentId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.document.id) {
      this.documentService()
        .update(this.document)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.document.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentService()
        .create(this.document)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.document.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocument(documentId): void {
    this.documentService()
      .find(documentId)
      .then(res => {
        this.document = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.badgeService()
      .retrieve()
      .then(res => {
        this.badges = res.data;
      });
    this.cmsSliderService()
      .retrieve()
      .then(res => {
        this.cmsSliders = res.data;
      });
    this.cmsCarouselService()
      .retrieve()
      .then(res => {
        this.cmsCarousels = res.data;
      });
    this.cmsAdvertisementCategoryService()
      .retrieve()
      .then(res => {
        this.cmsAdvertisementCategories = res.data;
      });
    this.cmsTestimonialsService()
      .retrieve()
      .then(res => {
        this.cmsTestimonials = res.data;
      });
    this.documentTypeService()
      .retrieve()
      .then(res => {
        this.documentTypes = res.data;
      });
    this.documentKindService()
      .retrieve()
      .then(res => {
        this.documentKinds = res.data;
      });
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.cmsPageService()
      .retrieve()
      .then(res => {
        this.cmsPages = res.data;
      });
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
  }
}
