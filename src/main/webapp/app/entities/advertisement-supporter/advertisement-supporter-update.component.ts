import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import CompanyService from '../company/company.service';
import { ICompany } from '@/shared/model/company.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementSupporter, AdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import AdvertisementSupporterService from './advertisement-supporter.service';

const validations: any = {
  advertisementSupporter: {
    hasAccepted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementSupporterUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;
  public advertisementSupporter: IAdvertisementSupporter = new AdvertisementSupporter();

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];

  @Inject('companyService') private companyService: () => CompanyService;

  public companies: ICompany[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementSupporterId) {
        vm.retrieveAdvertisementSupporter(to.params.advertisementSupporterId);
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
    if (this.advertisementSupporter.id) {
      this.advertisementSupporterService()
        .update(this.advertisementSupporter)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSupporter.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementSupporterService()
        .create(this.advertisementSupporter)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementSupporter.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementSupporter(advertisementSupporterId): void {
    this.advertisementSupporterService()
      .find(advertisementSupporterId)
      .then(res => {
        this.advertisementSupporter = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.advertisementService()
      .retrieve()
      .then(res => {
        this.advertisements = res.data;
      });
    this.companyService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
  }
}
