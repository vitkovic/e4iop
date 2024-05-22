import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';

import PortalUserOrganizationService from '@/entities/portal-user-organization/portal-user-organization.service';
import ReportService from '@/entities/reports/report.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceStatisticNio extends mixins(AlertMixin) {
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  @Inject('reportService') private reportService: () => ReportService;

  public isFetching = false;
  public currentLanguage = '';
  public search = {
    searchTerms: '',
    currentLanguage: '',
    selType: '',
  };

  public organizations: {
    id: number;
    type: string;
    name: string;
    address: string;
    services: {
      nameSr: string;
    }[];
  }[] = [];

  public services: {}[] = [];
  public fields: any[] = [];
  public filter: string = '';
  public perPage = 20;
  public currentPage = 1;
  //computed property
  get rows() {
    return this.services.length;
  }

  public mounted(): void {
    this.retrieveAllOrganizations();
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

  public changeFieldsNames(): void {
    if (this.currentLanguage === 'sr') {
      this.fields = [
        { key: 'nameSr', label: this.$t('riportalApp.reports.name'), sortable: true },
        { key: 'descriptionSr', label: this.$t('riportalApp.reports.description'), sortable: true },
        { key: 'researchInfrastructure.nameSr', label: this.$t('riportalApp.reports.infrastructureName'), sortable: true },
        { key: 'availability.availability', label: this.$t('riportalApp.reports.availability'), sortable: true },
        { key: 'subtypes.subtype', label: this.$t('riportalApp.reports.type'), sortable: true },
        { key: 'requestForServiceNumber', label: this.$t('riportalApp.reports.numberOfRequests'), sortable: true },
        { key: 'realizedRequestsForServiceNumber', label: this.$t('riportalApp.reports.realizedNumberOfRequests'), sortable: true },
      ];
    } else if (this.currentLanguage === 'src') {
      this.fields = [
        { key: 'nameSrCyr', label: this.$t('riportalApp.reports.name'), sortable: true },
        { key: 'descriptionSrCyr', label: this.$t('riportalApp.reports.description'), sortable: true },
        { key: 'researchInfrastructure.nameSrCyr', label: this.$t('riportalApp.reports.infrastructureName'), sortable: true },
        { key: 'availability.availabilityCyr', label: this.$t('riportalApp.reports.availability'), sortable: true },
        { key: 'subtypes.subtypeCyr', label: this.$t('riportalApp.reports.type'), sortable: true },
        { key: 'requestForServiceNumber', label: this.$t('riportalApp.reports.numberOfRequests'), sortable: true },
        { key: 'realizedRequestsForServiceNumber', label: this.$t('riportalApp.reports.realizedNumberOfRequests'), sortable: true },
      ];
    } else if (this.currentLanguage === 'en') {
      this.fields = [
        { key: 'nameEn', label: this.$t('riportalApp.reports.name'), sortable: true },
        { key: 'descriptionEn', label: this.$t('riportalApp.reports.description'), sortable: true },
        { key: 'researchInfrastructure.nameEn', label: this.$t('riportalApp.reports.infrastructureName'), sortable: true },
        { key: 'availability.availabilityEn', label: this.$t('riportalApp.reports.availability'), sortable: true },
        { key: 'subtypes.subtypeEn', label: this.$t('riportalApp.reports.type'), sortable: true },
        { key: 'requestForServiceNumber', label: this.$t('riportalApp.reports.numberOfRequests'), sortable: true },
        { key: 'realizedRequestsForServiceNumber', label: this.$t('riportalApp.reports.realizedNumberOfRequests'), sortable: true },
      ];
    }
  }

  public clear(): void {
    this.search.selType = '';
  }

  public retrieveAllOrganizations(): void {
    this.isFetching = true;
    this.portalUserOrganizationService()
      .retrieveForReport()
      .then(
        res => {
          this.organizations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public showDetails(index: number) {
    let org = this.organizations[index];
    if (this.organizations[index].services) {
      this.organizations[index].services = null;
      return;
    }
    this.reportService()
      .retrieveDetailsWithServices(org.id)
      .then(res => {
        this.organizations[index].services = res.data;
        console.log(this.organizations[index].services);
        this.services = res.data;
      });
  }

  public searchOrganizations(): void {
    this.isFetching = true;
    this.search.currentLanguage = this.currentLanguage;
    this.portalUserOrganizationService()
      .search(this.search)
      .then(
        res => {
          this.organizations = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
}
