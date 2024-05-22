<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.portalUserOrganization.home.title')" id="portal-user-organization-heading">Portal User Organizations</span>
            <router-link v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
             :to="{name: 'PortalUserOrganizationCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-portal-user-organization">
                <span  v-text="$t('riportalApp.portalUserOrganization.home.createLabel')">
                    Create a new Portal User Organization
                </span>
            </router-link>
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
                    <!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" v-on:click="changeOrder('legalNameSr')"><span v-text="$t('riportalApp.portalUserOrganization.legalName')">Legal Name Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legalNameSr'"></jhi-sort-indicator></th>
                    
                    <th class="sticky" nowrap v-on:click="changeOrder('shortName')"><span v-text="$t('riportalApp.portalUserOrganization.shortName')">Short Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shortName'"></jhi-sort-indicator></th>
                    <th class="sticky" v-on:click="changeOrder('webAddress')"><span v-text="$t('riportalApp.portalUserOrganization.webAddress')">Web Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'webAddress'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('pib')"><span v-text="$t('riportalApp.portalUserOrganization.pib')">Pib</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pib'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('registrationNumber')"><span v-text="$t('riportalApp.portalUserOrganization.registrationNumber')">Registration Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registrationNumber'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" v-on:click="changeOrder('keywords')"><span v-text="$t('riportalApp.portalUserOrganization.keywords')">Keywords</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keywords'"></jhi-sort-indicator></th>
                    <th class="sticky" v-on:click="changeOrder('address')"><span v-text="$t('riportalApp.portalUserOrganization.address')">Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator></th>
                    
                    
                    <th class="sticky" nowrap v-on:click="changeOrder('legalStatus.id')"><span v-text="$t('riportalApp.portalUserOrganization.legalStatus')">Legal Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legalStatus.id'"></jhi-sort-indicator></th>
                    <th class="sticky"></th>
                </tr>
                </thead>
                <tbody>
                    
                <tr v-for="portalUserOrganization in portalUserOrganizations"
                    :key="portalUserOrganization.id">
                    <!--
                    <td>
                        <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: portalUserOrganization.id}}">{{portalUserOrganization.id}}</router-link>
                    </td>
                    -->
                    <td>
                        <span v-if="currentLanguage === 'sr'">{{portalUserOrganization.legalNameSr}}</span>
                        <span v-if="currentLanguage === 'en'">{{portalUserOrganization.legalNameEn}}</span>
                        <span v-if="currentLanguage === 'src'">{{portalUserOrganization.legalNameSrCyr}}</span>
                    </td>
                    
                    <td>{{portalUserOrganization.shortName}}</td>
                    <td>{{portalUserOrganization.webAddress}}</td>
                    <!--
                    <td>{{portalUserOrganization.pib}}</td>
                    <td>{{portalUserOrganization.registrationNumber}}</td>
                    -->
                    <td>{{portalUserOrganization.keywords}}</td>
                    <td>{{portalUserOrganization.address}}
                    {{portalUserOrganization.postalCode}}
                    {{portalUserOrganization.city}}
                    {{portalUserOrganization.country}}</td>
                    <td>
                        <div  v-if="portalUserOrganization.legalStatus">                            
                                {{portalUserOrganization.legalStatus.value}}                            
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: portalUserOrganization.id}}" tag="button" class="btn btn-info btn-sm details">
                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PortalUserOrganizationEdit', params: {portalUserOrganizationId: portalUserOrganization.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(portalUserOrganization)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity
                                   v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
                                   >
                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.portalUserOrganization.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-portalUserOrganization-heading" v-text="$t('riportalApp.portalUserOrganization.delete.question', {'id': removeId})">Are you sure you want to delete this Portal User Organization?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-portalUserOrganization" v-text="$t('entity.action.delete')" v-on:click="removePortalUserOrganization()">Delete</button>
            </div>
        </b-modal>
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

<script lang="ts" src="./portal-user-organization.component.ts">
</script>
