import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CollaborationService from '../collaboration/collaboration.service';
import { ICollaboration } from '@/shared/model/collaboration.model';

import AlertService from '@/shared/alert/alert.service';
import { ICollaborationRating, CollaborationRating } from '@/shared/model/collaboration-rating.model';
import CollaborationRatingService from './collaboration-rating.service';

const validations: any = {
  collaborationRating: {
    number: {
      required,
      numeric,
    },
    description: {
      required,
    },
    isVisible: {},
    isDeleted: {},
  },
};

@Component({
  validations,
})
export default class CollaborationRatingUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('collaborationRatingService') private collaborationRatingService: () => CollaborationRatingService;
  public collaborationRating: ICollaborationRating = new CollaborationRating();

  @Inject('collaborationService') private collaborationService: () => CollaborationService;

  public collaborations: ICollaboration[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.collaborationRatingId) {
        vm.retrieveCollaborationRating(to.params.collaborationRatingId);
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
    if (this.collaborationRating.id) {
      this.collaborationRatingService()
        .update(this.collaborationRating)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.collaborationRating.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.collaborationRatingService()
        .create(this.collaborationRating)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.collaborationRating.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCollaborationRating(collaborationRatingId): void {
    this.collaborationRatingService()
      .find(collaborationRatingId)
      .then(res => {
        this.collaborationRating = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.collaborationService()
      .retrieve()
      .then(res => {
        this.collaborations = res.data;
      });
    this.collaborationService()
      .retrieve()
      .then(res => {
        this.collaborations = res.data;
      });
  }
}
