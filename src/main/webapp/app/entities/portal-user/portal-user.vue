<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.portalUser.home.title')" id="portal-user-heading">Portal Users</span>
            <router-link :to="{name: 'PortalUserCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-portal-user"
            v-if="authenticated && (hasAnyRole(['PA', 'RPRIPO']) || hasAnyAuthority('ROLE_ADMIN'))">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.portalUser.home.createLabel')">
                    Create a new Portal User
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
        <div class="alert alert-warning" v-if="!isFetching && portalUsers && portalUsers.length === 0">
            <span v-text="$t('riportalApp.portalUser.home.notFound')">No portalUsers found</span>
        </div>
        <div class="table-responsive" v-if="portalUsers && portalUsers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('userFirstName')"><span v-text="$t('riportalApp.portalUser.firstName')">First Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('user.lastName')"><span v-text="$t('riportalApp.portalUser.familyName')">Family Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'familyName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phone')"><span v-text="$t('riportalApp.portalUser.phone')">Phone</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('position')"><span v-text="$t('riportalApp.portalUser.position')">Position</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'position'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('researcher.id')"><span v-text="$t('riportalApp.portalUser.researcher')">Researcher</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'researcher.id'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('userOrganization.legalNameSr')"><span v-text="$t('riportalApp.portalUser.userOrganization')">User Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userOrganization.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="portalUser in portalUsers"
                    :key="portalUser.id">
                    <td>
                        <router-link class="link-style" :to="{name: 'PortalUserView', params: {portalUserId: portalUser.id}}">{{portalUser.id}}</router-link>
                    </td>
                    <td>{{portalUser.user.firstName}}</td>
                    <td>{{portalUser.user.lastName}}</td>
                    <td>{{portalUser.phone}}</td>
                    <td>{{portalUser.position}}</td>
                    <!--
                    <td>
                        <div v-if="portalUser.researcher">
                            <router-link :to="{name: 'ResearcherView', params: {researcherId: portalUser.researcher.id}}">{{portalUser.researcher.id}}</router-link>
                        </div>
                    </td>
                    -->
                    <td>
                        <div v-if="portalUser.userOrganization">
                            <router-link class="link-style" :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: portalUser.userOrganization.id}}">
                                <span v-if="currentLanguage == 'sr'">{{portalUser.userOrganization.legalNameSr}}</span>
                                <span v-if="currentLanguage == 'en'">{{portalUser.userOrganization.legalNameEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{portalUser.userOrganization.legalNameSrCyr}}</span>
                            </router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <!--
                            <router-link :to="{name: 'ActivateUserRoles', params: {portalUserId: portalUser.id}}" tag="button" id="jh-create-entity" 
                                class="btn btn-primary float-right jh-create-entity create-portal-user mr-2">                
                                <span  v-text="$t('riportalApp.portalUser.home.userRoles')">
                                    Create a new Portal User
                                </span>
                            </router-link>
                            -->
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: portalUser.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PortalUserEdit', params: {portalUserId: portalUser.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(portalUser)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity
                                   v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
                                   >
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
            <span slot="modal-title"><span id="riportalApp.portalUser.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-portalUser-heading" v-text="$t('riportalApp.portalUser.delete.question', {'id': removeId})">Are you sure you want to delete this Portal User?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-portalUser" v-text="$t('entity.action.delete')" v-on:click="removePortalUser()">Delete</button>
            </div>
        </b-modal>
        <div v-show="portalUsers && portalUsers.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./portal-user.component.ts">
</script>
