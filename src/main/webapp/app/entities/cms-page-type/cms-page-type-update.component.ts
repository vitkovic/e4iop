import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CmsPageService from '../cms-page/cms-page.service';
import { ICmsPage } from '@/shared/model/cms-page.model';

import AlertService from '@/shared/alert/alert.service';
import { ICmsPageType, CmsPageType } from '@/shared/model/cms-page-type.model';
import CmsPageTypeService from './cms-page-type.service';

const validations: any = {
  cmsPageType: {
    type: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class CmsPageTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsPageTypeService') private cmsPageTypeService: () => CmsPageTypeService;
  public cmsPageType: ICmsPageType = new CmsPageType();

  @Inject('cmsPageService') private cmsPageService: () => CmsPageService;

  public cmsPages: ICmsPage[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsPageTypeId) {
        vm.retrieveCmsPageType(to.params.cmsPageTypeId);
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
    if (this.cmsPageType.id) {
      this.cmsPageTypeService()
        .update(this.cmsPageType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsPageType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsPageTypeService()
        .create(this.cmsPageType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsPageType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCmsPageType(cmsPageTypeId): void {
    this.cmsPageTypeService()
      .find(cmsPageTypeId)
      .then(res => {
        this.cmsPageType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cmsPageService()
      .retrieve()
      .then(res => {
        this.cmsPages = res.data;
      });
  }
}
