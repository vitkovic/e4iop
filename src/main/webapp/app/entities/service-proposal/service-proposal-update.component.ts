import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { IServiceProposal, ServiceProposal } from '@/shared/model/service-proposal.model';
import ServiceProposalService from './service-proposal.service';

const validations: any = {
  serviceProposal: {
    date: {},
    name: {},
    description: {},
    purpose: {},
  },
};

@Component({
  validations,
})
export default class ServiceProposalUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('serviceProposalService') private serviceProposalService: () => ServiceProposalService;
  public serviceProposal: IServiceProposal = new ServiceProposal();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.serviceProposalId) {
        vm.retrieveServiceProposal(to.params.serviceProposalId);
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
    if (this.serviceProposal.id) {
      this.serviceProposalService()
        .update(this.serviceProposal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceProposal.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.serviceProposalService()
        .create(this.serviceProposal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.serviceProposal.created', { param: param.id });
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
      this.serviceProposal[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.serviceProposal[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.serviceProposal[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.serviceProposal[field] = null;
    }
  }

  public retrieveServiceProposal(serviceProposalId): void {
    this.serviceProposalService()
      .find(serviceProposalId)
      .then(res => {
        res.date = new Date(res.date);
        this.serviceProposal = res;
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
