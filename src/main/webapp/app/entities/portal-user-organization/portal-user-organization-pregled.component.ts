import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPortalUserOrganization } from '@/shared/model/portal-user-organization.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PortalUserOrganizationService from './portal-user-organization.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PortalUserOrganizationPregled extends mixins(AlertMixin) {
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public portalUserOrganizations: IPortalUserOrganization[] = [];

  public isFetching = false;
  public currentLanguage = '';

  public mounted(): void {
    this.retrieveAllPortalUserOrganizations();
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
    this.retrieveAllPortalUserOrganizations();
  }

  public retrieveAllPortalUserOrganizations(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.portalUserOrganizationService()
      .retrievePregled(paginationQuery)
      .then(
        res => {
          this.portalUserOrganizations = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPortalUserOrganization): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePortalUserOrganization(): void {
    this.portalUserOrganizationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.portalUserOrganization.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPortalUserOrganizations();
        this.closeDialog();
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
    this.retrieveAllPortalUserOrganizations();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
