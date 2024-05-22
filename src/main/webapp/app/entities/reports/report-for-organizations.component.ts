import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';

import PortalUserOrganizationService from '@/entities/portal-user-organization/portal-user-organization.service';
import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';
import { IServiceAvailability } from '@/shared/model/service-availability.model';
import { IServiceSubtype, ServiceSubtype } from '@/shared/model/service-subtype.model';
import ReportService from '@/entities/reports/report.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ReportForOrganizations extends mixins(AlertMixin) {
  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;
  @Inject('reportService') private reportService: () => ReportService;

  public isFetching = false;
  public currentLanguage = '';
  public search = {
    searchTerms: '',
    currentLanguage: '',
    selType: '',
    fromDate: null,
    toDate: null,
    organizationId: 0,
  };

  public organizations1: {}[] = [];

  public organizations: {
    id: number;
    type: string;
    name: string;
    address: string;
    details: {
      infrastructureNumber: number;
      serviceNumber: number;
      requiredServiceNumber: number;
      realizedServiceNumber: number;
      requestedServiceNumber: number;
      offeredServiceNumber: number;
      realizedServiceNumber1: number;
    };
    services: {
      requestForServiceNumber: number;
      realizedRequestsForServiceNumber: number;
      nameSr: string;
      nameEn: string;
      nameSrCyr: string;
      descriptionSr: string;
      descriptionEn: string;
      descriptionSrCyr: string;
      researchInfrastructure: IResearchInfrastructure;
      availability: IServiceAvailability;
      subtypes: IServiceSubtype;
    }[];
  }[] = [];

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
    this.search.organizationId = org.id;
    if (this.organizations[index].details) {
      this.organizations[index].details = null;
      return;
    } else {
      this.clearDetailsAndServices();
      this.portalUserOrganizationService()
        .retrieveDetails(this.search)
        .then(res => {
          this.organizations[index].services = null;
          this.organizations[index].details = res.data;
        });
    }
  }

  public stampanjeExcel(): void {
    this.search.currentLanguage = this.currentLanguage;
    this.reportService()
      .createStatisticOrganizationsExcel(this.search)
      .then(res => {
        var fileURL = window.URL.createObjectURL(new Blob([res]));
        var fileLink = document.createElement('a');
        fileLink.href = fileURL;
        fileLink.setAttribute('download', 'Statistika za organizacije.xlsx');
        document.body.appendChild(fileLink);
        fileLink.click();
      });
  }

  public clearDetailsAndServices(): void {
    for (let i = 0; i < this.organizations.length; i++) {
      this.organizations[i].details = null;
      this.organizations[i].services = null;
    }
  }

  public showServices(index: number) {
    let org = this.organizations[index];
    this.search.organizationId = org.id;
    if (this.organizations[index].services) {
      this.organizations[index].services = null;
      return;
    } else {
      this.clearDetailsAndServices();
      this.reportService()
        .retrieveDetailsWithServices(this.search)
        .then(res => {
          this.organizations[index].details = null;
          this.organizations[index].services = res.data;
          var a = 1;
        });
    }
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
