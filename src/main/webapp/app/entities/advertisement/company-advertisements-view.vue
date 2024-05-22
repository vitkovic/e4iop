<template>
    <div>
        <div v-if="company" class="d-flex mb-3">
            <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)"
                alt="company logo" 
                style="height: 100px; margin-right: 1rem">
            <h2 id="page-heading"
                class="jh-entity-heading"
                style="align-self: center; text-wrap: nowrap;"> 
                {{company.name}}
            </h2>
        </div>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && advertisements && advertisements.length === 0">
            <span v-text="$t('riportalApp.advertisement.home.notFound')">No advertisements found</span>
        </div>
        <div class="ml-3 mb-3" style="display: flex; align-items: center;">
            <h3 v-text="'Oglasi'" class="mr-3">Oglasi</h3>
            <div>
                <b-button :variant="filterAllButtonVariant" v-text="'Svi'" v-on:click="showAllAdvertisements()">Cancel</b-button>
                <b-button :variant="filterOfferButtonVariant" v-text="'Ponuda'" v-on:click="showOfferAdvertisements()">Cancel</b-button>
                <b-button :variant="filterDemandButtonVariant" v-text="'Potražnja'" v-on:click="showDemandAdvertisements()">Cancel</b-button>
            </div>
        </div>
        <div class="table-responsive" v-if="advertisements && advertisements.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.advertisement.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('type.id')"><span v-text="$t('riportalApp.advertisement.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('kind.id')"><span v-text="$t('riportalApp.advertisement.kind')">Kind</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.advertisement.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('subsubcategory.name')"><span v-text="$t('riportalApp.advertisement.subsubcategory')">Subsubcategory</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subsubcategory.name'"></jhi-sort-indicator></th>  
                    <th v-on:click="changeOrder('budget')"><span v-text="$t('riportalApp.advertisement.budget')">Budget</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'budget'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('activationDatetime')"><span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationDatetime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('duration.id')"><span v-text="'Expiration Date'">Duration</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duration.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('company.id')"><span v-text="$t('riportalApp.advertisement.company')">Company</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisement in advertisements"
                    :key="advertisement.id">
                    <td>                            
                        <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisement.id}}">
                                {{advertisement.title}}
                        </router-link>
                    </td>
                    <td>{{advertisement.type.type}}</td>
                    <td>
                        <div v-if="advertisement.kind">
                            {{advertisement.kind.kind}}
                        </div>
                    </td>
                    <td>{{advertisement.description}}</td>
                    <td>
                        <div v-if="advertisement.subsubcategory">
                            {{advertisement.subsubcategory.name}}
                        </div>
                    </td>
                    <td>{{advertisement.budget}}</td>                  
                    <td>{{advertisement.activationDatetime ? $d(Date.parse(advertisement.activationDatetime), 'short') : ''}}</td>
                    <td>
                        <div v-if="advertisement.duration && advertisement.activationDatetime">
                            <!-- {{advertisement.duration.duration}} -->
                            {{ $d(Date.parse(getExpirationDate(advertisement)), 'short') }}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: advertisement.company.id}}">{{advertisement.company.name}}</router-link>
                        </div>
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

<script lang="ts" src="./company-advertisements-view.component.ts">
</script>
