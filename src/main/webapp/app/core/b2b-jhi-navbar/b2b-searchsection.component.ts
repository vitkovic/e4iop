import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';

import { IAdvertisement } from '@/shared/model/advertisement.model';

import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
import AdvertisementService from './advertisement.service';

import SearchPageService from '../../core/b2b-jhi-navbar/searchpage.service';

import { mixins } from 'vue-class-component';

import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';

@Component({
  mixins: [Vue2Filters.mixin],
})
const advProps = Vue.extend({
  props: {
    advertisements: null,
    cmsnews: null,
    cmsquestions: null,
    companies: null,
  },
});

@Component
export default class SearchSection extends advProps {
  @Inject('translationService') private translationService: () => TranslationService;
  @Inject('advertisementService') private advertisementService: () => AdvertisementService;

  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;

  // public advertisements=[];

  data() {
    return {
      toggle: true,
      dissbutton: 'disabled',
    };
  }

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
    this.mainSearchCategory = 1;

    // const message = this.$t('riportalApp.researchInfrastructure.errors.deleteFailed');
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    //alert(this.$t('riportalApp.researchInfrastructure.errors.deleteFailed') + this.currentLanguage);
  }
  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public advertisementCategorizationBranch(advertisement: IAdvertisement): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementCategorizationBranch(advertisement, currentLanguage);
  }

  public advertisementKindsString(advertisement: IAdvertisement): string {
    const currentLanguage = this.$store.getters.currentLanguage;
    return this.advertisementService().advertisementKindsString(advertisement, currentLanguage);
  }
}
