import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import { required, email } from 'vuelidate/lib/validators';

import { ICompany } from '@/shared/model/company.model';

import AccountService from '@/account/account.service';
import CompanyService from '@/entities/company.service';
import PortalUserService from '@/entities/portal-user.service';
import UserManagementService from '@/admin/user-management.service';

import { Authority } from '../../shared/security/authority';

const validations: any = {
  emailAddress: { required, email },
};

@Component({
  mixins: [Vue2Filters.mixin],
  validations,
})
export default class CompanyPortalUsers extends mixins(AlertMixin) {
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('userService') private userManagementService: () => UserManagementService;

  private hasAnyAuthorityValue = false;

  public company: ICompany | null = null;
  public companyId: number;
  public users: any[] = [];
  public userToDelete: any | null = null;
  public emailAddress: string | null = null;

  public authority = Authority;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.companyId = to.params.companyId;

        vm.retrieveCompany();
        vm.retrieveAllCompanyUsers();
      }
    });
  }

  public retrieveCompany() {
    this.companyService()
      .find(this.companyId)
      .then(res => {
        this.company = res;
      });
  }

  public retrieveAllCompanyUsers() {
    this.userManagementService()
      .findAllForCompany(this.companyId)
      .then(res => {
        this.users = res;
      });
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public prepareAddUserModal(): void {
    this.emailAddress = null;
    if (<any>this.$refs.addUserModal) {
      (<any>this.$refs.addUserModal).show();
    }
  }

  public addUser(): void {
    let formData = new FormData();
    formData.append('companyId', '' + this.companyId);
    formData.append('email', '' + this.emailAddress);

    this.accountService()
      .inviteB2BUser(formData)
      .then(res => {
        const message = 'Aktivacioni mail je poslat na adresu ' + this.emailAddress;
        this.$notify({
          text: message,
        });
      });

    this.closeAddUserModal();
  }

  public closeAddUserModal(): void {
    (<any>this.$refs.addUserModal).hide();
  }

  public prepareDeleteUserModal(user): void {
    this.userToDelete = user;
    if (<any>this.$refs.deleteUserModal) {
      (<any>this.$refs.deleteUserModal).show();
    }
  }

  public deleteUser(): void {
    this.portalUserService()
      .removeCompanyUser(this.userToDelete.id)
      .then(res => {
        this.retrieveAllCompanyUsers();

        const message = 'Korisnik "' + ''.concat(this.userToDelete.firstName, ' ', this.userToDelete.lastName) + '" je obrisan.';
        this.$notify({
          text: message,
        });
      });
    this.closeDeleteUserModal();
  }

  public closeDeleteUserModal(): void {
    (<any>this.$refs.deleteUserModal).hide();
  }

  public authorityLanguage(authority: string): string {
    if (authority != this.authority.COMPANY_ADMIN && authority != this.authority.COMPANY_USER) {
      return '';
    }

    return this.$t('authority.company.' + authority);
  }

  public isAdmin(user): boolean {
    if (user.authorities.includes(Authority.ADMIN)) {
      return true;
    }

    if (user.authorities.includes(Authority.COMPANY_ADMIN)) {
      return true;
    }

    return false;
  }
}
