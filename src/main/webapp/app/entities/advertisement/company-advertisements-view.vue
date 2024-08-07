<template>
  <div>
    <div v-if="company" class="d-flex mb-3 mt-4">
      <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" class="company-logo mr-3" />
      <h2 id="page-heading" class="jh-entity-heading company-title" style="align-self: center;">
        {{ company.name }}
      </h2>
    </div>
    <b-alert
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && advertisements && advertisements.length === 0">
      <span v-text="$t('riportalApp.advertisement.home.notFound')">No advertisements found</span>
    </div>
    <div class="ml-3 mb-3 d-flex flex-column flex-sm-row">
      <h3 v-text="$t('riportalApp.advertisement.home.title')" class="mr-3 mb-3 mb-sm-0">Oglasi</h3>
      <div>
        <b-button
          :variant="filterAllButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.all')"
          v-on:click="showAllAdvertisements()"
          >Cancel</b-button
        >
        <b-button
          :variant="filterOfferButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.offer')"
          v-on:click="showOfferAdvertisements()"
          >Cancel</b-button
        >
        <b-button
          :variant="filterDemandButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.demand')"
          v-on:click="showDemandAdvertisements()"
          >Cancel</b-button
        >
      </div>
    </div>
    <div class="custom-table-responsive" v-if="advertisements && advertisements.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <th v-on:click="changeOrder('title')">
              <span v-text="$t('riportalApp.advertisement.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('type.id')">
              <span v-text="$t('riportalApp.advertisement.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('kind.id')">
              <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
              <!-- <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator> -->
            </th>
            <th v-on:click="changeOrder('subsubcategory.name')">
              <span v-text="$t('riportalApp.advertisement.categorization')">Subsubcategory</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subsubcategory.name'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('budget')">
              <span v-text="$t('riportalApp.advertisement.budget')">Budget</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'budget'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('activationDatetime')">
              <span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationDatetime'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('duration.id')">
              <span v-text="$t('riportalApp.advertisement.expirationDatetime')">Duration</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duration.id'"></jhi-sort-indicator>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="advertisement in advertisements" :key="advertisement.id">
            <td>
              <router-link :to="{ name: 'AdvertisementView', params: { advertisementId: advertisement.id } }" class="text-body">
                {{ advertisement.title }}
              </router-link>
            </td>
            <td>
              <span v-if="$store.getters.currentLanguage === 'sr'">{{ advertisement.type.type }}</span>
              <span v-else-if="$store.getters.currentLanguage === 'src'">{{ advertisement.type.typeSrc }}</span>
              <span v-else-if="$store.getters.currentLanguage === 'en'">{{ advertisement.type.typeEn }}</span>
            </td>
            <td>
              <div v-if="advertisement.kinds">
                {{ advertisementKindsString(advertisement) }}
              </div>
            </td>
            <td v-if="advertisement.subsubcategory">
              {{ advertisementCategorizationBranch(advertisement) }}
            </td>
            <td>
              <div v-if="advertisement.budget">
                {{ advertisement.budget.toLocaleString('sr-SR', { style: 'currency', currency: 'RSD' }) }}
              </div>
            </td>
            <td>{{ advertisement.activationDatetime ? $d(Date.parse(advertisement.activationDatetime.toString()), { dateStyle: 'short' }) : '' }}
            </td>
            <td>
                {{ advertisement.expirationDatetime ? $d(Date.parse(advertisement.expirationDatetime.toString()), { dateStyle: 'short' }) : '' }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-show="advertisements && advertisements.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./company-advertisements-view.component.ts"></script>

<style scoped>
.company-logo {
  height: 75px;
}

.custom-table-responsive {
  overflow-x: auto;
}

@media (max-width: 1280px) {
  .custom-table-responsive table {
    min-width: 1280px;
  }
}

@media screen and (max-width: 578px) {
  .company-title {
    font-size: 2rem;
  }
}
</style>
