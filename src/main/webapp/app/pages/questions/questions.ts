import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICmsQuestion } from '@/shared/model/cms-question.model';
import CmsQuestionService from './cms-question.service';

@Component
export default class QuestionsComponent extends Vue {
  @Inject('cmsQuestionService')
  private cmsQuestionService: () => CmsQuestionService;

  public cmsQuestions: ICmsQuestion[] = [];
  public activeIndex: number | null = null;

  // Pagination parameters
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'createdAt';
  public reverse = true;
  public totalItems = 0;

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCmsQuestions();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCmsQuestions();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public retrieveAllCmsQuestions(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.cmsQuestionService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cmsQuestions = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public toggleAnswer(index: number): void {
    this.activeIndex = this.activeIndex === index ? null : index;
  }

  public isAnswerShown(index: number): boolean {
    return this.activeIndex === index;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllCmsQuestions();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }
}
