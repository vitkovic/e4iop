import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import CmsPageTypeService from '../cms-page-type/cms-page-type.service';
import { ICmsPageType } from '@/shared/model/cms-page-type.model';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { ICmsPage, CmsPage } from '@/shared/model/cms-page.model';
import CmsPageService from './cms-page.service';

const validations: any = {
  cmsPage: {
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
export default class CmsPageUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsPageService') private cmsPageService: () => CmsPageService;
  public cmsPage: ICmsPage = new CmsPage();

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];

  @Inject('cmsPageTypeService') private cmsPageTypeService: () => CmsPageTypeService;

  public cmsPageTypes: ICmsPageType[] = [];

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsPageId) {
        vm.retrieveCmsPage(to.params.cmsPageId);
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
    this.cmsPage.documents = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.cmsPage.id) {
      this.cmsPageService()
        .update(this.cmsPage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsPage.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsPageService()
        .create(this.cmsPage)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsPage.created', { param: param.id });
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
      this.cmsPage[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsPage[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsPage[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsPage[field] = null;
    }
  }

  public retrieveCmsPage(cmsPageId): void {
    this.cmsPageService()
      .find(cmsPageId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsPage = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
    this.cmsPageTypeService()
      .retrieve()
      .then(res => {
        this.cmsPageTypes = res.data;
      });
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
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
