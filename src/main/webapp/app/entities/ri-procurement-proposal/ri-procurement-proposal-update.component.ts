import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IRiProcurementProposal, RiProcurementProposal } from '@/shared/model/ri-procurement-proposal.model';
import RiProcurementProposalService from './ri-procurement-proposal.service';

const validations: any = {
  riProcurementProposal: {
    date: {},
    name: {},
    technicalSpecification: {},
    purpose: {},
  },
};

@Component({
  validations,
})
export default class RiProcurementProposalUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('riProcurementProposalService') private riProcurementProposalService: () => RiProcurementProposalService;
  public riProcurementProposal: IRiProcurementProposal = new RiProcurementProposal();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riProcurementProposalId) {
        vm.retrieveRiProcurementProposal(to.params.riProcurementProposalId);
      }
      vm.initRelationships();
    });
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

  public save(): void {
    this.isSaving = true;
    if (this.riProcurementProposal.id) {
      this.riProcurementProposalService()
        .update(this.riProcurementProposal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riProcurementProposal.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.riProcurementProposalService()
        .create(this.riProcurementProposal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.riProcurementProposal.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.riProcurementProposal[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.riProcurementProposal[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.riProcurementProposal[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.riProcurementProposal[field] = null;
    }
  }

  public retrieveRiProcurementProposal(riProcurementProposalId): void {
    this.riProcurementProposalService()
      .find(riProcurementProposalId)
      .then(res => {
        res.date = new Date(res.date);
        this.riProcurementProposal = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
  }
}
