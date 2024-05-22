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
import { ICmsTestimonials, CmsTestimonials } from '@/shared/model/cms-testimonials.model';
import CmsTestimonialsService from './cms-testimonials.service';

const validations: any = {
  cmsTestimonials: {
    createdAt: {},
    modifiedAt: {},
    title: {
      required,
    },
    content: {},
  },
};

@Component({
  validations,
})
export default class CmsTestimonialsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsTestimonialsService') private cmsTestimonialsService: () => CmsTestimonialsService;
  public cmsTestimonials: ICmsTestimonials = new CmsTestimonials();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsTestimonialsId) {
        vm.retrieveCmsTestimonials(to.params.cmsTestimonialsId);
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
    if (this.cmsTestimonials.id) {
      this.cmsTestimonialsService()
        .update(this.cmsTestimonials)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsTestimonials.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsTestimonialsService()
        .create(this.cmsTestimonials)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsTestimonials.created', { param: param.id });
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
      this.cmsTestimonials[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsTestimonials[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsTestimonials[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsTestimonials[field] = null;
    }
  }

  public retrieveCmsTestimonials(cmsTestimonialsId): void {
    this.cmsTestimonialsService()
      .find(cmsTestimonialsId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsTestimonials = res;
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
