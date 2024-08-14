<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.company.home.title')" id="company-heading">Companies</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && companies && companies.length === 0">
            <span v-text="$t('riportalApp.company.home.notFound')">No companies found</span>
        </div>
        <div class="table-responsive" v-if="companies && companies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('riportalApp.company.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.company.registrationDateList')">Apr Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('pib')"><span v-text="$t('riportalApp.company.pib')">Rsnis Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pib'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('registrationNumber')"><span v-text="$t('riportalApp.company.registrationNumber')">Apr Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registrationNumber'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="company in companies"
                    :key="company.id">
                    <td class="text-center">
                        <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" style="height: 2.5rem;" />
                    </td>
                    <td>
                        <router-link :to="{name: 'CompanyView', params: {companyId: company.id}}" class="text-body">{{company.name}}</router-link>
                    </td>
                    <td>
                        {{ company.createdAt ? $d(Date.parse(company.createdAt.toString()), { dateStyle: 'short' }) : '' }}
                    </td>
                    <td>{{company.pib}}</td>
                    <td>{{company.registrationNumber}}</td>

                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CMSCompanyCollaborations', params: {companyId: company.id}}" tag="button" class="btn btn-info btn-sm details">
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.company.interactionButtons.collaborations')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompanyEdit', params: {companyId: company.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div v-show="companies && companies.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-b2b-companies.component.ts">
</script>
