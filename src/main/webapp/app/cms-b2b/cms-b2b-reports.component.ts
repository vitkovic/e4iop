import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import CMSB2BService from './cms-b2b.service';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IAdvertisementType } from '@/shared/model/advertisement-type.model';
import { IAdvertisementKind } from '@/shared/model/advertisement-kind.model';
import { IAdvertisementSubsubcategory } from '@/shared/model/advertisement-subsubcategory.model';
//import { IInquiry } from '@/shared/model/inquiry.model';
import { ICompanyRatingsDTO } from '@/shared/model/dto/company-ratings-dto.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import AccountService from '@/account/account.service';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import AdvertisementCategoryService from '@/entities/advertisement-category/advertisement-category.service';
import AdvertisementSubsubcategoryService from '@/entities/advertisement-category/advertisement-subsubcategory.service';
import AdvertisementTypeService from '@/entities/advertisement-type/advertisement-type.service';
import AdvertisementKindService from '@/entities/advertisement-kind/advertisement-kind.service';
import CollaborationStatusService from '@/entities/collaboration-status/collaboration-status.service';
import AdvertisementService from '@/entities/company/advertisement.service';
import CompanyService from '@/entities/company/company.service';
import DateTimeUtils from '@/shared/data/datetime-utils.service';
import { numeric, required, minLength, maxLength, minValue, maxValue, requiredIf } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

