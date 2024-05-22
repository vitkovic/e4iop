import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRiProcurementProposal } from '@/shared/model/ri-procurement-proposal.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RiProcurementProposalService from './ri-procurement-proposal.service';
import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';
import AccountService from '@/account/account.service';
import { NOT_AUTHORIZED } from '@/constants';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RiProcurementProposal extends mixins(AlertMixin) {
  @Inject('riProcurementProposalService') private riProcurementProposalService: () => RiProcurementProposalService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('accountService') private accountService: () => AccountService;

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public riProposalsDTO: {
    id: 0;
    date: Date;
    name: '';
    technicalSpecification: '';
    purpose: '';
    portalUser: null;
    possibleEdit: false;
    likes: 0;
    dislikes: 0;
  }[] = [];

  public search = {
    searchTerms: '',
    currentLanguage: '',
  };

  public InfrastructureProposalsLikes = {
    proposal: null,
    nlike: 0,
    ndislike: 0,
  };

  public riProcurementProposals: IRiProcurementProposal[] = [];

  public isFetching = false;
  private hasAnyAuthorityValue = false;

  public mounted(): void {
    this.retrieveAllRiProcurementProposals();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRiProcurementProposals();
  }

  public retrieveAllRiProcurementProposals(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.riProcurementProposalService()
      .retrieve(paginationQuery)
      .then(
        res => {
          //this.riProcurementProposals = res.data;
          this.riProposalsDTO = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRiProcurementProposal): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRiProcurementProposal(): void {
    this.riProcurementProposalService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.riProcurementProposal.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRiProcurementProposals();
        this.closeDialog();
      })
      .catch(error => {
        if (error.response.status === 400 && error.response.data.type === NOT_AUTHORIZED) {
          const por = this.$t('riportalApp.riResearchOrganization.errors.notAuthorized');
          this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        }
      });
  }

  public like(proposal: any): void {
    this.isFetching = true;
    this.InfrastructureProposalsLikes.proposal = proposal;
    this.InfrastructureProposalsLikes.nlike = 1;
    this.InfrastructureProposalsLikes.ndislike = 0;
    this.riProcurementProposalService()
      .like(this.InfrastructureProposalsLikes)
      .then(res => {
        proposal.likes = res.likes;
        proposal.dislikes = res.dislikes;
        this.isFetching = false;
      });
  }

  public dislike(proposal: any): void {
    this.isFetching = true;
    this.InfrastructureProposalsLikes.proposal = proposal;
    this.InfrastructureProposalsLikes.nlike = 0;
    this.InfrastructureProposalsLikes.ndislike = 1;
    this.riProcurementProposalService()
      .like(this.InfrastructureProposalsLikes)
      .then(res => {
        proposal.likes = res.likes;
        proposal.dislikes = res.dislikes;
        this.isFetching = false;
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
    this.retrieveAllRiProcurementProposals();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public hasAnyRole(roles: any): boolean {
    if (this.userRoles) {
      for (let i = 0; i < roles.length; i++) {
        if (this.userRoles.includes(roles[i])) {
          return true;
        }
      }
    }
    return false;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get userRoles(): any {
    return this.$store.getters.roles;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
