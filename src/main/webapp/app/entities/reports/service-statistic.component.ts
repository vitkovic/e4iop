import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiService } from '@/shared/model/ri-service.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ReportService from '@/entities/reports/report.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ServiceStatistic extends mixins(AlertMixin) {
  @Inject('reportService') private reportService: () => ReportService;

  public showCounter = true; // prikazuje ili ne nove strane na dnu

  public isFetching = false;
  public currentLanguage = '';
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
    this.retrieveStatistic();
    this.changeFieldsNames();
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
        { key: 'requestRating', label: this.$t('riportalApp.reports.averageRating'), sortable: true },
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

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
        this.changeFieldsNames();
      }
    );
  }

  public retrieveStatistic(): void {
    this.isFetching = true;
    this.reportService()
      .retrieveServiceStatistic()
      .then(
        res => {
          this.services = res.data;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public stampanje(): void {
    this.reportService()
      .createStatisticExcel()
      .then(res => {
        var fileURL = window.URL.createObjectURL(new Blob([res]));
        var fileLink = document.createElement('a');
        fileLink.href = fileURL;
        fileLink.setAttribute('download', 'Statistika za servise.xlsx');
        document.body.appendChild(fileLink);
        fileLink.click();
      });
  }
}
