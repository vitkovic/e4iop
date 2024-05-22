<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.portalUserOrganization.home.title')" id="portal-user-organization-heading">Portal User Organizations</span>
            
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && portalUserOrganizations && portalUserOrganizations.length === 0">
            <span v-text="$t('riportalApp.portalUserOrganization.home.notFound')">No portalUserOrganizations found</span>
        </div>
        <div class="table-responsive" v-if="portalUserOrganizations && portalUserOrganizations.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    
                    <th v-if="currentLanguage==='sr'" v-on:click="changeOrder('legalNameSr')"><span v-text="$t('riportalApp.portalUserOrganization.legalName')">Legal Name Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legalNameSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage==='en'"  v-on:click="changeOrder('legalNameEn')"><span v-text="$t('riportalApp.portalUserOrganization.legalName')">Legal Name En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legalNameEn'"></jhi-sort-indicator></th>
                    <th  v-if="currentLanguage==='src'" v-on:click="changeOrder('legalNameSrCyr')"><span v-text="$t('riportalApp.portalUserOrganization.legalName')">Legal Name Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legalNameSrCyr'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('shortName')"><span v-text="$t('riportalApp.portalUserOrganization.shortName')">Short Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shortName'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('webAddress')"><span v-text="$t('riportalApp.portalUserOrganization.webAddress')">Web Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'webAddress'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('pib')"><span v-text="$t('riportalApp.portalUserOrganization.pib')">Pib</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pib'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('registrationNumber')"><span v-text="$t('riportalApp.portalUserOrganization.registrationNumber')">Registration Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registrationNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('keywords')"><span v-text="$t('riportalApp.portalUserOrganization.keywords')">Keywords</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keywords'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('address')"><span v-text="$t('riportalApp.portalUserOrganization.address')">Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('postalCode')"><span v-text="$t('riportalApp.portalUserOrganization.postalCode')">Postal Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postalCode'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('city')"><span v-text="$t('riportalApp.portalUserOrganization.city')">City</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'city'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('country')"><span v-text="$t('riportalApp.portalUserOrganization.country')">Country</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'country'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('legalStatus.id')"><span v-text="$t('riportalApp.portalUserOrganization.legalStatus')">Legal Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legalStatus.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="portalUserOrganization in portalUserOrganizations"
                    :key="portalUserOrganization.id">
                    <td>
                        <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: portalUserOrganization.id}}">{{portalUserOrganization.id}}</router-link>
                    </td>
                    <td  v-if="currentLanguage==='sr'" >{{portalUserOrganization.legalNameSr}}</td>
                    <td  v-if="currentLanguage==='en'" >{{portalUserOrganization.legalNameEn}}</td>
                    <td   v-if="currentLanguage==='src'" >{{portalUserOrganization.legalNameSrCyr}}</td>
                    <!--
                    <td>{{portalUserOrganization.shortName}}</td>
                    -->
                    <td>{{portalUserOrganization.webAddress}}</td>
                    <td>{{portalUserOrganization.pib}}</td>
                    <td>{{portalUserOrganization.registrationNumber}}</td>
                    <td>{{portalUserOrganization.keywords}}</td>
                    <td>{{portalUserOrganization.address}}</td>
                    <!--
                    <td>{{portalUserOrganization.postalCode}}</td>
                    -->
                    <td>{{portalUserOrganization.city}}</td>
                    <td>{{portalUserOrganization.country}}</td>
                    
                    <td>
                        <span v-if="portalUserOrganization.legalStatus">{{portalUserOrganization.legalStatus.value}}</span>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PortalUserOrganizationPregledDetails', params: {portalUserOrganizationId: portalUserOrganization.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>                                                        
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
       
        <div v-show="portalUserOrganizations && portalUserOrganizations.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./portal-user-organization-pregled.component.ts">
</script>
