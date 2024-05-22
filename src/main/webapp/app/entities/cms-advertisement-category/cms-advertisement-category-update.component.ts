import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AdvertisementCategoryService from '../advertisement-category/advertisement-category.service';
import { IAdvertisementCategory } from '@/shared/model/advertisement-category.model';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import AlertService from '@/shared/alert/alert.service';
import { ICmsAdvertisementCategory, CmsAdvertisementCategory } from '@/shared/model/cms-advertisement-category.model';
import CmsAdvertisementCategoryService from './cms-advertisement-category.service';

const validations: any = {
  cmsAdvertisementCategory: {
    createdAt: {},
    modifiedAt: {},
  },
};

@Component({
  validations,
})
export default class CmsAdvertisementCategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsAdvertisementCategoryService') private cmsAdvertisementCategoryService: () => CmsAdvertisementCategoryService;
  public cmsAdvertisementCategory: ICmsAdvertisementCategory = new CmsAdvertisementCategory();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];

  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;

  public advertisementCategories: IAdvertisementCategory[] = [];

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsAdvertisementCategoryId) {
        vm.retrieveCmsAdvertisementCategory(to.params.cmsAdvertisementCategoryId);
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
    if (this.cmsAdvertisementCategory.id) {
      this.cmsAdvertisementCategoryService()
        .update(this.cmsAdvertisementCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsAdvertisementCategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsAdvertisementCategoryService()
        .create(this.cmsAdvertisementCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsAdvertisementCategory.created', { param: param.id });
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
      this.cmsAdvertisementCategory[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsAdvertisementCategory[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsAdvertisementCategory[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsAdvertisementCategory[field] = null;
    }
  }

  public retrieveCmsAdvertisementCategory(cmsAdvertisementCategoryId): void {
    this.cmsAdvertisementCategoryService()
      .find(cmsAdvertisementCategoryId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsAdvertisementCategory = res;
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
    this.advertisementCategoryService()
      .retrieve()
      .then(res => {
        this.advertisementCategories = res.data;
      });
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
  }
}
