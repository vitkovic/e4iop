import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdvertisement } from '@/shared/model/advertisement.model';
import { AdvertisementStatusOptions } from '@/shared/model/advertisement-status.model';
import { IAdvertisementType, AdvertisementTypeOptions } from '@/shared/model/advertisement-type.model';
import { ICompany } from '@/shared/model/company.model';

import AdvertisementService from './advertisement.service';
import AdvertisementTypeService from '../advertisement-status/advertisement-type.service';
import AccountService from '@/account/account.service';
import CompanyService from '@/entities/company.service';

enum AdvertisementTypeFilter {
  ALL = 'all',
  OFFER = 'offer',
  DEMAND = 'demand',
}

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Advertisement extends mixins(AlertMixin) {
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('companyService') private companyService: () => CompanyService;

  private hasAnyAuthorityValue = false;

  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public advertisements: IAdvertisement[] = [];
  public advertisementTypes: IAdvertisementType[] = [];
  public activeAdType: IAdvertisementType | null = null;
  public company: ICompany | null = null;

  private companyId: number;

  public isFetching = false;
  public activeAdTypeFilter = AdvertisementTypeFilter.ALL;
  public filterAllButtonVariant = 'secondary';
  public filterOfferButtonVariant = 'outline-secondary';
  public filterDemandButtonVariant = 'outline-secondary';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.companyId = to.params.companyId;
        vm.companyService()
          .find(vm.companyId)
          .then(res => {
            vm.company = res;
            // vm.retrieveAllAdvertisements();
            if (to.query.filter === 'offer') {
              vm.showOfferAdvertisements();
            } else if (to.query.filter === 'demand') {
              vm.showDemandAdvertisements();
            } else {
              vm.retrieveAllAdvertisements();
            }
          });
      }
    });
  }

  public mounted(): void {
    // this.retrieveAllAdvertisements();
    this.advertisementTypeService()
      .retrieve()
      .then(res => {
        this.advertisementTypes = res.data;
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

    if (this.activeAdTypeFilter === AdvertisementTypeFilter.ALL) {
      this.advertisementService()
        .retrieveForCompany(this.companyId, AdvertisementStatusOptions.ACTIVE, paginationQuery)
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
      if (this.activeAdType) {
        this.advertisementService()
          .findAllForCompanyByStatusAndType(this.companyId, AdvertisementStatusOptions.ACTIVE, this.activeAdType.typeEn, paginationQuery)
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

  public getExpirationDate(advertisement: IAdvertisement): Date {
    const expirationDate = new Date(advertisement.activationDatetime);
    expirationDate.setMonth(expirationDate.getMonth() + Number(advertisement.duration.duration));

    return expirationDate;
  }

  public showAllAdvertisements(): void {
    this.activeAdTypeFilter = AdvertisementTypeFilter.ALL;
    this.activeAdType = null;
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'secondary';
    this.filterOfferButtonVariant = 'outline-secondary';
    this.filterDemandButtonVariant = 'outline-secondary';
  }

  public showOfferAdvertisements(): void {
    this.activeAdTypeFilter = AdvertisementTypeFilter.OFFER;
    this.activeAdType = this.advertisementTypes.find(type => type.typeEn === AdvertisementTypeOptions.OFFER);
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterOfferButtonVariant = 'secondary';
    this.filterDemandButtonVariant = 'outline-secondary';
  }

  public showDemandAdvertisements(): void {
    this.activeAdTypeFilter = AdvertisementTypeFilter.DEMAND;
    this.activeAdType = this.advertisementTypes.find(type => type.typeEn === AdvertisementTypeOptions.DEMAND);
    this.retrieveAllAdvertisements();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterOfferButtonVariant = 'outline-secondary';
    this.filterDemandButtonVariant = 'secondary';
  }

  public advertisementCategorizationBranch(advertisement: IAdvertisement): string {
    let branch = '';
    const currentLanguage = this.$store.getters.currentLanguage;

    if (currentLanguage === 'sr') {
      branch =
        advertisement.subsubcategory.advertisementSubcategory.advertisementCategory.name +
        ' / ' +
        advertisement.subsubcategory.advertisementSubcategory.name +
        ' / ' +
        advertisement.subsubcategory.name;

      return branch;
    }

    if (currentLanguage === 'src') {
      branch =
        advertisement.subsubcategory.advertisementSubcategory.advertisementCategory.nameSrc +
        ' / ' +
        advertisement.subsubcategory.advertisementSubcategory.nameSrc +
        ' / ' +
        advertisement.subsubcategory.nameSrc;

      return branch;
    }

    if (currentLanguage === 'en') {
      branch =
        advertisement.subsubcategory.advertisementSubcategory.advertisementCategory.nameEn +
        ' / ' +
        advertisement.subsubcategory.advertisementSubcategory.nameEn +
        ' / ' +
        advertisement.subsubcategory.nameEn;

      return branch;
    }

    return branch;
  }

  public advertisementKindsString(advertisement: IAdvertisement) {
    const currentLanguage = this.$store.getters.currentLanguage;

    return advertisement.kinds
      .map(kind => {
        if (currentLanguage === 'sr') {
          return kind.kind;
        } else if (currentLanguage === 'src') {
          return kind.kindSrc;
        } else if (currentLanguage === 'en') {
          return kind.kindEn;
        }
        return '';
      })
      .join(', ');
  }
}
