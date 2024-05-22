import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';

import ReportService from '@/entities/reports/report.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class InfrastructureStatistic extends mixins(AlertMixin) {
  @Inject('reportService') private reportService: () => ReportService;

  public isFetching = false;
  public currentLanguage = '';
  public search = {
    searchTerms: '',
    currentLanguage: '',
    selType: '',
  };

  public showDetails = false;

  public infrastructureStatistic = {};

  public mounted(): void {
    this.retrieveStatistic();
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

  public stampanjeExcel(): void {
    this.reportService()
      .createStatisticInfrastructureExcel()
      .then(res => {
        var fileURL = window.URL.createObjectURL(new Blob([res]));
        var fileLink = document.createElement('a');
        fileLink.href = fileURL;
        fileLink.setAttribute('download', 'Statistika za infrastrukturu.xlsx');
        document.body.appendChild(fileLink);
        fileLink.click();
      });
  }

  public showDetailsF(): void {
    if (this.showDetails == false) this.showDetails = true;
    else this.showDetails = false;
  }

  public clear(): void {
    this.search.selType = '';
  }

  public retrieveStatistic(): void {
    this.isFetching = true;
    this.reportService()
      .retrieveInfrastructureStatistic()
      .then(
        res => {
          this.infrastructureStatistic = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
}
