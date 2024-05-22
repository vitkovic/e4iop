import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentService from '../document/document.service';
import { IDocument } from '@/shared/model/document.model';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import AlertService from '@/shared/alert/alert.service';
import { IBadge, Badge } from '@/shared/model/badge.model';
import BadgeService from './badge.service';

const validations: any = {
  badge: {
    title: {
      required,
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class BadgeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('badgeService') private badgeService: () => BadgeService;
  public badge: IBadge = new Badge();

  @Inject('documentService') private documentService: () => DocumentService;

  public documents: IDocument[] = [];

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.badgeId) {
        vm.retrieveBadge(to.params.badgeId);
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
    if (this.badge.id) {
      this.badgeService()
        .update(this.badge)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.badge.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.badgeService()
        .create(this.badge)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.badge.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveBadge(badgeId): void {
    this.badgeService()
      .find(badgeId)
      .then(res => {
        this.badge = res;
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
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
  }
}
