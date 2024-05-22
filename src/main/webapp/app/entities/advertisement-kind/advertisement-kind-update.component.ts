import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AdvertisementService from '../advertisement/advertisement.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdvertisementKind, AdvertisementKind } from '@/shared/model/advertisement-kind.model';
import AdvertisementKindService from './advertisement-kind.service';

const validations: any = {
  advertisementKind: {
    kind: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class AdvertisementKindUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;
  public advertisementKind: IAdvertisementKind = new AdvertisementKind();

  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  public advertisements: IAdvertisement[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.advertisementKindId) {
        vm.retrieveAdvertisementKind(to.params.advertisementKindId);
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
    if (this.advertisementKind.id) {
      this.advertisementKindService()
        .update(this.advertisementKind)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementKind.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.advertisementKindService()
        .create(this.advertisementKind)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.advertisementKind.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdvertisementKind(advertisementKindId): void {
    this.advertisementKindService()
      .find(advertisementKindId)
      .then(res => {
        this.advertisementKind = res;
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
  }
}
