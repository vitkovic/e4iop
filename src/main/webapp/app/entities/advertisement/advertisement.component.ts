import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import { IAdvertisementStatus, AdvertisementStatusOptions } from '@/shared/model/advertisement-status.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import AdvertisementService from './advertisement.service';
import AdvertisementStatusService from '../advertisement-status/advertisement-status.service';
import AccountService from '@/account/account.service';

enum AdvertisementStatusFilter {
  ALL = 'all',
  ACTIVE = 'active',
  INACTIVE = 'inactive',
  ARCHIVED = 'archived',
}

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Advertisement extends mixins(AlertMixin) {
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  @Inject('advertisementStatusService') private advertisementStatusService: () => AdvertisementStatusService;
  @Inject('accountService') private accountService: () => AccountService;

  public advertisements: IAdvertisement[] = [];
  public advertisementStatuses: IAdvertisementStatus[] = [];
  public activeAdStatus: IAdvertisementStatus | null = null;
  public advertisementStatusOptions = AdvertisementStatusOptions;

  private removeId: number = null;
  private advertisementToSwitchStatus: IAdvertisement = null;
  private newAdvertisementStatus: IAdvertisementStatus = null;
  private hasAnyAuthorityValue = false;

  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public isFetching = false;

  public activeAdStatusFilter = AdvertisementStatusFilter.ALL;
  public filterAllButtonVariant = 'secondary';
  public filterActiveButtonVariant = 'outline-secondary';
  public filterInactiveButtonVariant = 'outline-secondary';
  public filterSoftDeleteButtonVariant = 'outline-secondary';

  public mounted(): void {
    this.retrieveAllAdvertisements();
    this.advertisementStatusService()
      .retrieve()
      .then(res => {
        this.advertisementStatuses = res.data;
      });
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllAdvertisements();
  }

  public retrieveAllAdvertisements(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    if (this.activeAdStatusFilter === AdvertisementStatusFilter.ALL) {
      this.advertisementService()
        .retrieve(paginationQuery)
        .then(
          res => {
            this.advertisements = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    } else {
      if (this.activeAdStatus) {
        this.advertisementService()
          .retrieveAllByStatusId(this.activeAdStatus.id, paginationQuery)
          .then(
            res => {
              this.advertisements = res.data;
              this.totalItems = Number(res.headers['x-total-count']);
              this.queryCount = this.totalItems;
              this.isFetching = false;
            },
            err => {
              this.isFetching = false;
            }
          );
      }
    }
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

  public prepareRemove(instance: IAdvertisement): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdvertisement(): void {
    this.advertisementService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.advertisement.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdvertisements();
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
    this.retrieveAllAdvertisements();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public getExpirationDate(advertisement: IAdvertisement): Date {
    const expirationDate = new Date(advertisement.activationDatetime);
    expirationDate.setMonth(expirationDate.getMonth() + Number(advertisement.duration.duration));

    return expirationDate;
  }

  public prepareDeactivate(instance: IAdvertisement): void {
    this.advertisementToSwitchStatus = instance;
    this.newAdvertisementStatus = this.advertisementStatuses.find(status => status.statusEn === AdvertisementStatusOptions.INACTIVE);

    if (<any>this.$refs.deactivateEntity) {
      (<any>this.$refs.deactivateEntity).show();
    }
  }

  public closeDeactivateDialog(): void {
    (<any>this.$refs.deactivateEntity).hide();
  }

  public deactivateAdvertisement(): void {
    this.advertisementService()
      .updateStatus(this.advertisementToSwitchStatus.id, this.newAdvertisementStatus.id)
      .then(() => {
        const message = this.$t('riportalApp.advertisement.notifications.advertisementDeactivation');
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.retrieveAllAdvertisements();
        this.closeDeactivateDialog();
      });
  }

  public prepareActivate(instance: IAdvertisement): void {
    this.advertisementToSwitchStatus = instance;
    this.newAdvertisementStatus = this.advertisementStatuses.find(status => status.statusEn === AdvertisementStatusOptions.ACTIVE);

    if (<any>this.$refs.activateEntity) {
      (<any>this.$refs.activateEntity).show();
    }
  }

  public closeActivateDialog(): void {
    (<any>this.$refs.activateEntity).hide();
  }

  public activateAdvertisement(): void {
    this.advertisementService()
      .updateStatus(this.advertisementToSwitchStatus.id, this.newAdvertisementStatus.id)
      .then(() => {
        const message = this.$t('riportalApp.advertisement.notifications.advertisementActivation');
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.$router.push({ name: 'AdvertisementEdit', params: { advertisementId: this.advertisementToSwitchStatus.id } });
      });

    this.closeActivateDialog();
  }

  public prepareSoftDelete(instance: IAdvertisement): void {
    this.advertisementToSwitchStatus = instance;
    this.newAdvertisementStatus = this.advertisementStatuses.find(status => status.statusEn === AdvertisementStatusOptions.ARCHIVED);

    this.removeId = instance.id;
    if (<any>this.$refs.softDeleteEntity) {
      (<any>this.$refs.softDeleteEntity).show();
    }
  }

  public closeSoftDeleteDialog(): void {
    (<any>this.$refs.softDeleteEntity).hide();
  }

  public softDeleteAdvertisement(): void {
    this.advertisementService()
      .updateStatus(this.advertisementToSwitchStatus.id, this.newAdvertisementStatus.id)
      .then(() => {
        const message = this.$t('riportalApp.advertisement.notifications.advertisementDeleted');
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.retrieveAllAdvertisements();
        this.closeSoftDeleteDialog();
      });
  }

  public showAllAdvertisements(): void {
    this.activeAdStatusFilter = AdvertisementStatusFilter.ALL;
    this.activeAdStatus = null;
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'secondary';
    this.filterActiveButtonVariant = 'outline-secondary';
    this.filterInactiveButtonVariant = 'outline-secondary';
  }

  public showActiveAdvertisements(): void {
    this.activeAdStatusFilter = AdvertisementStatusFilter.ACTIVE;
    this.activeAdStatus = this.advertisementStatuses.find(status => status.statusEn === AdvertisementStatusOptions.ACTIVE);
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterActiveButtonVariant = 'secondary';
    this.filterInactiveButtonVariant = 'outline-secondary';
    this.filterSoftDeleteButtonVariant = 'outline-secondary';
  }

  public showInactiveAdvertisements(): void {
    this.activeAdStatusFilter = AdvertisementStatusFilter.INACTIVE;
    this.activeAdStatus = this.advertisementStatuses.find(status => status.statusEn === AdvertisementStatusOptions.INACTIVE);
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterActiveButtonVariant = 'outline-secondary';
    this.filterInactiveButtonVariant = 'secondary';
    this.filterSoftDeleteButtonVariant = 'outline-secondary';
  }

  public showSoftDeleteAdvertisements(): void {
    this.activeAdStatusFilter = AdvertisementStatusFilter.ARCHIVED;
    this.activeAdStatus = this.advertisementStatuses.find(status => status.statusEn === AdvertisementStatusOptions.ARCHIVED);
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterActiveButtonVariant = 'outline-secondary';
    this.filterInactiveButtonVariant = 'outline-secondary';
    this.filterSoftDeleteButtonVariant = 'secondary';
  }

  public advertisementCategorizationBranch(advertisement: IAdvertisement): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementCategorizationBranch(advertisement, currentLanguage);
  }

  public advertisementKindsString(advertisement: IAdvertisement): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementKindsString(advertisement, currentLanguage);
  }
}
