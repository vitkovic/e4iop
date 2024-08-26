import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import { IAdvertisementStatus } from '@/shared/model/advertisement-status.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import AdvertisementService from './advertisement.service';
import AdvertisementStatusService from '../advertisement-status/advertisement-status.service';
import AccountService from '@/account/account.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';

enum AdvertisementStatus {
  ACTIVE = 'Активан',
  INACTIVE = 'Неактиван',
  ARCHIVED = 'Архивиран',
}

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
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  public advertisements: IAdvertisement[] = [];
  public advertisementStatuses: IAdvertisementStatus[] = [];
  public activeAdStatus: IAdvertisementStatus = null;

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
  public portalUser: IPortalUser = null;
  public txtsearch = null;
  public category = null;
  public types;
  public typeNameValue: string | null = null;
  public typesearch = false;
  public activeAdStatusFilter = AdvertisementStatusFilter.ALL;
  public filterAllButtonVariant = 'secondary';
  public filterActiveButtonVariant = 'outline-secondary';
  public filterInactiveButtonVariant = 'outline-secondary';
  public filterSoftDeleteButtonVariant = 'outline-secondary';
  public companyId: number = 0;
  public subc : any = 0;

  public getCompany(): any {
    let user = this.$store.getters.account;
    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          console.log(this.portalUser);
          if (this.portalUser?.company) {
            this.companyId = this.portalUser.company.id;
          }
        });
    }
  }

  data() {
    return {
      companyId: 0,
    };
  }

  public mounted(): void {
    const urlParams = new URLSearchParams(window.location.search);

    this.txtsearch = this.$route.query.search;
    this.category = this.$route.query.category;
    this.types = this.$route.query.type;
    this.subc = this.$route.query.subc;

    if (this.txtsearch == null || this.txtsearch === 'undefined') {
      this.txtsearch = urlParams.get('search');
      this.category = urlParams.get('category');
      this.types = urlParams.get('type');
      this.subc = urlParams.get('subc');;
    }

    if (this.types != null && this.types !== 'undefined') {
      this.typesearch = true;
    } else {
      this.typesearch = false;
    }

    console.log(this.subc);

    this.retrieveAllAdvertisements();

    // this.getCompany();

    this.advertisementStatusService()
      .retrieve()
      .then(res => {
        this.advertisementStatuses = res.data;
      });
  }
  public created(): void {
    const urlParams = new URLSearchParams(window.location.search);

    this.txtsearch = this.$route.query.search;
    this.category = this.$route.query.category;
    this.types = this.$route.query.type;

    if (this.txtsearch == null || this.txtsearch === 'undefined') {
      this.txtsearch = urlParams.get('search');
      this.category = urlParams.get('category');
      this.types = urlParams.get('type');
    }

    if (this.types != null) {
      this.typesearch = true;
    } else {
      this.typesearch = false;
    }

    if (this.types === '3451') {
      this.typeNameValue = 'offer';
    } else if (this.types === '3452') {
      this.typeNameValue = 'demand';
    }

    this.retrieveAllAdvertisements();

    //this.getCompany();

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

    if (this.typesearch) {
      if (this.activeAdStatusFilter === AdvertisementStatusFilter.ALL) {
        const activeStatusId = 3551;
        this.advertisementService()
          //  .retrieveSearchType(this.types,paginationQuery)
          .retrieveSearchTypeStatus(this.types, activeStatusId, paginationQuery)
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
          //console.log(this.activeAdStatus);
          this.advertisementService()
            .retrieveSearchTypeStatus(this.types, this.activeAdStatus.id, paginationQuery)
            .then(
              res => {
                this.advertisements = res.data;
                this.totalItems = Number(res.headers['x-total-count']);
                this.queryCount = this.totalItems;
                this.isFetching = false;
                return;
              },
              err => {
                this.isFetching = false;
              }
            );
        }
      }
    } else {
	  if (this.subc == 1) {
		 if (this.activeAdStatusFilter === AdvertisementStatusFilter.ALL) {
	        this.advertisementService()
	          .retrieveSearchSub(this.txtsearch, this.category, paginationQuery)
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
	            .retrieveSearchByStatusIdSub(this.txtsearch, this.activeAdStatus.id, this.category, paginationQuery)
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
	  } else {
		  /* po osnovnoj kategoriji
      if (this.activeAdStatusFilter === AdvertisementStatusFilter.ALL) {
		
        this.advertisementService()
          .retrieveSearch(this.txtsearch, this.category, paginationQuery)
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
            .retrieveSearchByStatusId(this.txtsearch, this.activeAdStatus.id, this.category, paginationQuery)
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
      */
     }
    }
  }

  public get authenticated(): boolean {
    this.getCompany();
    return this.$store.getters.authenticated;
  }

  public get account(): boolean {
    return this.$store.getters.account;
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
    this.newAdvertisementStatus = this.advertisementStatuses.filter(status => status.status === 'Неактиван')[0];

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
    this.newAdvertisementStatus = this.advertisementStatuses.filter(status => status.status === 'Активан')[0];

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
    this.newAdvertisementStatus = this.advertisementStatuses.filter(status => status.status === 'Архивиран')[0];

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
    //console.log("Active");
    this.activeAdStatusFilter = AdvertisementStatusFilter.ACTIVE;
    this.activeAdStatus = this.advertisementStatuses.find(status => status.status === AdvertisementStatus.ACTIVE);

    this.activeAdStatus = {};
    this.activeAdStatus.id = 3551;

    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterActiveButtonVariant = 'secondary';
    this.filterInactiveButtonVariant = 'outline-secondary';
    this.filterSoftDeleteButtonVariant = 'outline-secondary';
  }

  public showInactiveAdvertisements(): void {
    this.activeAdStatusFilter = AdvertisementStatusFilter.INACTIVE;
    this.activeAdStatus = this.advertisementStatuses.find(status => status.status === AdvertisementStatus.INACTIVE);

    this.activeAdStatus = {};
    this.activeAdStatus.id = 3552;

    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterActiveButtonVariant = 'outline-secondary';
    this.filterInactiveButtonVariant = 'secondary';
    this.filterSoftDeleteButtonVariant = 'outline-secondary';
  }

  public showSoftDeleteAdvertisements(): void {
    this.activeAdStatusFilter = AdvertisementStatusFilter.ARCHIVED;
    this.activeAdStatus = this.advertisementStatuses.find(status => status.status === AdvertisementStatus.ARCHIVED);

    this.activeAdStatus = {};
    this.activeAdStatus.id = 3553;

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

  get typeName(): string | null {
    return this.typeNameValue;
  }
}
