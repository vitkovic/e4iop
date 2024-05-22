import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ScientificSubdomainService from '../scientific-subdomain/scientific-subdomain.service';
import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';

import AlertService from '@/shared/alert/alert.service';
import { IScientificDomain, ScientificDomain } from '@/shared/model/scientific-domain.model';
import ScientificDomainService from './scientific-domain.service';

const validations: any = {
  scientificDomain: {
    domain: {},
    domainEn: {},
    domainCyr: {},
  },
};

@Component({
  validations,
})
export default class ScientificDomainUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('scientificDomainService') private scientificDomainService: () => ScientificDomainService;
  public scientificDomain: IScientificDomain = new ScientificDomain();

  @Inject('scientificSubdomainService') private scientificSubdomainService: () => ScientificSubdomainService;

  public scientificSubdomains: IScientificSubdomain[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.scientificDomainId) {
        vm.retrieveScientificDomain(to.params.scientificDomainId);
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
    if (this.scientificDomain.id) {
      this.scientificDomainService()
        .update(this.scientificDomain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.scientificDomain.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.scientificDomainService()
        .create(this.scientificDomain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.scientificDomain.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveScientificDomain(scientificDomainId): void {
    this.scientificDomainService()
      .find(scientificDomainId)
      .then(res => {
        this.scientificDomain = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.scientificSubdomainService()
      .retrieve()
      .then(res => {
        this.scientificSubdomains = res.data;
      });
  }
}
