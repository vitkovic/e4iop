import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPortalUser } from '@/shared/model/portal-user.model';
import { ICompany } from '@/shared/model/company.model';

import PortalUserService from './portal-user.service';

@Component
export default class ConfirmAccountData extends Vue {
  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUser: IPortalUser | null = null;
  public company: ICompany | null = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.retrieveAccountData();
    });
  }

  public retrieveAccountData(): void {
    const user = this.$store.getters.account;

    if (!user) {
      return;
    }

    this.portalUserService()
      .findByUserId(user.id)
      .then(res => {
        this.portalUser = res;
        if (this.portalUser?.company) {
          this.company = this.portalUser?.company;
          console.log(this.company);
        }
      });
  }
}
