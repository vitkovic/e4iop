import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PortalUserService from '../portal-user/portal-user.service';
import { IPortalUser } from '@/shared/model/portal-user.model';

import AlertService from '@/shared/alert/alert.service';
import { ICmsQuestion, CmsQuestion } from '@/shared/model/cms-question.model';
import CmsQuestionService from './cms-question.service';

const validations: any = {
  cmsQuestion: {
    createdAt: {},
    modifiedAt: {},
    question: {
      required,
    },
    answer: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CmsQuestionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cmsQuestionService') private cmsQuestionService: () => CmsQuestionService;
  public cmsQuestion: ICmsQuestion = new CmsQuestion();

  @Inject('portalUserService') private portalUserService: () => PortalUserService;

  public portalUsers: IPortalUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cmsQuestionId) {
        vm.retrieveCmsQuestion(to.params.cmsQuestionId);
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
    if (this.cmsQuestion.id) {
      this.cmsQuestionService()
        .update(this.cmsQuestion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsQuestion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cmsQuestionService()
        .create(this.cmsQuestion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('riportalApp.cmsQuestion.created', { param: param.id });
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
      this.cmsQuestion[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsQuestion[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cmsQuestion[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.cmsQuestion[field] = null;
    }
  }

  public retrieveCmsQuestion(cmsQuestionId): void {
    this.cmsQuestionService()
      .find(cmsQuestionId)
      .then(res => {
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        this.cmsQuestion = res;
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
    this.portalUserService()
      .retrieve()
      .then(res => {
        this.portalUsers = res.data;
      });
  }
}
