<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-10 col-md-8">
        <h2 class="mb-5 cms-title" v-text="$t('riportalApp.cmsB2B.titlePages')"></h2>

        <div class="row">
          <div class="col-4 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.pages.about')"></p>
          </div>
          <div class="col-8 col-md-5">
            <router-link :to="{ name: 'About' }" tag="button" class="btn btn-info btn-sm details">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
            <router-link
              :to="{ name: 'CMSPageEditSpecific', params: { pageType: cmsPageTypeOptions.ABOUT } }"
              tag="button"
              class="btn btn-primary btn-sm details"
            >
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
              <span v-text="$t('entity.action.edit')">View</span>
            </router-link>
          </div>
        </div>
        <div class="row">
          <div class="col-4 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.pages.privacyPolicy')"></p>
          </div>
          <div class="col-8 col-md-5">
            <router-link :to="{ name: 'PrivacyPolicy' }" tag="button" class="btn btn-info btn-sm details">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
            <router-link
              :to="{ name: 'CMSPageEditSpecific', params: { pageType: cmsPageTypeOptions.PRIVACY_POLICY } }"
              tag="button"
              class="btn btn-primary btn-sm details"
            >
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
              <span v-text="$t('entity.action.edit')">View</span>
            </router-link>
          </div>
        </div>
        <div class="row">
          <div class="col-4 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.pages.termsOfUse')"></p>
          </div>
          <div class="col-8 col-md-5">
            <router-link :to="{ name: 'TermsOfUse' }" tag="button" class="btn btn-info btn-sm details">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
            <router-link
              :to="{ name: 'CMSPageEditSpecific', params: { pageType: cmsPageTypeOptions.TERMS_OF_USE } }"
              tag="button"
              class="btn btn-primary btn-sm details"
            >
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
              <span v-text="$t('entity.action.edit')">View</span>
            </router-link>
          </div>
        </div>
        <div class="row">
          <div class="col-4 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.pages.cookiePolicy')"></p>
          </div>
          <div class="col-8 col-md-5">
            <router-link :to="{ name: 'CookiePolicy' }" tag="button" class="btn btn-info btn-sm details">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
            <router-link
              :to="{ name: 'CMSPageEditSpecific', params: { pageType: cmsPageTypeOptions.COOKIES } }"
              tag="button"
              class="btn btn-primary btn-sm details"
            >
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
              <span v-text="$t('entity.action.edit')">View</span>
            </router-link>
          </div>
        </div>

        <div class="row mb-2">
          <div class="col-4 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.pages.faq')"></p>
          </div>
          <div class="col-8 col-md-8 d-flex">
            <router-link :to="{ name: 'Questions' }" tag="button" class="btn btn-info btn-sm details mr-1">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
            <router-link :to="{ name: 'CmsQuestion' }" tag="button" class="btn btn-info btn-sm details">
              <span v-text="$t('riportalApp.cmsB2B.pages.questionsManagement')">View</span>
            </router-link>
          </div>
        </div>

        <div class="row">
          <div class="col-4 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.pages.slider')"></p>
          </div>
          <div class="col-8 col-md-8 d-flex">
            <router-link :to="{ name: 'B2BHome' }" tag="button" class="btn btn-info btn-sm details mr-1">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
            <router-link :to="{ name: 'CmsSlider' }" tag="button" class="btn btn-info btn-sm details">
              <span v-text="$t('riportalApp.cmsB2B.pages.sliderManagement')">View</span>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cms-b2b-pages.component.ts"></script>

<style scoped>
.cms-title{
  color: #004b90;
}
</style>
