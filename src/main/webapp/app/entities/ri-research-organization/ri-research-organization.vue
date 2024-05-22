<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riResearchOrganization.home.title')" id="ri-research-organization-heading">Ri Research Organizations</span>
            <router-link :to="{name: 'RiResearchOrganizationCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-research-organization">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riResearchOrganization.home.createLabel')">
                    Create a new Ri Research Organization
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
        <div class="alert alert-warning" v-if="!isFetching && riResearchOrganizations && riResearchOrganizations.length === 0">
            <span v-text="$t('riportalApp.riResearchOrganization.home.notFound')">No riResearchOrganizations found</span>
        </div>
        <div class="table-responsive" v-if="riResearchOrganizations && riResearchOrganizations.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('riportalApp.riResearchOrganization.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('descriptionSr')"><span v-text="$t('riportalApp.riResearchOrganization.descriptionSr')">Description Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionSr'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('decsriptionEn')"><span v-text="$t('riportalApp.riResearchOrganization.decsriptionEn')">Decsription En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'decsriptionEn'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('logo')"><span v-text="$t('riportalApp.riResearchOrganization.logo')">Logo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logo'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('descriptionSrCyr')"><span v-text="$t('riportalApp.riResearchOrganization.descriptionSrCyr')">Description Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionSrCyr'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('puOrganization.id')"><span v-text="$t('riportalApp.riResearchOrganization.puOrganization')">Pu Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'puOrganization.id'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('researchInstitution.id')"><span v-text="$t('riportalApp.riResearchOrganization.researchInstitution')">Research Institution</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'researchInstitution.id'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('activityStatus.id')"><span v-text="$t('riportalApp.riResearchOrganization.activityStatus')">Activity Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activityStatus.id'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('manager.id')"><span v-text="$t('riportalApp.riResearchOrganization.manager')">Manager</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'manager.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('contactPerson.id')"><span v-text="$t('riportalApp.riResearchOrganization.contactPerson')">Contact Person</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contactPerson.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riResearchOrganization in riResearchOrganizations"
                    :key="riResearchOrganization.id">
                    <td>
                        <router-link :to="{name: 'RiResearchOrganizationView', params: {riResearchOrganizationId: riResearchOrganization.id}}">{{riResearchOrganization.id}}</router-link>
                    </td>
                    <td>{{riResearchOrganization.name}}</td>
                    <td>{{riResearchOrganization.descriptionSr}}</td>
                    <td>{{riResearchOrganization.decsriptionEn}}</td>
                    <td>{{riResearchOrganization.logo}}</td>
                    <td>{{riResearchOrganization.descriptionSrCyr}}</td>
                    <td>
                        <div v-if="riResearchOrganization.puOrganization">
                            <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: riResearchOrganization.puOrganization.id}}">
                                <span v-if="currentLanguage == 'sr'">{{riResearchOrganization.puOrganization.legalNameSr}}</span>
                                <span v-if="currentLanguage == 'src'">{{riResearchOrganization.puOrganization.legalNameSrCyr}}</span>
                                <span v-if="currentLanguage == 'en'">{{riResearchOrganization.puOrganization.legalNameEn}}</span>
                            </router-link>
                        </div>
                    </td>
                    <!--
                    <td>
                        <div v-if="riResearchOrganization.researchInstitution">
                            <router-link :to="{name: 'ResearchInstitutionView', params: {researchInstitutionId: riResearchOrganization.researchInstitution.id}}">{{riResearchOrganization.researchInstitution.id}}</router-link>
                        </div>
                    </td>
                    
                    <td>
                        <div v-if="riResearchOrganization.activityStatus">
                            <router-link :to="{name: 'RipoActivityStatusView', params: {ripoActivityStatusId: riResearchOrganization.activityStatus.id}}">{{riResearchOrganization.activityStatus.id}}</router-link>
                        </div>
                    </td>
                    -->
                    <td>
                        <div v-if="riResearchOrganization.manager">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: riResearchOrganization.manager.id}}">{{riResearchOrganization.manager.user.lastName}} {{riResearchOrganization.manager.user.firstName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="riResearchOrganization.contactPerson">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: riResearchOrganization.contactPerson.id}}">{{riResearchOrganization.contactPerson.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiResearchOrganizationView', params: {riResearchOrganizationId: riResearchOrganization.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiResearchOrganizationEdit', params: {riResearchOrganizationId: riResearchOrganization.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riResearchOrganization)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.riResearchOrganization.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riResearchOrganization-heading" v-text="$t('riportalApp.riResearchOrganization.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Research Organization?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riResearchOrganization" v-text="$t('entity.action.delete')" v-on:click="removeRiResearchOrganization()">Delete</button>
            </div>
        </b-modal>
        <div v-show="riResearchOrganizations && riResearchOrganizations.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-research-organization.component.ts">
</script>
