import { Component, Vue, Inject } from 'vue-property-decorator';
import { required } from 'vuelidate/lib/validators';
import { IRiFinanceSource, RiFinanceSource } from '@/shared/model/ri-finance-source.model';
import AlertService from '@/shared/alert/alert.service';
import RiFinanceSourceService from './ri-finance-source.service';

const validations: any = {
  riFinanceSource: {
    sourceSr: { required },
    sourceSrc: { required },
    sourceEn: { required },
  },
};

@Component({
  validations,
})
export default class RiFinanceSourceUpdate extends Vue {
  @Inject('alertService') private alertService!: () => AlertService;
  @Inject('riFinanceSourceService') private riFinanceSourceService!: () => RiFinanceSourceService;

  public riFinanceSource: IRiFinanceSource = new RiFinanceSource();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riFinanceSourceId) {
        vm.retrieveRiFinanceSource(to.params.riFinanceSourceId);
      }
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
    if (this.riFinanceSource.id) {
      this.riFinanceSourceService()
        .update(this.riFinanceSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riFinanceSource.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riFinanceSourceService()
        .create(this.riFinanceSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riFinanceSource.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRiFinanceSource(riFinanceSourceId): void {
    this.riFinanceSourceService()
      .find(riFinanceSourceId)
      .then(res => {
        this.riFinanceSource = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
