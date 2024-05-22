import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ResearchInfrastructureService from './research-infrastructure.service';
import { IScientificDomain } from '@/shared/model/scientific-domain.model';
import ScientificDomainService from '@/entities/scientific-domain/scientific-domain.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ResearchInfrastructurePregled extends mixins(AlertMixin) {
  @Inject('researchInfrastructureService') private researchInfrastructureService: () => ResearchInfrastructureService;
  @Inject('scientificDomainService') private scientificDomainService: () => ScientificDomainService;

  public scientificDomains: IScientificDomain[] = [];

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public currentLanguage = '';
  public search = {
    searchTerms: '',
    currentLanguage: '',
    selSubdomains: [],
  };

  public researchInfrastructures: IResearchInfrastructure[] = [];

  public isFetching = false;
  public selectedSubdomains = [];

  public showCounter = true; // prikazuje ili ne nove strane na dnu

  public mounted(): void {
    this.retrieveAllResearchInfrastructures();
    this.retrieveAllScientificDomains();
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

  public retrieveAllScientificDomains(): void {
    this.isFetching = true;

    this.scientificDomainService()
      .retrieveWithSubdomains()
      .then(
        res => {
          this.scientificDomains = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public loadImage(filename): string {
    return 'api/research-infrastructures/image/' + filename;
  }

  public checkEmptySearch(): boolean {
    if (!this.search.searchTerms && !this.selectedSubdomains.length) return true;
    else return false;
  }

  public searchRI(): void {
    this.isFetching = true;
    this.search.currentLanguage = this.currentLanguage;
    this.search.selSubdomains = this.selectedSubdomains;
    if (this.checkEmptySearch()) {
      this.showCounter = true;
      this.retrieveAllResearchInfrastructures();
    } else {
      this.researchInfrastructureService()
        .search(this.search)
        .then(res => {
          this.researchInfrastructures = res.data;
          this.isFetching = false;
          this.showCounter = false;
        });
    }
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
      .retrievePregled(paginationQuery)
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
    // this.transition();
    if (this.reverse) {
      this.researchInfrastructures.sort((a, b) => (a[propOrder] > b[propOrder] ? -1 : 1));
    } else {
      this.researchInfrastructures.sort((a, b) => (a[propOrder] < b[propOrder] ? -1 : 1));
    }
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
