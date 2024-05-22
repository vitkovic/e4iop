import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import AlertService from '@/shared/alert/alert.service';
import { ICmsCarousel, CmsCarousel } from '@/shared/model/cms-carousel.model';
import CmsCarouselService from './cms-carousel.service';

const validations: any = {
  cmsCarousel: {
    createdAt: {},
    modifiedAt: {},
    title: {
      required,
    },
    link: {
      required,
    },
    newTab: {},
    ordinalNumber: {},
  },
};

@Component({
  validations,
})
export default class CmsCarouselUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsCarouselService') private cmsCarouselService: () => CmsCarouselService;
  public cmsCarousel: ICmsCarousel = new CmsCarousel();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsCarouselId) {
        vm.retrieveCmsCarousel(to.params.cmsCarouselId);
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
    if (this.cmsCarousel.id) {
      this.cmsCarouselService()
        .update(this.cmsCarousel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsCarousel.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsCarouselService()
        .create(this.cmsCarousel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsCarousel.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.cmsCarousel[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsCarousel[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsCarousel[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsCarousel[field] = null;
    }
  }

  public retrieveCmsCarousel(cmsCarouselId): void {
    this.cmsCarouselService()
      .find(cmsCarouselId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsCarousel = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
  }
}
