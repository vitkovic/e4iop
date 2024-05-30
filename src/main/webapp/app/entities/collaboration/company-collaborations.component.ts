import { mixins } from 'vue-class-component';
import AlertMixin from '@/shared/alert/alert.mixin';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';

import { ICollaboration } from '@/shared/model/collaboration.model';
import { ICollaborationStatus } from '@/shared/model/collaboration-status.model';
import { ICollaborationRating } from '@/shared/model/collaboration-rating.model';
import { ICompany } from '@/shared/model/company.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';

import CollaborationService from './collaboration.service';
import CollaborationStatusService from '@/entities/collabooration-status.service';
import CollaborationRatingService from '@/entities/collabooration-rating.service';
import CompanyService from '@/entities/company.service';
import AdvertisementService from '@/entities/advertisement.service';

enum CollaborationStatusOptions {
  ACCEPTED = 'прихваћена',
  REJECTED = 'одбијена',
  PENDING = 'на чекању',
}

enum AdvertisementStatusOptions {
  ACTIVE = 'Активан',
  INACTIVE = 'Неактиван',
  ARCHIVED = 'Архивиран',
}

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Collaboration extends mixins(AlertMixin) {
  @Inject('collaborationService') private collaborationService: () => CollaborationService;
  @Inject('collaborationStatusService') private collaborationStatusService: () => CollaborationStatusService;
  @Inject('collaborationRatingService') private collaborationRatingService: () => CollaborationRatingService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'datetime';
  public reverse = false;
  public totalItems = 0;

  public collaborations: ICollaboration[] = [];
  public collaborationToChangeStatus: ICollaboration | null = null;
  public collaborationToRate: ICollaboration | null = null;
  public collaborationRatings: ICollaborationRating[] = [];
  public selectedRating: ICollaborationRating | null = null;
  public company: ICompany = null;
  public advertisement: IAdvertisement = null;
  public ratingComment = '';
  public pendingCollaborationsCount = 0;
  public selectedCollRadioBtn: string = 'ne';
  public isCanceled: boolean = false;

  public isFetching = false;
  public collaborationsStatusChoices: ICollaborationStatus[] | null = null;
  public collaborationStatusOptions = CollaborationStatusOptions;
  public advertisementStatusOptions = AdvertisementStatusOptions;
  public selectedStatusIds: number[] = [];
  public collaborationSideFlags: boolean[] = [];
  public companyOfferFlag = false;
  public companyRequestFlag = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.companyService()
          .find(to.params.companyId)
          .then(res => {
            vm.company = res;
            // vm.getPortalUser();
            vm.retrieveAllCollaborations();
          });
      }
    });
  }

  public mounted(): void {
    this.collaborationStatusService()
      .retrieve()
      .then(res => {
        // this.selectedStatusIds = res.data?.map(option => option.id)
        this.collaborationsStatusChoices = res.data.reverse();
      });

    this.$store.watch(
      () => this.companyOfferFlag,
      () => {
        this.retrieveAllCollaborations();
      }
    );

    this.$store.watch(
      () => this.companyRequestFlag,
      () => {
        this.retrieveAllCollaborations();
      }
    );

    this.$store.watch(
      () => this.selectedStatusIds,
      () => {
        this.retrieveAllCollaborations();
      }
    );
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCollaborations();
  }

  public retrieveAllCollaborations(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    // Check if default collaboration side flags should be set
    if (!this.companyOfferFlag && !this.companyRequestFlag) {
      this.collaborationSideFlags = [true, true];
    } else {
      this.collaborationSideFlags = [this.companyOfferFlag, this.companyRequestFlag];
    }

    // Check if default selected status options should be set
    let selectedStatusIds: number[] = [];
    if (this.selectedStatusIds.length === 0) {
      let statusOptions = this.collaborationsStatusChoices?.map(option => Number(option.id));

      if (statusOptions != undefined) {
        selectedStatusIds = statusOptions;
      }
    } else {
      selectedStatusIds = this.selectedStatusIds;
    }

    this.collaborationService()
      .retrieveByCompany(this.company.id, selectedStatusIds, this.collaborationSideFlags, paginationQuery)
      .then(
        res => {
          this.collaborations = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICollaboration): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCollaboration(): void {
    this.collaborationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('riportalApp.collaboration.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCollaborations();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'datetime') {
      result.push('datetime');
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
    this.retrieveAllCollaborations();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public prepareRating(instance: ICollaboration): void {
    this.collaborationToRate = instance;

    if (this.collaborationRatings.length == 0) {
      this.collaborationRatingService()
        .retrieve()
        .then(res => {
          this.collaborationRatings = res.data;
        });
    }

    if (<any>this.$refs.ratingEntity) {
      (<any>this.$refs.ratingEntity).show();
    }
  }

  public selectRating(rating: ICollaborationRating) {
    this.selectedRating = rating;
  }

  public rateCollaboration() {
    let formData = new FormData();
    formData.append('collaborationId', '' + this.collaborationToRate.id);
    formData.append('ratingId', '' + this.selectedRating.id);
    formData.append('comment', '' + this.ratingComment);

    if (this.company.id === this.collaborationToRate.companyOffer.id) {
      const companyName = this.collaborationToRate.companyRequest.name;

      this.collaborationService()
        .rateCollaborationForCompanyOffer(formData)
        .then(res => {
          this.retrieveAllCollaborations();
          const notificatonMessage = 'Ocenili ste saradnju sa kompanijom ' + companyName;
          this.$notify({
            text: notificatonMessage,
          });
        });
    } else if (this.company.id === this.collaborationToRate.companyRequest.id) {
      const companyName = this.collaborationToRate.companyOffer.name;

      this.collaborationService()
        .rateCollaborationForCompanyRequest(formData)
        .then(res => {
          this.retrieveAllCollaborations();
          const notificatonMessage = 'Ocenili ste saradnju sa kompanijom ' + companyName;
          this.$notify({
            text: notificatonMessage,
          });
        });
    }

    this.closeRatingDialog();
  }

  public closeRatingDialog(): void {
    this.collaborationToRate = null;
    this.selectedRating = null;
    this.ratingComment = '';
    (<any>this.$refs.ratingEntity).hide();
  }

  public prepareCopyAd(instance: ICollaboration): void {
    this.advertisement = instance.advertisement;

    console.log(this.company.id);
    console.log(this.advertisement.company.id);
    console.log(this.advertisement.title);

    if (<any>this.$refs.copyAdModal) {
      (<any>this.$refs.copyAdModal).show();
    }
  }

  public closeCopyAd(): void {
    if (<any>this.$refs.copyAdModal) {
      (<any>this.$refs.copyAdModal).hide();
    }
  }

  public confirmCopyAd(): void {
    this.closeCopyAd();

    if (!this.advertisement) {
      return;
    }

    this.advertisementService()
      .createCopy(this.advertisement.id)
      .then(res => {
        const notificatonMessage = 'Oglas "' + this.advertisement.title + '" je obnovljen';
        this.$notify({
          text: notificatonMessage,
        });
      });
  }

  public ratingExists(collaboration: ICollaboration): boolean {
    return (
      (collaboration.companyOffer.id === this.company.id && collaboration.ratingOffer) ||
      (collaboration.companyRequest.id === this.company.id && collaboration.ratingRequest)
    );
  }

  public prepareConfirmCollaboration(instance: ICollaboration): void {
    this.collaborationToChangeStatus = instance;

    if (this.collaborationToChangeStatus.advertisement) {
      this.collaborationService()
        .getPendingCollaborationsCountForAdvertisement(this.collaborationToChangeStatus.advertisement.id)
        .then(res => {
          this.pendingCollaborationsCount = res.data;
        });
    }

    if (<any>this.$refs.confirmCollaboration) {
      (<any>this.$refs.confirmCollaboration).show();
    }
  }

  public prepareCancelCollaboration(instance: ICollaboration): void {
    this.collaborationToChangeStatus = instance;

    if (<any>this.$refs.cancelCollaboration) {
      (<any>this.$refs.cancelCollaboration).show();
    }
  }

  public closeConfirmCollaboration(): void {
    (<any>this.$refs.confirmCollaboration).hide();
  }

  public closeCancelCollaboration(): void {
    (<any>this.$refs.cancelCollaboration).hide();
  }

  public cancelCollaboration(): void {
    const ADVERTISEMENT_TITLE = this.collaborationToChangeStatus.advertisement.title;

    if (!this.collaborationToChangeStatus) {
      this.closeCancelCollaboration();
      return;
    }

    this.collaborationService()
      .cancelCollaboration(this.collaborationToChangeStatus.id)
      .then(res => {
        this.isCanceled = true;
        const message = this.$t('riportalApp.thread.notifications.cancelCollaboration', { ADVERTISEMENT_TITLE });
        this.$notify({
          text: message,
        });

        this.retrieveAllCollaborations();
      });

    this.closeCancelCollaboration();
  }

  public confirmCollaboration(): void {
    const ADVERTISEMENT_ID = this.collaborationToChangeStatus.advertisement.id;
    const ADVERTISEMENT_TITLE = this.collaborationToChangeStatus.advertisement.title;

    if (!this.collaborationToChangeStatus) {
      this.closeConfirmCollaboration();
      return;
    }

    this.collaborationService()
      .confirmCollaboration(this.collaborationToChangeStatus.id)
      .then(res => {
        const message1 = this.$t('riportalApp.thread.notifications.confirmCollaboration1', { ADVERTISEMENT_TITLE });
        const message2 = this.$t('riportalApp.thread.notifications.confirmCollaboration2', { ADVERTISEMENT_TITLE });

        if (this.selectedCollRadioBtn === 'ne') {
          this.retrieveAllCollaborations();
          this.$notify({
            text: message1,
          });
        } else {
          this.selectedCollRadioBtn = 'ne';

          this.collaborationService()
            .cancelPendingCollaborationsForAdvertisement(ADVERTISEMENT_ID)
            .then(res => {
              this.retrieveAllCollaborations();
              this.$notify({
                text: message2,
              });
            });
        }
      });

    this.closeConfirmCollaboration();
  }
}
