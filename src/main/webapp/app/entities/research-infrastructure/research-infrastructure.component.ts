import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ResearchInfrastructureService from './research-infrastructure.service';
import AccountService from '@/account/account.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ResearchInfrastructure extends mixins(AlertMixin) {
  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;
  @Inject('accountService') private accountService: () => AccountService;

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public currentLanguage = '';
  private hasAnyAuthorityValue = false;

  public researchInfrastructures: IResearchInfrastructure[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllResearchInfrastructures();
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

  public clear(): void {
    this.page = 1;
    this.retrieveAllResearchInfrastructures();
  }

  public retrieveAllResearchInfrastructures(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.researchInfrastructureService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.researchInfrastructures = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IResearchInfrastructure): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeResearchInfrastructure(): void {
    this.researchInfrastructureService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.researchInfrastructure.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllResearchInfrastructures();
        this.closeDialog();
      })
      .catch(error => {
        this.closeDialog();
        const message = this.$t('riportalApp.researchInfrastructure.errors.deleteFailed');
        this.$notify({ text: JSON.stringify(message).replace(/["]/g, ''), type: 'error', duration: 10000 });
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllResearchInfrastructures();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public hasAnyRole(roles: any): boolean {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  getDescriptionByLanguage(researchInfrastructure: IResearchInfrastructure): string {
    switch (this.currentLanguage) {
      case 'sr':
        return researchInfrastructure.descriptionSr;
      case 'en':
        return researchInfrastructure.descriptionEn;
      case 'src':
        return researchInfrastructure.descriptionSrCyr;
      default:
        return researchInfrastructure.descriptionSr;
    }
  }

  public shortenedDescription(index: number): string {
    const infrastructure = this.researchInfrastructures[index];
    const description = this.getDescriptionByLanguage(infrastructure);

    return description.length <= 90 || infrastructure.showFullDescription ? description : description.slice(0, 90) + '...';
  }

  // Method to toggle the showFullDescription flag
  public toggleDescription(index: number): void {
    this.$set(this.researchInfrastructures, index, {
      ...this.researchInfrastructures[index],
      showFullDescription: !this.researchInfrastructures[index].showFullDescription,
    });
  }
}