interface RatingFilters {
  types: IAdvertisementType[];
  kinds: IAdvertisementKind[];
  subsubcategories: IAdvertisementSubsubcategory[];
}

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CMSB2BReports extends Vue {
  @Inject('cmsB2BService') private cmsB2BService: () => CMSB2BService;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('advertisementSubsubcategoryService') private advertisementSubsubcategoryService: () => AdvertisementSubsubcategoryService;
  @Inject('advertisementCategoryService') private advertisementCategoryService: () => AdvertisementCategoryService;
  @Inject('advertisementTypeService') private advertisementTypeService: () => AdvertisementTypeService;
  @Inject('advertisementKindService') private advertisementKindService: () => AdvertisementKindService;
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;
  @Inject('dateTimeUtils') private dateTimeUtils: () => DateTimeUtils;

  public advertisements: IAdvertisement[] = [];
  public collaborations: ICollaboration[] = [];
  public portalUsers: IPortalUser[] = [];
  public companyRatings: ICompanyRatingsDTO[] = [];

  public itemsPerPage = 500000;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public isFetching = false;
  public advCount = 0;
  private advCategList = null;
  private mainSearchCategory = null;
  // public advCategory = ;
  public advSubsubcategory: IAdvertisementSubsubcategory | null = null;
  public advType = 1;
  public advTypeList = null;
  public advKind = 1;
  public advKindList = null;
  public advCompany = 1;
  public advCompanyList = null;
  public advSubsubcategoryList: IAdvertisementSubsubcategory[] = [];
  public activationDatetimeTo = null;
  public activationDatetimeFrom = null;
  public collabStatusList = null;
  public collabStatus = 1;
  public collabCount = 0;
  public inqStatus = 1;
  public inqCount = 0;
  public inqStatusList = null;
  public usersCount = 0;
  public currentLanguage = '';
  public propOrderCollab = 'id';
  public reverseCollab = false;

  public ratingFilters: RatingFilters = {
    types: [],
    kinds: [],
    subsubcategories: [],
  };

  data() {
    return {
      advcount: 0,
      mainSearchCategory: 1,
      advType: 1,
      advKind: 1,
      collabCount: 0,
      collabStatus: 1,
      inqCount: 0,
      inqStatus: 1,
      usersCount: 0,
      activationDatetimeTo: null,
      activationDatetimeFrom: null,
    };
  }

  private hasAnyAuthorityValue = false;

  public retrieveCollaborations(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sortCollab(),
    };

    this.cmsB2BService()
      .retrieveCoolaborationsByStatus(this.collabStatus, paginationQuery)
      .then(
        res => {
          this.collaborations = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.collabCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveAdvertisements(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.advertisements = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.advCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveAdvertisementsByCategory(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearch('', this.mainSearchCategory, paginationQuery)
      .then(
        res => {
          this.advertisements = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.advCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveAdvertisementsByDateInterval(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearchDates(this.activationDatetimeFrom.toString(), this.activationDatetimeTo.toString(), paginationQuery)
      .then(
        res => {
          this.advertisements = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.advCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveAdvertisementsByKind(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearchKind(this.advKind, paginationQuery)
      .then(
        res => {
          this.advertisements = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.advCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveAdvertisementsByType(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearchType(this.advType, paginationQuery)
      .then(
        res => {
          this.advertisements = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.advCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveFilteredCollaborations(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearchCollabDates(
        this.activationDatetimeFrom.toString(),
        this.activationDatetimeTo.toString(),
        this.collabStatus,
        this.advType,
        this.advSubsubcategory.id,
        this.advKind,
        paginationQuery
      )
      .then(
        res => {
          this.collaborations = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.collabCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveUsersByDateInterval(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearchUsersDates(
        this.isFetching,
        this.activationDatetimeFrom.toString(),
        this.activationDatetimeTo.toString(),
        paginationQuery
      )
      .then(
        res => {
          this.portalUsers = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.usersCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveFilteredRatings() {
    // If no type is selected include all types
    let types: number[] = [];
    if (this.ratingFilters.types.length > 0) {
      types = this.ratingFilters.types.map(type => type.id);
    } else {
      types = this.advTypeList.map(type => type.id);
    }

    // If no kind is selected include all kinds
    let kinds: number[] = [];
    if (this.ratingFilters.kinds.length > 0) {
      kinds = this.ratingFilters.kinds.map(kind => kind.id);
    } else {
      kinds = this.advKindList.map(kind => kind.id);
    }

    // If no subsubcategory is selected include all subsubcategories
    let subsubcategories: number[] = [];
    if (this.ratingFilters.subsubcategories.length > 0) {
      subsubcategories = this.ratingFilters.subsubcategories.map(subsubcategory => subsubcategory.id);
    } else {
      subsubcategories = this.advSubsubcategoryList.map(subsubcategory => subsubcategory.id);
    }

    this.cmsB2BService()
      .getRatingsReport(this.activationDatetimeFrom.toString(), this.activationDatetimeTo.toString(), types, kinds, subsubcategories)
      .then(res => {
        this.companyRatings = res.data;
        console.log(res.data);
      });
  }

  public updateInstantFieldFrom(event) {
    if (event.target.value) {
      this.activationDatetimeFrom = event.target.value;
      console.log(this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveAdvertisementsByDateInterval();
    } else {
      this.activationDatetimeFrom = null;
    }
  }

  public updateInstantFieldTo(event) {
    if (event.target.value) {
      this.activationDatetimeTo = event.target.value;
      if (this.activationDatetimeFrom != null) this.retrieveAdvertisementsByDateInterval();
    } else {
      this.activationDatetimeTo = null;
    }
  }

  public updateInstantFieldFromCollab(event) {
    if (event.target.value) {
      this.activationDatetimeFrom = event.target.value;
      console.log(this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveFilteredCollaborations();
    } else {
      this.activationDatetimeFrom = null;
    }
  }

  public updateInstantFieldToCollab(event) {
    if (event.target.value) {
      this.activationDatetimeTo = event.target.value;
      if (this.activationDatetimeFrom != null) this.retrieveFilteredCollaborations();
    } else {
      this.activationDatetimeTo = null;
    }
  }

  public updateInstantFieldFromUsers(event) {
    if (event.target.value) {
      this.activationDatetimeFrom = event.target.value;
      console.log(this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveUsersByDateInterval();
    } else {
      this.activationDatetimeFrom = null;
    }
  }

  public updateInstantFieldToUsers(event) {
    if (event.target.value) {
      this.activationDatetimeTo = event.target.value;
      if (this.activationDatetimeFrom != null) this.retrieveUsersByDateInterval();
    } else {
      this.activationDatetimeTo = null;
    }
  }

  public updateInstantFieldFromRatings(event) {
    if (event.target.value) {
      this.activationDatetimeFrom = event.target.value;
      console.log(this.activationDatetimeFrom);
      if (this.activationDatetimeTo != null) this.retrieveFilteredRatings();
    } else {
      this.activationDatetimeFrom = null;
    }
  }

  public updateInstantFieldToRatings(event) {
    if (event.target.value) {
      this.activationDatetimeTo = event.target.value;
      if (this.activationDatetimeFrom != null) this.retrieveFilteredRatings();
    } else {
      this.activationDatetimeTo = null;
    }
  }

  public retrieveAdvertisementsByCompany(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveSearchCompany(this.advCompany, paginationQuery)
      .then(
        res => {
          this.advertisements = res.data;
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.advCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
  public retrieveUsers(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.cmsB2BService()
      .retrieveUsers(paginationQuery)
      .then(
        res => {
          this.portalUsers = res.data;
          // console.log("Users uuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
          console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.usersCount = this.queryCount;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public sortCollab(): Array<any> {
    const result = [this.propOrderCollab + ',' + (this.reverseCollab ? 'asc' : 'desc')];
    if (this.propOrderCollab !== 'id') {
      result.push('id');
    }
    return result;
  }
  public mounted(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.retrieveAdvertisements();
    this.retrieveCollaborations();
    this.retrieveUsers();
    this.initRelationships();

    let currentDate = new Date();
    let monthsAgoDate = this.dateTimeUtils().getDateMonthsAgo(currentDate, 1);

    this.activationDatetimeFrom = this.dateTimeUtils().formatForDatetimeLocalInput(monthsAgoDate);
    this.activationDatetimeTo = this.dateTimeUtils().formatForDatetimeLocalInput(currentDate);
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
  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAdvertisements();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public transitionUsers(): void {
    this.retrieveUsers();
  }

  public changeOrderUsers(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transitionUsers();
  }

  public initRelationships(): void {
    this.advertisementSubsubcategoryService()
      .retrieve()
      .then(res => {
        this.advSubsubcategoryList = res.data;
        this.advSubsubcategory = this.advSubsubcategoryList[0];
      });

    this.advertisementCategoryService()
      .retrieve()
      .then(res => {
        this.advCategList = res.data;
        this.mainSearchCategory = 1;
      });

    this.advertisementTypeService()
      .retrieve()
      .then(res => {
        this.advTypeList = res.data;
        // console.log(res.data);
        this.advType = 1;
      });

    this.advertisementKindService()
      .retrieve()
      .then(res => {
        this.advKindList = res.data;
        //  console.log(res.data);
        this.advKind = 1;
      });

    this.companyService()
      .retrieve()
      .then(res => {
        this.advCompanyList = res.data;
        //   console.log(res.data);
        this.advCompany = 1;
      });

    this.collaborationStatusService()
      .retrieve()
      .then(res => {
        this.collabStatusList = res.data;
        //  console.log(res.data);
        this.collabStatus = 1;
      });
  }

  public formArrayForAdv(items): any {
    let newitems = [];

    for (let i = 0; i < items.length; i++) {
      // eslint-disable-line
      let line = '';
      //  console.log(items[i]);
      let newitemssub = [];
      for (const index in items[i]) {
        //console.log(`${index}: ${items[i][index]}`);

        if (index == 'id') newitemssub[index] = items[i][index];

        if (index == 'title') newitemssub[index] = items[i][index];

        if (index == 'description') newitemssub[index] = items[i][index];

        if (index == 'activationDatetime') newitemssub[index] = items[i][index];

        if (index == 'createdAt') newitemssub[index] = items[i][index];

        if (index == 'expirationDatetime') newitemssub[index] = items[i][index];

        if (index == 'budget') newitemssub[index] = items[i][index];

        if (index == 'company') {
          if (items[i][index].name !== 'undefined') newitemssub[index] = items[i][index].name;
        }

        if (index == 'kinds') {
          if (items[i][index] !== 'undefined' && items[i][index] !== null && items[i][index].length > 0)
            newitemssub[index] = items[i][index][0].kind;
          else newitemssub[index] = 'Nije na raspolaganju';
        }
      }

      newitems[i] = newitemssub;
      console.log(newitems[i]);
    }
    return newitems;
  }

  public formArrayForCollab(items: ICollaboration): any {
    let newitems = [];

    for (let i = 0; i < items.length; i++) {
      // eslint-disable-line
      let line = '';
      //  console.log(items[i]);
      let newitemssub = [];
      for (const index in items[i]) {
        console.log(`${index}: ${items[i][index]}`);

        if (index == 'id') newitemssub[index] = items[i][index];

        if (index == 'ratingOffer' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].description !== 'undefined') newitemssub[index] = items[i][index].description;
        } else if (index == 'ratingOffer') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'ratingRequest' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].description !== 'undefined') newitemssub[index] = items[i][index].description;
        } else if (index == 'ratingRequest') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'companyOffer' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].name !== 'undefined') newitemssub[index] = items[i][index].name;
        } else if (index == 'companyOffer') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'companyRequest' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].name !== 'undefined') newitemssub[index] = items[i][index].name;
        } else if (index == 'companyRequest') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'status' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].status !== 'undefined') newitemssub[index] = items[i][index].status;
        } else if (index == 'status') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'commentOffer') {
          newitemssub[index] = items[i][index];
        }

        if (index == 'commentRequest') {
          newitemssub[index] = items[i][index];
        }

        if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].title !== 'undefined') newitemssub[index] = items[i][index].title;
        } else if (index == 'advertisement') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].type !== 'undefined' && items[i][index].type !== null) newitemssub['Type'] = items[i][index].type.type;
        } else if (
          index == 'advertisement' &&
          items[i][index] == null &&
          (items[i][index].type == 'undefined' || items[i][index].type == null)
        ) {
          newitemssub['Type'] = 'Nije definisano';
        }

        if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].kinds !== 'undefined' && items[i][index].kinds !== null)
            newitemssub['Kind'] = items[i][index].kinds.map(kind => kind.kind).join(' - ');
        } else if (
          index == 'advertisement' &&
          items[i][index] == null &&
          (items[i][index].kinds == 'undefined' || items[i][index].kinds == null)
        ) {
          newitemssub['Kind'] = 'Nije definisano';
        }

        if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].subsubcategory !== 'undefined' && items[i][index].subsubcategory !== null)
            newitemssub['Categorization'] = this.advertisementCategorizationBranch(items[i][index]);
        } else if (
          index == 'advertisement' &&
          items[i][index] == null &&
          (items[i][index].subsubcategory == 'undefined' || items[i][index].subsubcategory == null)
        ) {
          newitemssub['Categorization'] = 'Nije definisano';
        }

        // if (index == 'advertisement' && items[i][index] != null && items[i][index] !== 'undefined' ) {
        // 	if (items[i][index].description !== 'undefined')
        // 	newitemssub[index] = items[i][index].description;
        // } else if (index == 'advertisement') {
        // 	newitemssub[index] = 'Nije definisano';
        // }

        if (index == 'datetime') newitemssub[index] = items[i][index];
      }

      newitems[i] = newitemssub;
      console.log(newitems[i]);
    }
    return newitems;
  }

  public formArrayForUsers(items): any {
    let newitems = [];
    console.log(items);
    for (let i = 0; i < items.length; i++) {
      // eslint-disable-line
      let line = '';
      //  console.log(items[i]);
      let newitemssub = [];
      for (const index in items[i]) {
        console.log(`${index}: ${items[i][index]}`);

        if (index == 'id') newitemssub[index] = items[i][index];

        if (index == 'user' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].firstName !== 'undefined') newitemssub['firstName'] = items[i][index].firstName;
        } else if (index == 'user') {
          newitemssub['firstName'] = 'Nije definisano';
        }

        if (index == 'user' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].lastName !== 'undefined') newitemssub['lastName'] = items[i][index].lastName;
        } else if (index == 'user') {
          newitemssub['lastName'] = 'Nije definisano';
        }

        if (index == 'user' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].email !== 'undefined') newitemssub['email'] = items[i][index].email;
          console.log('email' + newitemssub[index]);
        } else if (index == 'user') {
          newitemssub['email'] = 'Nije definisano';
        }

        if (index == 'company' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].name !== 'undefined') newitemssub[index] = items[i][index].name;
        } else if (index == 'company') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'userOrganization' && items[i][index] != null && items[i][index] !== 'undefined') {
          if (items[i][index].legalNameSr !== 'undefined') newitemssub[index] = items[i][index].legalNameSr;
        } else if (index == 'userOrganization') {
          newitemssub[index] = 'Nije definisano';
        }

        if (index == 'phone') newitemssub[index] = items[i][index];

        if (index == 'createdAt') newitemssub[index] = items[i][index];

        if (index == 'advertisementCreateds') newitemssub[index] = items[i][index];
      }

      newitems[i] = newitemssub;
      console.log(newitems[i]);
    }
    return newitems;
  }

  public formArrayForRatings(items): any {
    let newitems = [];
    console.log(items);

    let dateFrom = this.activationDatetimeFrom;
    let dateTo = this.activationDatetimeTo;
    let typesText = this.selectedTypeFiltersText();
    let kindsText = this.selectedKindFiltersText();
    let subsubcategoriesText = this.selectedSubsubcategoryFiltersText();

    for (let i = 0; i < items.length; i++) {
      let newitemssub = [];

      newitemssub['company'] = items[i]['companyName'];
      newitemssub['dateFrom'] = dateFrom;
      newitemssub['dateTo'] = dateTo;
      newitemssub['type'] = typesText;
      newitemssub['kind'] = kindsText;
      newitemssub['subsubcategory'] = subsubcategoriesText;
      newitemssub['totalRatings'] = items[i]['totalRatings'];
      newitemssub['averageRating'] = items[i]['averageRating'];
      newitemssub['totalRatings1'] = items[i]['totalRatings1'];
      newitemssub['percentageRating1'] = items[i]['percentageRating1'];
      newitemssub['totalRatings2'] = items[i]['totalRatings2'];
      newitemssub['percentageRating2'] = items[i]['percentageRating2'];
      newitemssub['totalRatings3'] = items[i]['totalRatings3'];
      newitemssub['percentageRating3'] = items[i]['percentageRating3'];
      newitemssub['totalRatings4'] = items[i]['totalRatings4'];
      newitemssub['percentageRating4'] = items[i]['percentageRating4'];

      newitems[i] = newitemssub;
      console.log(newitems[i]);
    }

    return newitems;
  }

  public exportCSVFile(items, fileTitle): any {
    var headers;
    var type;

    items = this[items];

    if (fileTitle == 'advertisements') {
      headers = {
        id: 'ID',
        createdAt: 'Kreiran',
        activationD: 'Datum aktivacije',
        expirationD: 'Datum isteka',
        title: 'Naslov',
        desc: 'Opis',
        budget: 'Budžet',
        kinds: 'Kategorija',
        company: 'Kompanija',
      };
      type = 0;
      items = this.formArrayForAdv(items);
    } else if (fileTitle == 'collaborations') {
      headers = {
        id: 'ID',
        createdAt: 'Datum',
        commentO: 'Komentar oglašivača',
        commentT: 'Komentar Tražioca',
        advC1: 'Oglašivač',
        advC2: 'Tražilac',
        adv: 'Naslov oglasa',
        advType: 'Tip',
        advKind: 'Vrsta',
        advCategorization: 'Kategorizacija',
        mark1: 'Ocena Oglašivača',
        mark2: 'Ocena tražioca',
        state: 'Status',
      };
      type = 1;
      items = this.formArrayForCollab(items);
    } else if (fileTitle == 'users') {
      headers = {
        id: 'ID',
        createdat: 'Datum kreiranja',
        phone: 'Telefon',
        name: 'Ime',
        last: 'Prezime',
        email: 'E-mail',
        ads: 'Broj kreiranih oglasa',
        company: 'Kompanija',
        organisation: 'Organizacija',
      };
      type = 2;
      console.log('users');
      items = this.formArrayForUsers(items);
    } else if (fileTitle == 'ratings') {
      headers = {
        company: this.$t('riportalApp.collaborationRating.ratings.company'),
        dateFrom: this.$t('riportalApp.reports.filters.dateFrom'),
        dateTo: this.$t('riportalApp.reports.filters.dateTo'),
        type: this.$t('riportalApp.advertisement.type'),
        kind: this.$t('riportalApp.advertisement.kind'),
        subsubcategory: this.$t('riportalApp.advertisement.categorization'),
        totalRatings: this.$t('riportalApp.collaborationRating.ratings.totalRatings'),
        averageRating: this.$t('riportalApp.collaborationRating.ratings.averageRating'),
        totalRatings1: this.$t('riportalApp.collaborationRating.ratings.totalRatings1'),
        percentageRating1: this.$t('riportalApp.collaborationRating.ratings.percentageRating1'),
        totalRatings2: this.$t('riportalApp.collaborationRating.ratings.totalRatings2'),
        percentageRating2: this.$t('riportalApp.collaborationRating.ratings.percentageRating2'),
        totalRatings3: this.$t('riportalApp.collaborationRating.ratings.totalRatings3'),
        percentageRating3: this.$t('riportalApp.collaborationRating.ratings.percentageRating3'),
        totalRatings4: this.$t('riportalApp.collaborationRating.ratings.totalRatings4'),
        percentageRating4: this.$t('riportalApp.collaborationRating.ratings.percentageRating4'),
      };
      type = 3;
      items = this.formArrayForRatings(items);
    }

    if (headers) {
      items.unshift(headers);
    }

    console.log(items);

    // const jsonObject = JSON.stringify(items);
    const csv = this.convertToCSV(items, type);
    const exportedFilename = fileTitle + '.csv' || 'export.csv'; // eslint-disable-line
    const blob = new Blob(['\ufeff', csv], { type: 'text/csv;charset=utf-8;' });
    if ((window.navigator as any).msSaveBlob) {
      (window.navigator as any).msSaveBlob(blob, exportedFilename);
    } else {
      const link = document.createElement('a');
      if (link.download !== undefined) {
        const url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', exportedFilename);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }
  }
  public convertToCSV(objArray, type): any {
    const array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
    let str = '';
    for (let i = 0; i < array.length; i++) {
      // eslint-disable-line
      let line = '';
      // console.log(array[i]);
      let j = 0;
      for (const index in array[i]) {
        if (j == 9 && type == 0) break;
        if (j == 14 && type == 1) break;
        if (j == 9 && type == 2) break;
        //	console.log(`${index}: ${array[i][index]}`);
        let div = document.createElement('div');
        div.innerHTML = array[i][index];
        let text = div.textContent || div.innerText || '';
        str += text + ',';
        j++;
      }
      str += '\r\n'; // eslint-disable-line
    }
    return str;
  }

  public changeOrderCollab(propOrderCollab): void {
    this.propOrderCollab = propOrderCollab;
    this.reverseCollab = !this.reverseCollab;
    this.transitionCollab();
  }

  public transitionCollab(): void {
    this.retrieveCollaborations();
  }

  public advertisementCategorizationBranch(instance: IAdvertisement | IAdvertisementSubsubcategory): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementCategorizationBranch(instance, currentLanguage);
  }

  public advertisementKindsString(advertisement: IAdvertisement): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementKindsString(advertisement, currentLanguage);
  }

  public advertisementKindTranslation(kind: IAdvertisementKind): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementKindTranslation(kind, currentLanguage);
  }

  public advertisementTypeTranslation(type: IAdvertisementType): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementTypeTranslation(type, currentLanguage);
  }

  public selectedTypeFiltersText(): string {
    if (this.ratingFilters.types.length > 0) {
      let types = this.ratingFilters.types.map(type => this.advertisementTypeTranslation(type));
      return types.join(' - ');
    } else {
      return this.$t('riportalApp.reports.filters.all');
    }
  }

  public selectedKindFiltersText(): string {
    if (this.ratingFilters.kinds.length > 0) {
      let kinds = this.ratingFilters.kinds.map(kind => this.advertisementKindTranslation(kind));
      return kinds.join(' - ');
    } else {
      return this.$t('riportalApp.reports.filters.all');
    }
  }

  public selectedSubsubcategoryFiltersText(): string {
    if (this.ratingFilters.subsubcategories.length > 0) {
      let subsubcategories = this.ratingFilters.subsubcategories.map(subsubcategory =>
        this.advertisementCategorizationBranch(subsubcategory)
      );
      return subsubcategories.join(' - ');
    } else {
      return this.$t('riportalApp.reports.filters.all');
    }
  }
}
