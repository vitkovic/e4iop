import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';

import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';

import AdvertisementSupporterService from '@/entities/advertisement-supporter/advertisement-supporter.service';

@Component
export default class AdvertisementSupporterAccept extends Vue {
  @Inject('advertisementSupporterService')
  private advertisementSupporterService: () => AdvertisementSupporterService;

  public advertisementSupporter: IAdvertisementSupporter | null = null;
  public advertisementId: number | null = null;
  public companyId: number | null = null;
  public message = '';

  success = false;
  error = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.query.advertisementId && to.query.companyId) {
        vm.advertisementId = to.query.advertisementId;
        vm.companyId = to.query.companyId;

        vm.success = true;
        vm.acceptAdvertisementSupporter();
      }
    });
  }

  public acceptAdvertisementSupporter(): void {
    this.advertisementSupporterService()
      .acceptForCompany(this.advertisementId, this.companyId)
      .then(res => {
        this.success = true;

        this.advertisementSupporter = res;
        this.message = 'Uspešno ste prihvatili poziv za pridruženo oglašavanje';
      })
      .catch(error => {
        this.error = true;

        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            this.message = 'Sistemska greška, nemate poziv za pridruženo oglašavanje';
          } else if (status === 400) {
            this.advertisementSupporter = error.response.data;
            this.message = 'Poziv za pridruženo oglašavanje je već prihvaćen';
          } else {
            this.message = error.response.data;
          }
        } else {
          console.error('Greska!', error.message);
        }
      });
  }

  public init(key: string): void {}
}
